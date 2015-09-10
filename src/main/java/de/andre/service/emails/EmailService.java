package de.andre.service.emails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class EmailService {
	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	private final JavaMailSender mailSender;

	private final TemplateEngine templateEngine;

	private final TemplatesFactory templatesFactory;

	private String defaultFrom;

	public EmailService(final JavaMailSender mailSender, final TemplateEngine templateEngine,
						final TemplatesFactory templatesFactory) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.templatesFactory = templatesFactory;
	}

	@Async
	public void sendEmail(final EmailTemplateInfo pTemplateInfo) throws IOException, MessagingException {
		log.debug("Sending {0} email.", pTemplateInfo.getTemplateName());

		mailSender.send(pTemplateInfo.createMessage());
	}

	public void sendEmail(final String templateName, final Map<String, Object> pParams, final String pMessageTo) throws IOException, MessagingException {
		sendEmail(templateName, pParams, pMessageTo, false, (MultipartFile) null);
	}

	@Async
	public void sendEmail(final String templateName, final Map<String, Object> pParams, final String pMessageTo,
						  boolean persist, final MultipartFile... pAttachments) throws IOException, MessagingException {
		log.debug("Creating new {0} email.", templateName);
		EmailTemplateInfo emailTemplate = templatesFactory.lookupTemplate(templateName);

		if (emailTemplate != null) {
			emailTemplate.setMessageTo(pMessageTo);
			emailTemplate.setTemplateParams(pParams);

			for (final MultipartFile attachment : pAttachments) {
				emailTemplate.addAttachment(attachment);
			}

			if (persist) {
				log.debug("Persisting email {0}", emailTemplate.getTemplateName());
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
