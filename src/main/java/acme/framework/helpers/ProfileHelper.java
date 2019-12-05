/*
 * ProfileHelper.java
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

public class ProfileHelper {

	// Business methods -------------------------------------------------------

	public static void setProfiles(final String... profiles) {
		assert !StringHelper.someBlank(profiles);

		Locale locale;
		String profileProperty;

		locale = new Locale("en");
		Locale.setDefault(locale);

		profileProperty = StringHelper.toString(profiles, ",", "");
		System.setProperty("spring.profiles.active", profileProperty);
	}

}
