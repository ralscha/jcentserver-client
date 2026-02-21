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

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;

/**
 * Error decoder for Centrifugo API responses.
 */
public class ApiErrorDecoder implements feign.codec.ErrorDecoder {

	private final ErrorDecoder.Default delegate = new ErrorDecoder.Default();

	private final JsonMapper jsonMapper = JsonMapper.builder().build();

	@Override
	public Exception decode(String methodKey, Response response) {
		String responseBody = null;
		ApiError error = null;

		try (Body body = response.body(); InputStream inputStream = body.asInputStream()) {
			responseBody = new String(inputStream.readAllBytes());

			if (!responseBody.trim().isEmpty()) {
				JsonNode root = this.jsonMapper.readTree(responseBody);
				JsonNode errorNode = root.get("error");
				if (errorNode != null && !errorNode.isNull()) {
					error = this.jsonMapper.treeToValue(errorNode, ApiError.class);
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
