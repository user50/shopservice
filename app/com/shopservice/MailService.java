package com.shopservice;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MailService {

    private static final List<String> OWNERS = Arrays.asList("jmen7070@gmail.com");

    private static MailService instance;

    private MailService() {
    }

    public static MailService getInstance() {
        if (instance == null)
            instance = new MailService();

        return instance;
    }

    public void report(String subject, String body, List<String> recipients) throws MessagingException {
        final String username = "momentomori007@gmail.com";
        final String password = "23111989kjpjd";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients.toString().replace("[", "").replace("]", "")));

            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void report(Throwable throwable) {
        try {
            report(throwable.getMessage(), getStackTrace(throwable), OWNERS);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public String getStackTrace(Throwable t) {
        StringWriter stringWritter = new StringWriter();
        PrintWriter printWritter = new PrintWriter(stringWritter, true);
        t.printStackTrace(printWritter);
        printWritter.flush();
        stringWritter.flush();

        return stringWritter.toString();
    }
}
