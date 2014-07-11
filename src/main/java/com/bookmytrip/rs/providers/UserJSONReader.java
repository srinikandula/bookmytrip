package com.bookmytrip.rs.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.bookmytrip.domain.User;
import com.bookmytrip.formatter.JSONFormatter;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class UserJSONReader implements MessageBodyReader<User> {

	@Override
	public boolean isReadable(Class<?> arg0, Type type, Annotation[] arg2,MediaType arg3) {
		return type.equals(User.class);
	}

	@Override
	public User readFrom(Class<User> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> headers, InputStream in)
			throws IOException, WebApplicationException {
		String formData = readAsString(in);
		return (User)JSONFormatter.toObject(formData, User.class);
	}

	private final String readAsString(InputStream in) throws IOException {
		Reader reader = new InputStreamReader(in);
		StringBuilder sb = new StringBuilder();

		char[] c = new char[1024];
		int l;
		while ((l = reader.read(c)) != -1) {
			sb.append(c, 0, l);
		}
		return sb.toString();
	}

}
