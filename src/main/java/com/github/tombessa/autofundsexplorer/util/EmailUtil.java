/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tombessa.autofundsexplorer.util;

import com.github.tombessa.autofundsexplorer.util.exception.BusinessException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;

/**
 * @author antonyonne.bessa
 */
public class EmailUtil {

    /**
     * Utility method to send simple HTML email
     *
     * @param session
     * @param fromEmail
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String fromEmail, String toEmail, String toName, String subject, String body, Boolean isHTML, List<String> filesNameAttachment, List<File> attachments) {
        if (filesNameAttachment != null)
            if (filesNameAttachment.size() != attachments.size())
                return;
        try {
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromEmail));
            if (toEmail.contains(Constants.PONTO_VIRGULA)) {
                String[] emails = toEmail.split(Constants.PONTO_VIRGULA);
                String[] names = toName.split(Constants.PONTO_VIRGULA);

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emails[0], names[0]));
                for (int index = 0; index < emails.length; index++) {
                    if (emails.length != names.length)
                        if (names.length > 0)
                            message.addRecipient(Message.RecipientType.CC, new InternetAddress(emails[index], names[0]));
                        else
                            message.addRecipient(Message.RecipientType.CC, new InternetAddress(emails[index]));
                    else if (names.length > 0)
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(emails[index], names[index]));
                    else
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(emails[index]));
                }
            } else {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            }

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            if (!isHTML) messageBodyPart.setText(body);
            else messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            for (int index = 0; index < filesNameAttachment.size(); index++) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachments.get(index));
                attachmentPart.setFileName(filesNameAttachment.get(index));
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(Constants.ERRO_EMAIL_1, e);
        }
    }
}
