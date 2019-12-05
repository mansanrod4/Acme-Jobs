/*
 * UniversalConverter.java
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import acme.framework.entities.DomainEntity;
import acme.framework.helpers.StringHelper;

public class UniversalConverter implements GenericConverter {

	// GenericConverter interface ---------------------------------------------

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> result;

		result = new HashSet<ConvertiblePair>();

		result.add(new ConvertiblePair(String.class, DomainEntity.class));
		result.add(new ConvertiblePair(DomainEntity.class, String.class));

		result.add(new ConvertiblePair(String.class, Collection.class));
		result.add(new ConvertiblePair(Collection.class, String.class));

		return result;
	}

	@Override
	public Object convert(final Object source, final TypeDescriptor sourceType, final TypeDescriptor targetType) {
		// source is nullable
		assert sourceType != null;
		assert targetType != null;

		Object result;

		try {
			if (source == null) {
				result = null;
			} else if (sourceType.getObjectType().equals(String.class)) {
				result = this.decode((String) source);
			} else {
				result = this.encode(source);
			}
		} catch (final Throwable oops) {
			return new RuntimeException(oops);
		}

		return result;
	}

	private Object decode(final String text) throws ClassNotFoundException, IOException {
		assert !StringHelper.isBlank(text);

		Object result;
		String clearText;
		byte[] data;
		InputStream inputStream;
		ObjectInputStream objectStream;
		TextEncryptor encryptor;

		// TODO: move the key to the configuration file!
		// TODO: make the encryptor and the base64 encoder static
		encryptor = Encryptors.text("$tr0ng-K3y!", "aabbccdd");
		clearText = encryptor.decrypt(text);

		data = Base64.getDecoder().decode(clearText.getBytes());
		inputStream = new ByteArrayInputStream(data);
		objectStream = new ObjectInputStream(inputStream);
		result = objectStream.readObject();
		objectStream.close();

		return result;
	}

	private String encode(final Object object) throws ClassNotFoundException, IOException {
		assert object != null;

		String result;
		ByteArrayOutputStream outputStream;
		ObjectOutputStream objectStream;
		TextEncryptor encryptor;

		outputStream = new ByteArrayOutputStream();
		objectStream = new ObjectOutputStream(outputStream);
		objectStream.writeObject(object);
		objectStream.close();

		result = new String(Base64.getEncoder().encode(outputStream.toByteArray()));

		// TODO: move the key to the configuration file!
		// TODO: make the encyptor and the base64 encoder static
		encryptor = Encryptors.text("$tr0ng-K3y!", "aabbccdd");
		result = encryptor.encrypt(result);

		return result;
	}

}
