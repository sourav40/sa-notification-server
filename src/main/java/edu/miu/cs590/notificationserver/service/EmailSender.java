package edu.miu.cs590.notificationserver.service;

import edu.miu.cs590.notificationserver.dto.EmailDto;

public interface EmailSender {

    boolean sendEmail(EmailDto emailDto);
}
