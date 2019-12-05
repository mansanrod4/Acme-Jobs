/*
 * LogbackCustomLayout.java
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

import acme.framework.helpers.StringHelper;
import acme.framework.helpers.ThrowableHelper;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.LayoutBase;

public class LogbackCustomLayout extends LayoutBase<ILoggingEvent> {

	// LayoutBase<ILoggingEvent> interface ------------------------------------

	@Override
	public String doLayout(final ILoggingEvent event) {
		assert event != null;

		final StringBuilder result;
		IThrowableProxy iterator;
		String message, title, description, paragraph;

		result = new StringBuilder();

		result.append(event.getLevel());
		result.append(" [");
		result.append(event.getLoggerName());
		result.append("] ");

		iterator = event.getThrowableProxy();
		message = event.getMessage();
		if (iterator == null && !StringHelper.isBlank(message)) {
			description = ThrowableHelper.formatText(message);
			result.append(description);
			result.append(System.lineSeparator());
		}

		while (iterator != null) {
			title = iterator.getStackTraceElementProxyArray()[0].toString();
			description = iterator.getMessage();
			description = description != null ? description : iterator.getClassName();
			paragraph = ThrowableHelper.formatParagraph(title, description);
			result.append(paragraph);
			iterator = iterator.getCause();
		}

		return result.toString();
	}

}
