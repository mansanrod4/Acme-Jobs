/*
 * AuthenticationConfiguration.java
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

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import acme.framework.services.AuthenticationService;
import acme.framework.utilities.ExtendedSecurityExpressionHandler;
import acme.framework.utilities.RememberMeLogoutHandler;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticationService authenticationBridgeService;


	// WebSecurityConfigurerAdapter interface ---------------------------------

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		super.configure(http);

		http.authorizeRequests() //
			.anyRequest().permitAll();

		http.formLogin() //
			.permitAll() //
			.loginPage("/master/sign-in") //
			.usernameParameter("username") //
			.passwordParameter("password") //
			.loginProcessingUrl("/master/sign-in") //
			.failureUrl("/master/sign-in?error") //
			.defaultSuccessUrl("/");

		http.logout() //
			.permitAll() //			
			.logoutRequestMatcher(new AntPathRequestMatcher("/master/sign-out")) //
			.logoutSuccessUrl("/master/welcome") //
			.invalidateHttpSession(true) //
			.clearAuthentication(true) //
			.deleteCookies("JSESSIONID", "remember") //
			.addLogoutHandler(new RememberMeLogoutHandler());

		// TODO: move the key to the configuration file!
		http.rememberMe() //
			.key("$tr0ng-K3y!") //
			.rememberMeParameter("remember") //
			.rememberMeCookieName("remember") //
			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(31)) //
			.userDetailsService(this.authenticationBridgeService);
	}

	// Beans ------------------------------------------------------------------

	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder result;

		result = new BCryptPasswordEncoder(5);

		return result;
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider result;

		result = new DaoAuthenticationProvider();
		result.setUserDetailsService(this.authenticationBridgeService);
		result.setPasswordEncoder(this.passwordEncoder());

		return result;
	}

	@Bean
	public ExtendedSecurityExpressionHandler webSecurityExpressionHandler() {
		ExtendedSecurityExpressionHandler result;

		result = new ExtendedSecurityExpressionHandler();

		return result;
	}

}
