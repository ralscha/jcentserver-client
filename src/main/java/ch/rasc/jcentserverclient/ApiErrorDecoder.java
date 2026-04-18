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
import java.nio.charset.StandardCharsets;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import feign.Response;
import feign.Response.Body;

/**
 * Error decoder for Centrifugo API responses.
 */
public class ApiErrorDecoder implements feign.codec.ErrorDecoder {

	private final JsonMapper jsonMapper = JsonMapper.builder().build();

	@Override
	public Exception decode(String ignoredMethodKey, Response response) {
		String responseBody = null;
		ApiError error = null;

		Body body = response.body();
		if (body == null) {
			return new ApiException(null, null, response.status());
		}

		try (Body responseBodySource = body; InputStream inputStream = responseBodySource.asInputStream()) {
			responseBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

			if (!responseBody.trim().isEmpty()) {
				try {
					JsonNode root = this.jsonMapper.readTree(responseBody);
					JsonNode errorNode = root.get("error");
					if (errorNode != null && !errorNode.isNull()) {
						error = this.jsonMapper.treeToValue(errorNode, ApiError.class);
					}
				}
				catch (RuntimeException e) {
					return new ApiException(null, responseBody, response.status());
				}
			}
		}
		catch (IOException e) {
			return new ApiException(null, responseBody, response.status());
		}

		if (error != null) {
			return new ApiException(error, responseBody, response.status());
		}

		return new ApiException(null, responseBody, response.status());
	}

}
