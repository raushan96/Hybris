package de.andre.service.emails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    private final TemplatesFactory templatesFactory;

    private String defaultFrom;

    public EmailService(final JavaMailSender mailSender, final TemplatesFactory templatesFactory) {
        this.mailSender = mailSender;
        this.templatesFactory = templatesFactory;
    }

    @Async
    public void sendEmail(final EmailTemplateInfo pTemplateInfo) throws IOException, MessagingException {
        logger.debug("Sending {} email.", pTemplateInfo.getTemplateName());

        mailSender.send(pTemplateInfo.createMessage());
    }

    @Async
    public void sendEmail(final String templateName, final Map<String, Object> pParams, final String pMessageTo) {
        try {
            sendEmail(templateName, pParams, pMessageTo, false);
        } catch (final IOException | MessagingException e) {
            logger.error("Unexpected error sending email: ", e);
        }
    }

    @Async
    public void sendEmail(final String templateName, final Map<String, Object> pParams, final String pMessageTo,
                          boolean persist, final MultipartFile... pAttachments) throws IOException, MessagingException {
        logger.debug("Creating new {} email.", templateName);
        EmailTemplateInfo emailTemplate = templatesFactory.lookupTemplate(templateName);

        if (emailTemplate != null) {
            emailTemplate.setMessageTo(pMessageTo);
            emailTemplate.setTemplateParams(pParams);

            for (final MultipartFile attachment : pAttachments) {
                emailTemplate.addAttachment(attachment);
            }

            if (persist) {
                logger.debug("Persisting email {}", emailTemplate.getTemplateName());
                //persist logic
            }

            mailSender.send(emailTemplate.createMessage());
        }
    }

    public String getDefaultFrom() {
        return defaultFrom;
    }

    public void setDefaultFrom(String defaultFrom) {
        this.defaultFrom = defaultFrom;
    }
}
