package com.secret.server.api.secret;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

//encryption and decryption for the secrets
public class SecretEncryptAndDecrypt implements AttributeConverter<String, String> {

    private static final String AES = "AES";
    private static final byte[] encryptionKey = "this-is-test-key".getBytes();
    private final Key key;

    private final Cipher cipher;

    public SecretEncryptAndDecrypt() throws Exception {
        key = new SecretKeySpec(encryptionKey, AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}