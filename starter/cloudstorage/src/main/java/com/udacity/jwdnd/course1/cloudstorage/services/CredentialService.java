package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentials() {
        return credentialMapper.getCredentials();
    }

    public List<Credential> getCredentialsByUserid(Integer userid) {
        return credentialMapper.getCredentialByUserid(userid);
    }

    public void addCredential(Credential credential) {
        credentialMapper.insert(credential);
    }

    public int updateByCredentialId(Credential credential) {
        return credentialMapper.updateByCredentialId(credential);
    }

    public Credential getCredentialById(Integer id) {
        return credentialMapper.getCredentialBycredentialid(id);
    }

    public int deleteCredentialById(Integer id) {
        return credentialMapper.deleteCredentialByCredentialid(id);
    }

}
