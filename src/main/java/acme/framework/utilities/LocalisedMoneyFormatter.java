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

import acme.framework.datatypes.Money;
import acme.framework.helpers.MessageHelper;

public class LocalisedMoneyFormatter implements Formatter<Money> {

	// Constructors -----------------------------------------------------------

	public LocalisedMoneyFormatter() {
		super();
	}

	// Formatter<Money> interface ---------------------------------------------

	@Override
	public Money parse(final String text, final Locale locale) throws ParseException {
		assert text != null;
		assert locale != null;

		Money result;
		DecimalFormatSymbols symbols;
		String thousandSeparator, decimalSeparator;
		String currencyRegex, numberRegex, regex;
		Pattern pattern;
		Matcher matcher;
		String errorMessage;
		String currency, number;
		double amount;

		symbols = new DecimalFormatSymbols(locale);
		thousandSeparator = Character.toString(symbols.getGroupingSeparator());
		decimalSeparator = Character.toString(symbols.getDecimalSeparator());
		currencyRegex = "[\\p{L}\\p{Sc}]+";
		numberRegex = String.format("[+-]?(\\d+|\\d{1,3}(\\%s\\d{3})*)(\\%s\\d{1,2})?", thousandSeparator, decimalSeparator);

		regex = String.format("^((?<C1>%1$s)\\s*(?<A1>%2$s))$|^((?<A2>%2$s)\\s*(?<C2>%1$s))$", currencyRegex, numberRegex);
		pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

		matcher = pattern.matcher(text);
		if (!matcher.find()) {
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locale);
			throw new ParseException(0, errorMessage);
		} else {
			currency = matcher.group("C1");
			if (currency == null) {
				currency = matcher.group("C2");
			}
			assert currency != null;
			currency = currency.toUpperCase(locale);

			number = matcher.group("A1");
			if (number == null) {
				number = matcher.group("A2");
			}
			assert number != null;
			number = number.replace(thousandSeparator, "");
			number = number.replace(decimalSeparator, ".");
			amount = Double.valueOf(number);

			result = new Money();
			result.setAmount(amount);
			result.setCurrency(currency);
		}

		return result;
	}

	@Override
	public String print(final Money object, final Locale locale) {
		assert object != null;
		assert locale != null;

		String result;
		DecimalFormatSymbols symbols;
		DecimalFormat formatter;
		String currency, number;
		double amount;

		symbols = new DecimalFormatSymbols(locale);
		formatter = new DecimalFormat();
		formatter.setDecimalFormatSymbols(symbols);
		formatter.setDecimalFormatSymbols(symbols);
		formatter.setDecimalSeparatorAlwaysShown(true);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);

		currency = object.getCurrency();
		amount = object.getAmount();
		number = formatter.format(amount);

		if (locale.getLanguage().equals("en")) {
			result = String.format("%s %s", currency, number);
		} else {
			result = String.format("%s %s", number, currency);
		}

		return result;
	}

}
