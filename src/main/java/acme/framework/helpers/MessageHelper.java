/*
 * MessageHelper.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.helpers;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import acme.framework.patches.ExtendedMessageSource;

public class MessageHelper {

	// Business methods -------------------------------------------------------

	public static String getMessage(final String code, final Object[] arguments, final String defaultMessage, final Locale locale) {
		assert !StringHelper.isBlank(code);
		// assert arguments is nullable
		assert !StringHelper.isBlank(defaultMessage);
		assert locale != null;

		String result;
		MessageSource messageSource;

		messageSource = FactoryHelper.getBean(MessageSource.class);
		result = messageSource.getMessage(code, arguments, defaultMessage, locale);
		result = result.trim();

		return result;
	}

	public static String getMessage(final String code, final Object[] arguments) {
		assert !StringHelper.isBlank(code);
		// assert arguments is nullable

		String result;
		MessageSource messageSource;
		Locale locale;

		messageSource = FactoryHelper.getBean(MessageSource.class);
		locale = LocaleContextHolder.getLocale();
		result = messageSource.getMessage(code, arguments, code, locale);
		result = result.trim();

		return result;
	}

	public static String getMessage(final String code) {
		assert !StringHelper.isBlank(code);

		String result;
		MessageSource messageSource;
		Locale locale;

		messageSource = FactoryHelper.getBean(MessageSource.class);
		locale = LocaleContextHolder.getLocale();
		result = messageSource.getMessage(code, null, code, locale);
		result = result.trim();

		return result;
	}

	// TODO: relocate this method somewhere closer to the 'framework.configuration' package. Note that
	// TODO+ we need two different message sources: the global one, which is appropriate for almost everything,
	// TODO+ and the validation one, which is appropriate for Validators only.  The only difference is that the
	// TODO+ global one can ignore codes that are not found in the i18n bundles, whereas the validation one cannot.

	public static ExtendedMessageSource buildMessageSource() {
		ExtendedMessageSource result;

		result = new ExtendedMessageSource();
		result.setDefaultEncoding("utf-8");
		result.setFallbackToSystemLocale(true);
		result.setCacheMillis(-1);
		result.setBasenames( //
			"/WEB-INF/messages/*.messages", //
			"/WEB-INF/tags/*.messages", //
			"/META-INF/views/**/*.messages");

		return result;
	}

}
