package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    final CredentialMapper credentialMapper;
    final UserMapper userMapper;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
    }

    public Credential[] getCredentials(Integer userid){
        return credentialMapper.getCredentials(userid);
    };

    public Credential getCredential(Integer credentialid){
        return credentialMapper.getCredential(credentialid);
    }
    public Integer createCredential(String url, String username,String key, String password){
        Integer userid = userMapper.getUser(username).getUserId();
        Credential credential = new Credential(0, url, username, key, password,userid );
        return credentialMapper.insertCredential(credential);
    }

    public void deleteCredential(Integer credentialid){
        credentialMapper.deleteCredential(credentialid);
    }
    public void updateCredential(Integer credentialid,String username, String url, String key, String password){

        credentialMapper.updateCredential(credentialid, username, url, key, password);
    }

}
