/*
 * LocalisedMoneyFormatter.java
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class LocalisedDoubleFormatter implements Formatter<Double> {

	// Constructors -----------------------------------------------------------

	public LocalisedDoubleFormatter() {
		super();
	}

	// Formatter<Money> interface ---------------------------------------------

	@Override
	public Double parse(final String text, final Locale locale) throws ParseException {
		assert text != null;
		assert locale != null;

		Double result;
		DecimalFormatSymbols symbols;
		String thousandSeparator, decimalSeparator;
		String regex;
		Pattern pattern;
		Matcher matcher;
		String errorMessage;
		String number;

		symbols = new DecimalFormatSymbols(locale);
		thousandSeparator = Character.toString(symbols.getGroupingSeparator());
		decimalSeparator = Character.toString(symbols.getDecimalSeparator());
		regex = String.format("^(?<N>[+-]?(\\d+|\\d{1,3}(\\%s\\d{3})*)(\\%s\\d+)?)$", thousandSeparator, decimalSeparator);
		pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

		matcher = pattern.matcher(text);
		if (!matcher.find()) {
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locale);
			throw new ParseException(0, errorMessage);
		} else {
			number = matcher.group("N");
			assert number != null;
			number = number.replace(thousandSeparator, "");
			number = number.replace(decimalSeparator, ".");

			result = Double.valueOf(number);
		}

		return result;
	}

	@Override
	public String print(final Double object, final Locale locale) {
		assert object != null;
		assert locale != null;

		String result;
		DecimalFormatSymbols symbols;
		DecimalFormat formatter;

		symbols = new DecimalFormatSymbols(locale);
		formatter = new DecimalFormat();
		formatter.setDecimalFormatSymbols(symbols);
		formatter.setDecimalFormatSymbols(symbols);
		formatter.setDecimalSeparatorAlwaysShown(true);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);

		result = formatter.format(object);

		return result;
	}

}
