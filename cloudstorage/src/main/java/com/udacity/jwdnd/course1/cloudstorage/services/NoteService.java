package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper ) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public Note[] getNotes(Integer userid){
        return noteMapper.getNotes(userid);
    }

    public int createNote(String notetitle, String notedescription, String username){
        Integer userid = userMapper.getUser(username).getUserId();
        Note note = new Note(0, notetitle, notedescription, userid);
        return noteMapper.insert(note);
    }
    public Note getNote(Integer noteid){
        return noteMapper.getNote(noteid);
    }
    public void updateNote(String notetitle, String notedescription, Integer noteid){

        noteMapper.updateNote(noteid, notetitle,notedescription);
    }

    public void deleteNote(Integer noteid){
        noteMapper.deleteNote(noteid);
    }
}
