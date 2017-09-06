package com.company;

/**
 * Created by Dean on 9/5/2017.
 */
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

    public void encrypt(File file, String password) throws Exception{
        crypto(Cipher.ENCRYPT_MODE, file, password);
        System.out.println("File Encrypted");
    }

    public boolean isEncrypted(File file){
        if((FilenameUtils.getExtension(file.toString())).equalsIgnoreCase("enc")){
            return  true;
        }
        return false;
    }

    public void decrypt(File encryptedFile, String password) throws Exception{
        crypto(Cipher.DECRYPT_MODE, encryptedFile, password);
        System.out.println("File Decrypted");
    }

    public void crypto(int cipherMode, File inputFile, String password) throws Exception{
        FileInputStream fileInputStream = new FileInputStream(inputFile);

        String filePath = inputFile.getParent();

        FileOutputStream fileOutputStream = new FileOutputStream(filePath+"\\encryptedFile.enc");

        byte[] salt = new byte[8];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        FileOutputStream saltOutFile = new FileOutputStream(filePath+"\\saltFile.enc");

        saltOutFile.write(salt);
        saltOutFile.close();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(cipherMode, secret);

        AlgorithmParameters params = cipher.getParameters();

        FileOutputStream ivOutFile = new FileOutputStream(filePath+"\\iv.enc");
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        ivOutFile.write(iv);
        ivOutFile.close();

        byte[] input = new byte[64];
        int bytesRead;

        while((bytesRead = fileInputStream.read(input)) != -1){
            byte[] output = cipher.update(input, 0, bytesRead);
            if(output != null){
                fileOutputStream.write(output);
            }
        }

        byte[] output = cipher.doFinal();
        if(output != null){
            fileOutputStream.write(output);
        }

        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
