package com.company;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

/**
 * Created by Dean on 9/6/2017.
 */
public class BlowfishEncryption {

    private static final String ALGORITHM = "Blowfish";
    private static String key = "litmus90";

    public void crypto(int cipherMode, File inputFile, File outputFile) throws Exception{
        Key secretKey = new SecretKeySpec(key.getBytes(),ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode, secretKey);

        FileInputStream inFile = new FileInputStream(inputFile);
        byte[] inBytes = new byte[(int) inputFile.length()];
        inFile.read(inBytes);

        byte[] outBytes = cipher.doFinal(inBytes);

        FileOutputStream outFile = new FileOutputStream(outputFile);
        outFile.write(outBytes);

        inFile.close();
        outFile.close();
    }

    public void encrypt(File inFile, File outFile) throws Exception{
        crypto(Cipher.ENCRYPT_MODE, inFile, outFile);
    }

    public void decrypt(File inFile, File outFile) throws Exception{
        crypto(Cipher.DECRYPT_MODE, inFile, outFile);
    }
}
