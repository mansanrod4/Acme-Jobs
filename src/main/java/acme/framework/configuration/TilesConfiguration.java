/*
 * TilesConfiguration.java
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
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesConfiguration {

	// Beans ------------------------------------------------------------------

	@Bean
	public ViewResolver viewResolver() {
		final TilesViewResolver result;

		result = new TilesViewResolver();
		result.setViewClass(TilesView.class);

		return result;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer result;
		String[] definitions;

		definitions = new String[] {
			"/META-INF/views/**/tiles.xml"
		};

		result = new TilesConfigurer();
		result.setDefinitions(definitions);

		return result;
	}

}
