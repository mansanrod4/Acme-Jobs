/*
 * LocalisedDateFormatter.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.utilities;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class LocalisedDateFormatter implements Formatter<Date> {

	// Constructors -----------------------------------------------------------

	public LocalisedDateFormatter() {
		super();
	}

	// Formatter<Date> interface ----------------------------------------------

	@Override
	public Date parse(final String text, final Locale locale) throws ParseException {
		assert text != null;
		assert locale != null;

		Date result;
		SimpleDateFormat formatter;
		ParsePosition parsePosition;
		String errorMessage;

		formatter = this.createDateFormat(locale);
		parsePosition = new ParsePosition(0);
		result = formatter.parse(text, parsePosition);

		if (parsePosition.getIndex() < text.length()) {
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locale);
			throw new ParseException(parsePosition.getIndex(), errorMessage);
		}

		return result;
	}

	@Override
	public String print(final Date object, final Locale locale) {
		assert object != null;
		assert locale != null;

		String result;
		SimpleDateFormat formatter;

		formatter = this.createDateFormat(locale);
		result = formatter.format(object);

		return result;
	}

	private SimpleDateFormat createDateFormat(final Locale locale) {
		assert locale != null;

		SimpleDateFormat result;
		String format;

		format = MessageHelper.getMessage("default.format.moment", null, "yyyy/MM/dd HH:mm", locale);
		result = new SimpleDateFormat(format);
		result.setLenient(false);

		return result;
	}

}
