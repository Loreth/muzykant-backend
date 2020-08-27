package pl.kamilprzenioslo.muzykant.service;

import java.util.UUID;
import javax.mail.MessagingException;

public interface MailService {

  void sendMail(String to, String subject, String content, boolean isHtmlContent)
      throws MessagingException;

  void sendConfirmationMail(String to, UUID confirmationToken) throws MessagingException;
}
