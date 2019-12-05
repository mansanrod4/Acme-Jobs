/*
 * EclipseStream.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes.  The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.framework.patches;

import java.io.IOException;
import java.io.OutputStream;

public class EclipseStream extends OutputStream {

	// Constructors -----------------------------------------------------------

	public EclipseStream(final OutputStream originalStream) {
		assert originalStream != null;

		this.target = originalStream;
	}


	// Internal state ---------------------------------------------------------

	private final OutputStream	target;
	private static OutputStream	lastStream;


	// OutputStream interface -------------------------------------------------

	@Override
	public void close() throws IOException {
		this.target.close();
	}

	@Override
	public void flush() throws IOException {
		this.target.flush();
	}

	@Override
	public void write(final byte[] buffer) throws IOException {
		assert buffer != null;

		this.swap();
		this.target.write(buffer);
	}

	@Override
	public void write(final byte[] buffer, final int offset, final int length) throws IOException {
		assert buffer != null;
		assert offset >= 0 && offset < buffer.length;
		assert offset + length - 1 < buffer.length;

		this.swap();
		this.target.write(buffer, offset, length);
	}

	@Override
	public void write(final int datum) throws IOException {
		this.swap();
		this.target.write(datum);
	}

	// Ancillary methods ------------------------------------------------------
	private void swap() throws IOException {
		if (EclipseStream.lastStream != this && EclipseStream.lastStream != null) {
			EclipseStream.lastStream.flush();
			try {
				Thread.sleep(250);
			} catch (final InterruptedException oops) {
			}
		}
		EclipseStream.lastStream = this;
	}
}
