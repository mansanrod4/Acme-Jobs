/*
 * Model.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acme.framework.helpers.ConversionHelper;
import acme.framework.helpers.StringHelper;

public class Model {

	// Internal state ---------------------------------------------------------

	private List<Map<String, Object>>	list;
	private Map<String, Object>			current;


	// Constructors -----------------------------------------------------------

	public Model() {
		this.list = new ArrayList<Map<String, Object>>();
		this.append();
	}

	// Business methods -------------------------------------------------------

	public int size() {
		int result;

		result = this.current.isEmpty() ? this.list.size() - 1 : this.list.size();

		return result;
	}

	public Map<String, Object> get(final int index) {
		assert index >= 0 && index < this.size();

		Map<String, Object> result;

		result = this.list.get(index);

		return result;
	}

	public void append() {
		this.current = new HashMap<String, Object>();
		this.list.add(this.current);
	}

	public void append(final Map<String, Object> map) {
		assert map != null;

		if (!this.current.isEmpty()) {
			this.append();
		}
		this.current.putAll(map);
	}

	public void append(final Model model) {
		assert model != null;

		Map<String, Object> other;

		for (int index = 0; index < model.size(); index++) {
			other = model.get(index);
			if (!this.current.isEmpty()) {
				this.append();
			}
			this.current.putAll(other);
		}
	}

	public Map<String, Object> getCurrent() {
		return this.current;
	}

	public void setCurrent(final int index) {
		assert index >= 0 && index < this.size();

		this.current = this.list.get(index);
	}

	public boolean hasAttribute(final String name) {
		assert !StringHelper.isBlank(name);

		boolean result;

		result = this.current.containsKey(name);

		return result;
	}

	public Object getAttribute(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.hasAttribute(name);

		Object result;

		result = this.current.get(name);

		return result;
	}

	private <T> T getAttribute(final String name, final Class<T> clazz) {
		assert !StringHelper.isBlank(name);
		assert clazz != null;
		assert this.hasAttribute(name);

		T result;
		Object value;

		value = this.current.get(name);
		assert value == null || ConversionHelper.canConvert(value, clazz);
		result = ConversionHelper.convert(value, clazz);

		return result;
	}

	public Integer getInteger(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.hasAttribute(name);

		Integer result;

		result = this.getAttribute(name, Integer.class);

		return result;
	}

	public Double getDouble(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.hasAttribute(name);

		Double result;

		result = this.getAttribute(name, Double.class);

		return result;
	}

	public String getString(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.hasAttribute(name);

		String result;

		result = this.getAttribute(name, String.class);

		return result;
	}

	public Boolean getBoolean(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.hasAttribute(name);

		boolean result;

		result = this.getAttribute(name, Boolean.class);

		return result;
	}

	public Date getDate(final String name) {
		assert !StringHelper.isBlank(name);
		assert this.hasAttribute(name);

		Date result;

		result = this.getAttribute(name, Date.class);

		return result;
	}

	public void setAttribute(final String name, final Object value) {
		assert !StringHelper.isBlank(name);
		// assert value is nullable

		this.current.put(name, value);
	}

}
