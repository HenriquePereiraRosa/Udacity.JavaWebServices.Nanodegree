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
    @Autowired
    EncryptionService encryptionService ;
    @Autowired
    HashService hashService;

    public Credential getById(Integer id) {
        return credentialMapper.getById(id);
    }

    public Credential getByUrl(String url) {
        Credential credential = credentialMapper.getByUrl(url);
        return credential;
    }

    public Integer saveOne(Credential credential) {
        String key =  hashService.createEncodedSalt();
        String hashedPass = encryptionService
                .encryptValue(credential.getPassword(), key);
        credential.setKey(key);
        credential.setPassword(hashedPass);
        return credentialMapper.insertOne(credential);
    }

    public Integer deleteByUrl(String url) {
        return credentialMapper.deleteByUrl(url);
    }

    public List<Credential> getAllByUserId(Integer id) {
        List<Credential> credentials = credentialMapper.getByUserId(id);
//        for (int i = 0; i < credentials.size(); i++) {
//            Credential item = credentials.get(i);
//            credentials.get(i).setPassword(encryptionService
//                    .decryptValue(item.getPassword(), item.getKey()));
//        }
        return credentials;
    }

    public Integer updateOne(Credential credential) {
        Credential credentialDb = credentialMapper.getById(credential.getId());
        credentialDb.setUrl(credential.getUrl());
        credentialDb.setUsername(credential.getUsername());
        credentialDb.setKey(hashService.createEncodedSalt());
        credentialDb.setPassword(encryptionService
                .encryptValue(credential.getPassword(), credentialDb.getKey()));
        return credentialMapper.updateOne(credentialDb);
    }

    public Integer deleteById(Integer id) { return credentialMapper.deteleById(id);
    }

}
