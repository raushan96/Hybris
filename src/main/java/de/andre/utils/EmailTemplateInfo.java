package de.andre.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EmailTemplateInfo {
	private static final Logger log = LoggerFactory.getLogger(EmailTemplateInfo.class);

	private Map<String, Object> templateParams = new HashMap<>();

	private String templateUrl;
	private String messageFrom;
	private String messageSubject;
	private String encoding;
	private MimeMessage mimeMessage;

	private String messageTo;
	private Locale locale;

	private TemplateEngine templateEngine;

	public MimeMessageHelper createMessage() throws MessagingException {
		final Context ctx = new Context(locale != null ? locale : LocaleContextHolder.getLocale());
		ctx.setVariables(templateParams);

		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, encoding);
		message.setFrom(messageFrom);
		message.setSubject(messageSubject);
		message.setTo(messageTo);

		final String htmlContent = templateEngine.process(templateUrl, ctx);
		message.setText(htmlContent, true);

		return message;
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
}
