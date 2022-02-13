package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    File getFiles(Integer userid);

    @Select("SELECT FILES.filename FROM FILES WHERE FILES.userid = #{userid}")
    String[] getFileNames(Integer userid);


    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES( #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFile(String filename);

    @Delete("DELETE FROM FILES WHERE filename = #{filename}")
    void deleteFile(String filename);


}
