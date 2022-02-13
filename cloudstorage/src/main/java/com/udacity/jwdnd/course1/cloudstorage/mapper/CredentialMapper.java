package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {



    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    Credential[] getCredentials(Integer userid);


    @Insert("INSERT INTO CREDENTIALS (url, username,key, password, userid) VALUES(#{url},#{username},#{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(Integer credentialid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET  url=#{url}, key=#{key}, password = #{password}, username = #{username} WHERE credentialid = #{credentialid}")
    void updateCredential(Integer credentialid, String username, String url, String key, String password);





}
