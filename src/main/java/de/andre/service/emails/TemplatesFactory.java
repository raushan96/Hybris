package de.andre.service.emails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.andre.utils.HybrisConstants.FORGOT_PASSWORD_EMAIL;
import static de.andre.utils.HybrisConstants.WELCOME_EMAIL;

public abstract class TemplatesFactory {
	private static final Logger log = LoggerFactory.getLogger(TemplatesFactory.class);

	protected abstract EmailTemplateInfo createForgotPasswordTemplate();
	protected abstract EmailTemplateInfo createWelcomeTemplate();

	public EmailTemplateInfo lookupTemplate(final String pName) {
		EmailTemplateInfo emailTemplate = null;

		switch (pName) {
			case FORGOT_PASSWORD_EMAIL:
				emailTemplate = createForgotPasswordTemplate();
				break;
			case WELCOME_EMAIL:
				emailTemplate = createWelcomeTemplate();
				break;
			default:
				log.error("Unexpected email type.");
				break;
		}
		return emailTemplate;
	}
}
