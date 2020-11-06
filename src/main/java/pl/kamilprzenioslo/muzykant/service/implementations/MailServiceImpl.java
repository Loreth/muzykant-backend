package pl.kamilprzenioslo.muzykant.service.implementations;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.service.MailService;

@Service
public class MailServiceImpl implements MailService {

  private static final String CONFIRM_MAIL_SUBJECT = "Potwierdź rejestrację";
  private final String mailFromUsername;
  private final String mailConfirmationUrl;
  private final String confirmMailContent;
  private final JavaMailSender mailSender;

  public MailServiceImpl(
      @Value("${spring.mail.username}") String mailFromUsername,
      JavaMailSender mailSender,
      @Value("${app.emailConfirmationTokenExpirationH}") int emailConfirmationTokenExpirationH,
      @Value("${app.mailConfirmationUrl}") String mailConfirmationUrl) {
    this.mailFromUsername = mailFromUsername;
    this.mailSender = mailSender;
    this.mailConfirmationUrl = mailConfirmationUrl;

    confirmMailContent =
        "Witaj w naszym muzycznym gronie! <br><br>"
            + "Aby potwierdzić rejestrację w serwisie Muzykant, kliknij poniższy link. <br><br>"
            + "Link wygasa po "
            + emailConfirmationTokenExpirationH
            + " godzinach. <br>"
            + "Po upływie tego czasu konieczna będzie ponowna rejestracja. <br><br>";
  }

  @Async
  @Override
  public void sendMail(String to, String subject, String content, boolean isHtmlContent)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    try {
      mimeMessageHelper.setFrom(new InternetAddress(mailFromUsername, "Muzykant"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(content, isHtmlContent);
    mailSender.send(mimeMessage);
  }

  @Async
  @Override
  public void sendConfirmationMail(String to, UUID confirmationToken) throws MessagingException {
    sendMail(
        to,
        CONFIRM_MAIL_SUBJECT,
        confirmMailContent
            + wrapInAnchorTag(mailConfirmationUrl + "?token=" + confirmationToken.toString()),
        true);
  }

  private String wrapInAnchorTag(String text) {
    return "<a href=\"" + text + "\">" + text + "</a>";
  }
}
