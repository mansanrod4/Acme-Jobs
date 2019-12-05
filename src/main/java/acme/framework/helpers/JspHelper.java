/*
 * JspHelper.java
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

import java.sql.Date;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import acme.framework.datatypes.Money;

public class JspHelper {

	// Business methods -------------------------------------------------------

	public static String getRequestUrl(final HttpServletRequest request) {
		assert request != null;

		String result;
		String scheme, server, port, uri, query;

		scheme = request.getScheme();
		server = request.getServerName();
		port = String.valueOf(request.getServerPort());
		uri = (String) request.getAttribute("javax.servlet.forward.request_uri");
		query = (String) request.getAttribute("javax.servlet.forward.query_string");
		result = String.format("%s://%s:%s%s%s", scheme, server, port, uri, StringHelper.isBlank(query) ? "" : "?" + query);

		return result;
	}

	public static String getBaseUrl(final HttpServletRequest request) {
		assert request != null;

		String result;
		String scheme, server, port, context;

		scheme = request.getScheme();
		server = request.getServerName();
		port = String.valueOf(request.getServerPort());
		context = request.getContextPath();
		result = String.format("%s://%s:%s%s/", scheme, server, port, context);

		return result;
	}

	public static String computeDataSort(final Object value) {
		String result;
		SimpleDateFormat simpleDateFormat;
		Date date;
		String criteria;

		if (value instanceof Date) {
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			criteria = simpleDateFormat.format((Date) value);
		} else if (value instanceof Timestamp) {
			date = new Date(((Timestamp) value).getTime());
			simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			criteria = simpleDateFormat.format(date);
		} else if (value instanceof Money) {
			criteria = String.format("%s %010.2f", ((Money) value).getCurrency(), ((Money) value).getAmount());
		} else if (value instanceof Double) {
			criteria = String.format("%010.2f", (Double) value);
		} else if (value instanceof Integer) {
			criteria = String.format("%010d", (Integer) value);
		} else {
			criteria = null;
		}

		result = criteria == null ? "" : String.format("data-sort=\"%s\"", criteria);

		return result;
	}

	public static String computeDataText(final Object value, final String format) {
		// assert value is nullable
		assert !StringHelper.isBlank(format);

		String result;
		MessageFormat formatter;

		if (format.equals("{0}") && ConversionHelper.canConvert(value, String.class)) {
			result = ConversionHelper.convert(value, String.class);
		} else {
			formatter = new MessageFormat(format);
			result = formatter.format(value);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static void updateDatatableColumns(final HttpServletRequest request, final Map<String, Object> column) {
		Collection<Map<String, Object>> columns;

		columns = (Collection<Map<String, Object>>) request.getAttribute("__data_table_columns");
		columns.add(column);
	}

}
