package de.andre.service.account;

import de.andre.utils.EmailTemplateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public abstract class EmailService {
	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	private final JavaMailSender mailSender;

	private final TemplateEngine templateEngine;

	private String defaultFrom;

	public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	@Async
	public void sendEmail(final EmailTemplateInfo pTemplateInfo) throws IOException, MessagingException {
		log.debug("Sending {0} email.", pTemplateInfo.getTemplateUrl());

		mailSender.send(pTemplateInfo.createMessage());
	}

	@Async
	public void sendEmail(final Map<String, Object> pParams, final String pMessageTo) throws IOException, MessagingException {
		log.debug("Creating new email.");
		EmailTemplateInfo template = createForgotPasswordTemplate();

		template.setMessageTo(pMessageTo);
		template.setTemplateParams(pParams);

//		mailSender.send(pTemplateInfo.createMessage());
	}

	public void sendSimpleEmail(final String pRecipientName, final String pRecipientEmail, final Locale locale)
			throws MessagingException {

		final Context ctx = new Context(locale);
		ctx.setVariable("name", pRecipientName);
		ctx.setVariable("date", new Date());

		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject("Simple email");
		message.setFrom("lox@gmail.com");
		message.setTo(pRecipientEmail);

		final String htmlContent = templateEngine.process("email-simple.html", ctx);
		message.setText(htmlContent, true);

		mailSender.send(mimeMessage);
	}

	public void sendMailWithAttachment(final String recipientName, final String recipientEmail, final String attachmentFileName,
			final byte[] attachmentBytes, final String attachmentContentType, final Locale locale)
			throws MessagingException {

		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setSubject("Example HTML email with attachment");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		final String htmlContent = templateEngine.process("email-withattachment.html", ctx);
		message.setText(htmlContent, true );

		final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
		message.addAttachment(attachmentFileName, attachmentSource, attachmentContentType);

		mailSender.send(mimeMessage);
	}

	public void sendMailWithInline(final String recipientName, final String recipientEmail, final String imageResourceName,
			final byte[] imageBytes, final String imageContentType, final Locale locale)
			throws MessagingException {

		final Context ctx = new Context(locale);
		ctx.setVariable("name", recipientName);
		ctx.setVariable("subscriptionDate", new Date());
		ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
		ctx.setVariable("imageResourceName", imageResourceName);

		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message =
				new MimeMessageHelper(mimeMessage, true, "UTF-8");
		message.setSubject("Example HTML email with inline image");
		message.setFrom("thymeleaf@example.com");
		message.setTo(recipientEmail);

		final String htmlContent = templateEngine.process("email-inlineimage.html", ctx);
		message.setText(htmlContent, true);

		// Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
		final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
		message.addInline(imageResourceName, imageSource, imageContentType);

		mailSender.send(mimeMessage);
	}

	public String getDefaultFrom() {
		return defaultFrom;
	}

	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom = defaultFrom;
	}

	protected abstract EmailTemplateInfo createForgotPasswordTemplate();

}
