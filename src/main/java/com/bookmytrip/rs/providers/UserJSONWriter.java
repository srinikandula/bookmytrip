/*
 * Copyright (C) 2010 Tolven Inc

 * This library is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU Lesser General Public License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;  
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * Contact: info@tolvenhealth.com 
 *
 * @author <your name>
 * @version $Id: MenuStructureMessageWriter.java,v 1.1 2010/05/10 19:09:52 jchurin Exp $
 */

package com.bookmytrip.rs.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.bookmytrip.domain.User;
import com.google.gson.Gson;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserJSONWriter implements MessageBodyWriter<User> {

	// private TrimExpressionEvaluator ee = new TrimExpressionEvaluator();

	@Override
	public long getSize(User ams, Class<?> genericType, Type type,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> genericType, Type type,
			Annotation[] annotations, MediaType mediaType) {
		return (genericType == User.class);
	}

	@Override
	public void writeTo(User ams, Class<?> genericType, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream out)
			throws IOException, WebApplicationException {
		Gson gson = new Gson();
		String gsonString = gson.toJson(ams, User.class);
		OutputStreamWriter ow = new OutputStreamWriter(out);
		ow.write(gsonString);
	}

}
