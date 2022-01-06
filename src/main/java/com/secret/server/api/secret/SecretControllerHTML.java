package com.secret.server.api.secret;

import com.secret.server.model.Secret;
import com.secret.server.service.SecretService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//controller for the '/secret' page with html response
@Controller
@RequestMapping("/secret")
public class SecretControllerHTML extends SecretController {

    //calls the constructor of the super class
    protected SecretControllerHTML(SecretService secretService) {
        super(secretService);
    }

    //loads the '/secret' page
    //this is the page where the user creates a new secret
    @GetMapping
    public ModelAndView loadSecretForm(Secret secret) {
        return new ModelAndView("secret").addObject("secret", secret);
    }

    //submits a new secret
    @PostMapping
    public ModelAndView submitSecretForm(@ModelAttribute("secret") Secret secret) {
        //finalizes the parameters of the new secret(hash value, expiration date, creation date)
        finalizeSecretParameters(secret);
        //adds the new secret to the database
        secretService.addSecret(secret);
        return new ModelAndView("secret-confirmation").addObject("secret", secret);
    }

    //searches for a secret by its hash value
    //if the secret is not found throws a 404 error
    @Override
    @GetMapping(value = "/{hash}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getSecret(@PathVariable String hash){
        //searches for the secret, if it's not found, throws a 404 error
        Secret secret = findSecretOrThrowException(hash);
        //decreases the remaining views of the secret
        secretService.updateSecret(decreaseSecretRemainingViews(secret));
        return new ModelAndView("secret-access").addObject("secret", secret);
    }

}
