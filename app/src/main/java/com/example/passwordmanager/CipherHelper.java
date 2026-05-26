package com.example.passwordmanager;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class CipherHelper {
    public String GeneratePasswordCipher(String password, String masterPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherKey = MessageDigest.getInstance("SHA-256").digest(masterPassword.getBytes());
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        SecretKeySpec key = new SecretKeySpec(cipherKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedPassword = cipher.doFinal(password.getBytes());
        byte[] combined = new byte[16 + encryptedPassword.length];
        System.arraycopy(iv, 0, combined, 0, 16);
        System.arraycopy(encryptedPassword, 0, combined, 16, encryptedPassword.length);

        return Base64.encodeToString(combined, Base64.NO_WRAP);
    }

    public String DecryptPasswordCipher(String EncryptedPassword, String masterPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] combined = Base64.decode(EncryptedPassword, Base64.NO_WRAP);

        byte[] iv = new byte[16];
        byte[] encrypted = new byte[combined.length - 16];
        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, encrypted, 0, encrypted.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] key = MessageDigest.getInstance("SHA-256").digest(masterPassword.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        return new String(cipher.doFinal(encrypted));
    }

    public boolean ArePasswordsEqual(String password, String masterPassword, String encryptedPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String decryptedPassword = DecryptPasswordCipher(encryptedPassword, masterPassword);
        return decryptedPassword.equals(password);
    }
}
