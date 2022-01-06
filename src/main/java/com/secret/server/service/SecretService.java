package com.secret.server.service;

import com.secret.server.repository.SecretRepository;
import com.secret.server.model.Secret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecretService {

    @Autowired
    private SecretRepository secretRepository;

    public void addSecret(Secret secret){
        secretRepository.save(secret);
    }

    public Optional<Secret> getSecretByHash(String hash){
        if(secretRepository.existsById(hash))
            return secretRepository.findById(hash);
        return null;
    }

    public void deleteSecretByHash(String hash){
        secretRepository.deleteById(hash);
    }

    public void updateSecret(Secret secret){
        secretRepository.save(secret);
    }
}
