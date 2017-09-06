package com.company;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class Main {

    public static void main(String[] args) throws Exception{
	// write your code here
        File f = new File("C:\\Users\\Dean\\Desktop\\4717415.docx");

        String fileOneExtension = FilenameUtils.getExtension(f.toString());

        AESEncryption encryption = new AESEncryption();

        String password = "litmus90";

        encryption.encrypt(f, password);

        File encryptedFile = new File("C:\\Users\\Dean\\Desktop\\encryptedFile.enc");

        if(encryption.isEncrypted(encryptedFile)){
            encryption.decrypt(encryptedFile, password);
        }else {
            System.out.println("Nothing to do here");
        }

//        BlowfishEncryption blowfishEncryption = new BlowfishEncryption();
//        File f = new File("C:\\Users\\Dean\\Desktop\\Student Information.pdf");
//
//        String filePath = f.getParent();
//        String filename = FilenameUtils.getBaseName(f.toString());
//        String fileExtension = FilenameUtils.getExtension(f.toString());
//
//
//        File encryptedFile = new File(filePath+"\\"+filename+"encrypted.enc");
//
//        File decryptedFile = new File(filePath+"\\"+filename+"."+fileExtension);
//
//        blowfishEncryption.encrypt(f, encryptedFile);
//
//        blowfishEncryption.decrypt(encryptedFile, decryptedFile);

        System.out.println("Everything is OK");
    }
}
