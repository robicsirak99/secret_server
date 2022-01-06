package com.secret.server.api.secret;

import com.secret.server.model.Secret;
import com.secret.server.service.SecretService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//controller for the '/secret' page with xml response
@Controller
@RequestMapping("/secret")
public class SecretControllerXML extends SecretController {

    //calls the constructor of the super class
    protected SecretControllerXML(SecretService secretService) {
        super(secretService);
    }

    //searches for a secret by its hash value
    //if the secret is not found throws a 404 error
    @Override
    @GetMapping(value = "/{hash}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Secret getSecret(@PathVariable String hash) {
        //searches for the secret, if it's not found, throws a 404 error
        Secret secret = findSecretOrThrowException(hash);
        //decreases the remaining views of the secret
        secretService.updateSecret(decreaseSecretRemainingViews(secret));
        return secret;
    }
}
