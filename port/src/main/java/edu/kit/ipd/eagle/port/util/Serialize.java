package edu.kit.ipd.eagle.port.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * This class provides basic functionality to serialise objects to jsons.
 *
 * @author Dominik Fuchss
 *
 */
public final class Serialize {
	private Serialize() {
		throw new IllegalAccessError();
	}

	/**
	 * Get a new default object mapper for the project.
	 *
	 * @return a new default object mapper
	 */
	public static ObjectMapper getObjectMapper() {
		return getObjectMapper(false);
	}

	/**
	 * Get a new default object mapper for the project.
	 *
	 * @param indent indicator for indention
	 * @return a new default object mapper
	 */
	public static ObjectMapper getObjectMapper(boolean indent) {
		var objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, indent);
		objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker() //
				.withFieldVisibility(Visibility.ANY)//
				.withGetterVisibility(Visibility.NONE)//
				.withSetterVisibility(Visibility.NONE)//
				.withIsGetterVisibility(Visibility.NONE));
		return objectMapper;
	}

	/**
	 * Get a new object mapper which uses the getters of a class for
	 * (de)serialisation.
	 *
	 * @param indent indicator for indention
	 * @return a new object mapper
	 */
	public static ObjectMapper getObjectMapperForGetters(boolean indent) {
		var objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, indent);
		objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker() //
				.withFieldVisibility(Visibility.NONE)//
				.withGetterVisibility(Visibility.PUBLIC_ONLY)//
				.withSetterVisibility(Visibility.NONE)//
				.withIsGetterVisibility(Visibility.PUBLIC_ONLY));
		return objectMapper;
	}

	/**
	 * Get a new object mapper which stores class information .
	 *
	 * @param indent indicator for indention
	 * @return a new object mapper
	 */
	@SuppressWarnings("deprecation")
	public static ObjectMapper getObjectMapperForInheritance(boolean indent) {
		return getObjectMapper(indent).enableDefaultTyping();
	}

	/**
	 * Serialize binary data.
	 *
	 * @param s the data
	 * @return the serialized data as byte[] or {@code null} iff not successful
	 */
	public static byte[] serializeBinary(Serializable s) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(s);
			os.flush();
			byte[] data = bos.toByteArray();
			os.close();
			bos.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Load data from binary data.
	 *
	 * @param <T>  the acutal result type of the data
	 * @param data the binary data
	 * @return the deserialized data or {@code null} iff not successful
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserializeBinary(byte[] data) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object o = ois.readObject();

			ois.close();
			bis.close();
			return (T) o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
