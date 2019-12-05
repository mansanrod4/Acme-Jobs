/*
 * MvcConfiguration.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import acme.framework.utilities.LocalisedDateFormatter;
import acme.framework.utilities.LocalisedDoubleFormatter;
import acme.framework.utilities.LocalisedMoneyFormatter;

@Configuration
public class ConversionConfiguration implements WebMvcConfigurer {

	// Beans ------------------------------------------------------------------

	@Bean
	public FormattingConversionService conversionService() {
		DefaultFormattingConversionService result;
		LocalisedDateFormatter dateFormatter;
		LocalisedMoneyFormatter moneyFormatter;
		LocalisedDoubleFormatter doubleFormatter;

		result = new DefaultFormattingConversionService(true);

		dateFormatter = new LocalisedDateFormatter();
		result.addFormatter(dateFormatter);

		moneyFormatter = new LocalisedMoneyFormatter();
		result.addFormatter(moneyFormatter);

		doubleFormatter = new LocalisedDoubleFormatter();
		result.addFormatter(doubleFormatter);

		return result;
	}

}
