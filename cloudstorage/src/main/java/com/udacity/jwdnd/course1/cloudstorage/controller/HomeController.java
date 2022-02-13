package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Base64;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, CredentialService credentialService, NoteService noteService, UserService userService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication,
                              Model model){
        User user = userService.getUser(authentication.getName());
        Integer userid = user.getUserId();

        model.addAttribute("filelist", fileService.getFileNames(userid));
        model.addAttribute("notelist", noteService.getNotes(userid));
        model.addAttribute("credentiallist", credentialService.getCredentials(userid));
        model.addAttribute("encryption", encryptionService);
        return "home";
    }

    @GetMapping(value = "/get-file/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable String filename) {
        return fileService.getFile(filename).getFiledata();
    }

    @GetMapping(value = "/get-credential/{credentialid}")
        public Credential getCredential(@PathVariable Integer credentialid){
            return credentialService.getCredential(credentialid);
        }

    @GetMapping(value = "/get-note/{noteid}")
    public Note getNote(@PathVariable Integer notelid){
        return noteService.getNote(notelid);
    }


    @PostMapping("add-file")

    public String postFile( Authentication authentication, @RequestParam("fileUpload") MultipartFile file,

                            Model model) throws IOException {

        User user = userService.getUser(authentication.getName());
        Integer userid = user.getUserId();

        String[] filenames = fileService.getFileNames(userid);


        String fileName = file.getOriginalFilename();
        boolean contains = Arrays.asList(filenames).contains(fileName);
        if(!contains){
            fileService.createFile(file, user.getUsername());
            model.addAttribute("result", "success");
        }else{
            model.addAttribute("result", "error");

        }
        model.addAttribute("filelist", fileService.getFileNames(userid));



        return "result";
    }

    @PostMapping("add-credentials")
    public String postCredentials(Authentication authentication,@RequestParam("credentialId") Integer credentialId,
                                  @RequestParam("url") String url, @RequestParam("username") String username,
                                  @RequestParam("password") String password, Model model){
        User user = userService.getUser(authentication.getName());


        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encodedPass = encryptionService.encryptValue(password, encodedKey);

        if(credentialId == null){
            credentialService.createCredential(url, username, encodedKey,  encodedPass);
        }else{
            credentialService.updateCredential(credentialId, username, url, encodedKey, encodedPass);

        }
        model.addAttribute("credentiallist", credentialService.getCredentials(user.getUserId()));
        model.addAttribute("result", "success");


        return "result";
    }

    @PostMapping("add-note")
    public String postNote(Authentication authentication,@RequestParam("noteId") Integer noteid,
                           @RequestParam("noteTitle") String notetitle, @RequestParam("noteDescription") String notedescription,
                           Model model){


        User user = userService.getUser(authentication.getName());

        if(noteid == null){
            noteService.createNote(notetitle, notedescription, user.getUsername());
        }else{

            noteService.updateNote(notetitle, notedescription, noteid);
        }
        model.addAttribute("notelist", noteService.getNotes(user.getUserId()));
        model.addAttribute("result", "success");

        return "result";
    }


    @GetMapping("/delete-file/{fileName}")
    public String deleteFile(
            Authentication authentication, @PathVariable String fileName,
            Model model) {
        fileService.deleteFile(fileName);

        Integer userid = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("filelist", fileService.getFiles(userid));
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping("/delete-credential/{credentialid}")
    public String deleteCredential(
            Authentication authentication, @PathVariable Integer credentialid,
            Model model) {
        this.credentialService.deleteCredential(credentialid);

        Integer userid = this.userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("credentiallist", this.credentialService.getCredentials(userid));
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping("/delete-note/{noteid}")
    public String deleteNote(
            Authentication authentication, @PathVariable Integer noteid,
            Model model) {
        this.noteService.deleteNote(noteid);

        Integer userid = this.userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("notelist", this.noteService.getNotes(userid));
        model.addAttribute("result", "success");

        return "result";
    }






}
