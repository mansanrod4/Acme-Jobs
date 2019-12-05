/*
 * ConsoleReader.java
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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

public class ConsoleReader {

	// Internal state ---------------------------------------------------------

	private final InputStreamReader	stream;
	private final BufferedReader	reader;


	// Constructors -----------------------------------------------------------

	public ConsoleReader() {
		this.stream = new InputStreamReader(System.in);
		this.reader = new BufferedReader(this.stream);
	}

	// Business methods -------------------------------------------------------

	public String readCommand() throws Throwable {
		String result;
		StringBuilder buffer;
		String line;
		String prompt;
		boolean done;

		do {
			prompt = "> ";
			buffer = new StringBuilder();
			done = false;
			do {
				System.out.printf(prompt);
				line = this.reader.readLine();
				line = line.trim();
				if (line.endsWith(";")) {
					done = true;
					line = line.substring(0, line.length() - 1);
				}
				buffer.append(line);
				buffer.append(' ');
				prompt = "\t> ";
			} while (!done);
			result = StringUtils.trim(buffer.toString());
		} while (result.isEmpty());

		return result;
	}

	public String readLine() throws Throwable {
		String result;

		do {
			System.out.printf("> ");
			result = this.reader.readLine();
			result = result.trim();
		} while (result.isEmpty());

		return result;
	}

}
