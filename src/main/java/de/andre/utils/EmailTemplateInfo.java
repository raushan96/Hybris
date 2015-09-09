package de.andre.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.style.ToStringCreator;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EmailTemplateInfo {
	private static final Logger log = LoggerFactory.getLogger(EmailTemplateInfo.class);
	private static final int MAX_ATTACHMENTS = 4;

	private Map<String, Object> templateParams = new HashMap<>();

	private String templateUrl;
	private String messageFrom;
	private String messageSubject;
	private String encoding;
	private MimeMessage mimeMessage;

	private String messageTo;
	private Locale locale;

	private ArrayList<MultipartFile> multipartFiles;

	private TemplateEngine templateEngine;

	public MimeMessageHelper createMessage() throws MessagingException, IOException {
		final Context ctx = new Context(locale != null ? locale : LocaleContextHolder.getLocale());
		ctx.setVariables(templateParams);

		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, encoding);
		message.setFrom(messageFrom);
		message.setSubject(messageSubject);
		message.setTo(messageTo);

		final String htmlContent = templateEngine.process(templateUrl, ctx);
		message.setText(htmlContent, true);

		addAttachments(message);

		log.debug("Sending email: " + message.toString());

		return message;
	}

	private void addAttachments(final MimeMessageHelper pMessage) throws IOException, MessagingException {
		for (final MultipartFile multipartFile : multipartFiles) {
			final InputStreamSource attachmentSource = new ByteArrayResource(multipartFile.getBytes());
			if ("file".equals(multipartFile.getContentType())) {
				pMessage.addAttachment(
						multipartFile.getOriginalFilename(),
						attachmentSource,
						multipartFile.getContentType());
			} else if ("image".equals(multipartFile.getContentType())) {
				pMessage.addInline(
						multipartFile.getName(),
						attachmentSource,
						multipartFile.getContentType());
			} else {
				log.warn("Unexpected multipart type: " + multipartFile.getContentType());
			}
		}
	}

	public void addMultipartFile(final MultipartFile pMultipartFile) {
		if (pMultipartFile == null)
			throw new IllegalArgumentException("Multipart file cannot be null");

		if (multipartFiles == null) {
			multipartFiles = new ArrayList<>(MAX_ATTACHMENTS);
			multipartFiles.add(pMultipartFile);
		} else {
			if (multipartFiles.size() < MAX_ATTACHMENTS) {
				multipartFiles.add(pMultipartFile);
			} else {
				log.warn("Exceeded maximum attachments number, skipping");
			}
		}
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("template url", this.templateUrl)
				.append("message to", this.messageTo)
				.append("message from", this.messageFrom)
				.append("message subject", this.messageSubject)
				.toString();
	}

	public Map<String, Object> getTemplateParams() {
		return templateParams;
	}

	public void setTemplateParams(Map<String, Object> templateParams) {
		this.templateParams = templateParams;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
}
