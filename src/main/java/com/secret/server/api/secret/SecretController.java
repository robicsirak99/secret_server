package com.secret.server.api.secret;

import com.secret.server.model.Secret;
import com.secret.server.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

//controller for the secrets
public abstract class SecretController {

    public SecretService secretService;

    //date and time format for a secret
    public DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    protected SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    //searches for a secret by its hash value
    abstract Object getSecret(String hash);


    //sets the hash value, the creation date and the expiration date for a new secret
    public void finalizeSecretParameters(Secret secret){
        secret.setHash(UUID.randomUUID().toString());
        secret.setCreatedAt(LocalDateTime.now().format(dateTimeFormat));

        //if the user enters the number 0 for the 'expires at' field then the secret will never expire (except if the remaining views are decreased to 0)
        if(secret.getExpiresAt().equals("0")) secret.setExpiresAt("never expires");
        else secret.setExpiresAt(LocalDateTime.now().plusMinutes(Integer.valueOf(secret.getExpiresAt())).format(dateTimeFormat));
    }


    //returns a secret if it's existing and is not expired
    public Secret findSecretOrThrowException(String hash){

        //searches for a secret by its hash value
        Optional<Secret> secret = secretService.getSecretByHash(hash);

        //if no secret is found with this hash value, throws a 404 exception
        if(secret == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No secret found with this hash value.");

        //deletes the secret if it's expired and throws a 404 exception
        if(checkIfSecretIsExpired(secret.get())){
            secretService.deleteSecretByHash(hash);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No secret found with this hash value.");
        }
        return secret.get();
    }

    //checks if a secret is expired or not
    //a secret is expired if the expiration date and time of it is lower than the current date and time
    // or the remaining views of the secret is 0
    public boolean checkIfSecretIsExpired(Secret secret){
        LocalDateTime expiresAt = LocalDateTime.parse(secret.getExpiresAt(), dateTimeFormat);
        return ( (LocalDateTime.now().isAfter(expiresAt)) || (Integer.valueOf(secret.getRemainingViews())==0) );
    }

    //decreases the remaining view of a secret by one
    public Secret decreaseSecretRemainingViews(Secret secret){
        secret.setRemainingViews((String.valueOf(Integer.valueOf(secret.getRemainingViews())-1)));
        return secret;
    }
}
