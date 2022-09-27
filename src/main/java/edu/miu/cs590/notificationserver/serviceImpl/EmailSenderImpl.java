package edu.miu.cs590.notificationserver.serviceImpl;

import edu.miu.cs590.notificationserver.dto.EmailDto;
import edu.miu.cs590.notificationserver.entity.Email;
import edu.miu.cs590.notificationserver.mapper.EmailMapper;
import edu.miu.cs590.notificationserver.repository.EmailRepository;
import edu.miu.cs590.notificationserver.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;


@Component
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public boolean   sendEmail(EmailDto emailDto) {

        JavaMailSender emailSender = configureJavaMail();
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setText(emailDto.getMessage(), true);
            helper.setFrom(emailDto.getFrom());
            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            emailSender.send(mimeMessage);
            Email email = emailMapper.dtoToEmail(emailDto);
            email.setSendDate(LocalDateTime.now());
            emailRepository.save(email);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private JavaMailSender configureJavaMail() {
        JavaMailSenderImpl mailSender = setJavaMailSender();
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    private JavaMailSenderImpl setJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("souravshrestha40@gmail.com");
        mailSender.setPassword("esscdmkhspdzxiyz");
        return mailSender;
    }

}

