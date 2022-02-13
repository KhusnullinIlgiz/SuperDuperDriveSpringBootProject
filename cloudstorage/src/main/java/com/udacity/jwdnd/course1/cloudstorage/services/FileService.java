package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    final FileMapper fileMapper;
    final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public void createFile(MultipartFile multipartFile, String userName) throws IOException {

        InputStream inputStream = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRows;
        byte[] data = new byte[1024];
        while ((nRows = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRows);
        }
        buffer.flush();
        byte[] fileByteArray = buffer.toByteArray();

        String filename = multipartFile.getOriginalFilename();
        String contenttype = multipartFile.getContentType();
        String filesize = String.valueOf(multipartFile.getSize());
        Integer userid = userMapper.getUser(userName).getUserId();
        File file = new File(filename, contenttype, filesize, userid, fileByteArray);
        fileMapper.insertFile(file);

    };

    public File getFiles(Integer userid){
        return fileMapper.getFiles(userid);
    };

    public String[] getFileNames(Integer userid){
        return fileMapper.getFileNames(userid);
    };


    public File getFile(String filename){
        return fileMapper.getFile(filename);
    }
    public void deleteFile(String filename){
        fileMapper.deleteFile(filename);
    };
}
