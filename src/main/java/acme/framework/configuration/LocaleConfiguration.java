/*
 * LocaleConfiguration.java
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

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

	// WebMvcConfigurer interface ---------------------------------------------

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor;

		interceptor = this.localeChangeInterceptor();
		registry.addInterceptor(interceptor);
	}

	// Beans ------------------------------------------------------------------

	@Bean
	public LocaleContextResolver localeResolver() {
		final CookieLocaleResolver

		result = new CookieLocaleResolver();
		result.setDefaultLocale(Locale.ENGLISH);
		result.setCookieName("language");
		result.setCookieMaxAge((int) TimeUnit.DAYS.toSeconds(31));

		return result;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor result;

		result = new LocaleChangeInterceptor();
		result.setParamName("language");

		return result;
	}

}
