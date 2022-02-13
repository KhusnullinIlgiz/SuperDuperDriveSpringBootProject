package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {


    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    Note[] getNotes(Integer userid);


    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(Integer noteid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNote(Integer noteid);

    @Update("UPDATE NOTES SET NOTES.notetitle = #{notetitle}, NOTES.notedescription = #{notedescription}  WHERE NOTES.noteid = #{noteid}")
    void updateNote(Integer noteid, String notetitle, String notedescription);
}
