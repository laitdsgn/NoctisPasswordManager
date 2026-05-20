package com.example.passwordmanager;

import android.util.Base64;

import java.security.SecureRandom;

public class SaltGenerator {
    public String GenerateSalt() {
        SecureRandom Srandom = new SecureRandom();
        byte[] salt = new byte[16];
        Srandom.nextBytes(salt);
        String wynik =  Base64.encodeToString(salt, Base64.NO_WRAP);
        return wynik;
    }

}
