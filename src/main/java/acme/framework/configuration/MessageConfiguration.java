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

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import acme.framework.helpers.MessageHelper;
import acme.framework.patches.ExtendedMessageSource;

@Configuration
public class MessageConfiguration implements WebMvcConfigurer {

	// Beans ------------------------------------------------------------------

	@Bean
	public MessageSource messageSource() {
		ExtendedMessageSource result;

		result = MessageHelper.buildMessageSource();
		result.setUseCodeAsDefaultMessage(true);

		return result;
	}

}
