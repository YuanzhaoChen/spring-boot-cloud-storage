package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService,
                             UserService userService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    public int addCredential(CredentialForm credentialForm) {
        return this.credentialMapper.insertCredential(new Credential(
                credentialForm.getId(),
                credentialForm.getUrl(),
                credentialForm.getUserName(),
                credentialForm.getKey(),
                credentialForm.getEncryptedPassword(),
                credentialForm.getUserId()
        ));
    }

    public Credential getCredential(Integer id){
        return this.credentialMapper.getCredential(id);
    }

    public int deleteCredential(Integer id){
        this.credentialMapper.deleteCredential(id);
        return 0;
    }


    public int addCredential(Credential credential) {
        Integer userId = credential.getUserId();
        String salt = this.userService.getUserById(userId).getSalt();
        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), salt);
        Credential encryptedCredential = credential.copy(credential);
        encryptedCredential.setPassword(encryptedPassword);
        return this.credentialMapper.insertCredential(encryptedCredential);
    }

    public List<CredentialForm> getAllCredentialForms(Integer userId) {
        List<CredentialForm> credentialForms = new LinkedList<>();
        for (Credential credential : getAllCredentials(userId)) {
            credentialForms.add(toForm(credential));
        }
        return credentialForms;
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return this.credentialMapper.getAllCredentials(userId);
    }

    public CredentialForm toForm(Credential credential) {
        return new CredentialForm(
                credential.getId(),
                credential.getUrl(),
                credential.getUserName(),
                credential.getKey(),
                credential.getPassword(),
                this.encryptionService.decryptValue(credential.getPassword(), this.userService.getUserById(credential.getUserId()).getSalt()),
                credential.getUserId());
    }

    public int updateCredential(CredentialForm credentialForm) {
        this.credentialMapper.updateCredential(
                new Credential(
                        credentialForm.getId(),
                        credentialForm.getUrl(),
                        credentialForm.getUserName(),
                        credentialForm.getKey(),
                        this.encryptionService.encryptValue(credentialForm.getRawPassword(), this.userService.getUserById(credentialForm.getUserId()).getSalt()),
                        credentialForm.getUserId()
                )
        );
        return 0;
    }
}
