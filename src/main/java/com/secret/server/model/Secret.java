package com.secret.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.secret.server.api.secret.SecretEncryptAndDecrypt;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Secret{

    @Id
    @JsonProperty("hash")
    private String hash;

    @Convert(converter = SecretEncryptAndDecrypt.class)
    @JsonProperty("secretText")
    private String secretText;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("expiresAt")
    private String expiresAt;

    @JsonProperty("remainingViews")
    private String remainingViews;

    public Secret(){

    }

    public Secret(String hash, String secretText, String createdAt, String expiresAt, String remainingViews) {
        this.hash = hash;
        this.secretText = secretText;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.remainingViews = remainingViews;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getHash() {
        return hash;
    }

    public String getRemainingViews() {
        return remainingViews;
    }

    public String getSecretText() {
        return secretText;
    }

    public void setRemainingViews(String remainingViews) {
        this.remainingViews = remainingViews;
    }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public void setExpiresAt(String expiresAt) { this.expiresAt = expiresAt; }

    public void setHash(String hash) { this.hash = hash; }

    public void setSecretText(String secretText) { this.secretText = secretText; }
}
