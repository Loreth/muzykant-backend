package pl.kamilprzenioslo.muzykant.service.implementations;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.service.MailService;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

  private final JavaMailSender mailSender;

  @Override
  public void sendMail(String to, String subject, String content, boolean isHtmlContent)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    //    mimeMessageHelper.setFrom("Muzykant");
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(content, isHtmlContent);
    mailSender.send(mimeMessage);
  }
}
