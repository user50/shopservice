package com.shopservice.refreshers;

import com.shopservice.domain.ClientSettings;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by lozov on 23.06.15.
 */
public class BilasadFTPUploader {

    private static final String HOSTNAME = "bilasad.com";
    private static final String USER = "bilasadc";
    private static final String PASSWORD = "bilasad7669";
    private static final String FILENAME = "pricelist.yml";


    public BilasadFTPUploader() {
    }

    public void upload(byte[] ymlPrice) {
        FTPClient client = new FTPClient();
        ByteArrayInputStream bais = null;

        try {
            client.connect(HOSTNAME);

            String loginStatus = client.login(USER, PASSWORD) ? "Logged in to bilasad.com Successfully"
                                         : "Login  to bilasad.com fail";
            System.out.println(loginStatus);
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);

            client.changeWorkingDirectory("/public_html");

            bais = new ByteArrayInputStream(ymlPrice);

            String storingFileStatus = client.storeFile(FILENAME, bais) ? FILENAME + " has been stored Successfully"
                                                                        : "Fail when storing " + FILENAME;
            System.out.println(storingFileStatus);

            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
