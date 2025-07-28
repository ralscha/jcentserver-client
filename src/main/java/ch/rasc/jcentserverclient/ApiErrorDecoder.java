/*
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.jcentserverclient;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response.Body;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Error decoder for Centrifugo API responses.
 */
public class ApiErrorDecoder implements feign.codec.ErrorDecoder {

	private final ErrorDecoder.Default delegate = new ErrorDecoder.Default();

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Exception decode(String methodKey, Response response) {
		String responseBody = null;
		ApiError error = null;

		try (Body body = response.body(); InputStream inputStream = body.asInputStream()) {
			responseBody = new String(inputStream.readAllBytes());

			if (!responseBody.trim().isEmpty()) {
				JsonFactory factory = this.objectMapper.getFactory();
				try (JsonParser parser = factory.createParser(responseBody)) {
					if (parser.nextToken() == JsonToken.START_OBJECT) {
						while (parser.nextToken() != JsonToken.END_OBJECT) {
							String fieldName = parser.currentName();
							if ("error".equals(fieldName)) {
								parser.nextToken();
								error = this.objectMapper.readValue(parser, ApiError.class);
								break;
							}
							parser.skipChildren();
						}
					}
				}
			}
		}
		catch (IOException e) {
			// Fall back to default decoder
			return this.delegate.decode(methodKey, response);
		}

		if (error != null) {
			return new ApiException(error, responseBody, response.status());
		}

		return this.delegate.decode(methodKey, response);
	}

}
