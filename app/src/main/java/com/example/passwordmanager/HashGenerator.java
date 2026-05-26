package com.example.passwordmanager;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    public String GenerateHashWithSalt(String password, String salt) throws NoSuchAlgorithmException {

        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] saltBytes = Base64.decode(salt, Base64.DEFAULT);
        byte[] combined = new byte[passwordBytes.length + saltBytes.length];



       System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
       System.arraycopy(passwordBytes, 0, combined, saltBytes.length, passwordBytes.length);

       byte[] hash = MessageDigest.getInstance("SHA-256").digest(combined);

       String stringFinalHash = Base64.encodeToString(hash, Base64.NO_WRAP);
       return stringFinalHash;
    }

}
