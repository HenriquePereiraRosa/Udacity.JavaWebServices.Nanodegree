package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    CredentialMapper credentialMapper;

    public Credential getByUrl(String url) {
        Credential credential = credentialMapper.getByUrl(url);
        return credential;
    }

    public Integer saveFile(Credential credential) {
        return credentialMapper.insertOne(credential);
    }

    public Integer deleteFile(String url) {
        return credentialMapper.deleteByUrl(url);
    }

    public List<Credential> getAllById(Integer id) {
        return credentialMapper.getByUserId(id);
    }
}
