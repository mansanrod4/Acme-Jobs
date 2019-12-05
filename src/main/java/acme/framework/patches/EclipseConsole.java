/*
 * EclipseConsole.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.patches;

import java.io.PrintStream;

public class EclipseConsole {

	// Internal state ---------------------------------------------------------

	private static boolean isFixed = false;


	// Business methods -------------------------------------------------------

	public static void fix() {
		// HINT: introduces a 200ms delay into the 'System.err' or 'System.out' OutputStreams
		// HINT+ every time the output switches from one to the other. This is enough to prevent
		// HINT+  the Eclipse console from showing the output of the two streams out of order.

		EclipseStream out, err;

		try {
			if (!EclipseConsole.isFixed) {
				EclipseConsole.isFixed = true;
				out = new EclipseStream(System.out);
				err = new EclipseStream(System.err);
				System.setOut(new PrintStream(out, true, "utf-8"));
				System.setErr(new PrintStream(err, true, "utf-8"));
			}
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}

}
