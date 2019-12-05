/*
 * CommandManager.java
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import acme.components.CustomCommand;
import acme.framework.entities.UserRole;
import acme.framework.services.AbstractService;

public class CommandManager<R extends UserRole, E> {

	// Internal state ---------------------------------------------------------

	private Map<Command, AbstractService<R, E>>	serviceMap;
	private Map<Command, BasicCommand>			typeMap;


	// Constructors -----------------------------------------------------------

	public CommandManager() {
		this.serviceMap = new HashMap<Command, AbstractService<R, E>>();
		this.typeMap = new HashMap<Command, BasicCommand>();
	}

	// Business methods -------------------------------------------------------

	public boolean isRegistered(final Command command) {
		assert command != null;

		boolean result;

		result = this.serviceMap.containsKey(command);

		return result;
	}

	public void addBasicCommand(final BasicCommand basicCommand, final AbstractService<R, E> service) {
		assert basicCommand != null;
		assert !this.isRegistered(basicCommand);
		assert service != null;

		this.serviceMap.put(basicCommand, service);
		this.typeMap.put(basicCommand, basicCommand);
	}

	public void addCustomCommand(final CustomCommand customCommand, final BasicCommand basicCommand, final AbstractService<R, E> service) {
		assert customCommand != null;
		assert !this.isRegistered(customCommand);
		assert basicCommand != null;
		assert service != null;

		this.serviceMap.put(customCommand, service);
		this.typeMap.put(customCommand, basicCommand);
	}

	public BasicCommand getBaseCommand(final Command command) {
		assert command != null;
		assert this.isRegistered(command);

		BasicCommand result;

		result = this.typeMap.get(command);

		return result;
	}

	public Collection<Command> getSubCommands(final BasicCommand basicCommand) {
		assert basicCommand != null;
		assert this.isRegistered(basicCommand);

		Collection<Command> result;
		Command key;
		BasicCommand value;

		result = new ArrayList<Command>();
		for (Entry<Command, BasicCommand> entry : this.typeMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value.equals(basicCommand)) {
				result.add(key);
			}
		}

		return result;
	}

	public AbstractService<R, E> getService(final Command command) {
		assert command != null;
		assert this.isRegistered(command);

		AbstractService<R, E> result;

		result = this.serviceMap.get(command);

		return result;
	}

}
