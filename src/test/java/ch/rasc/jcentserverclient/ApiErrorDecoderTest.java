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

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import feign.Request;
import feign.Response;

@DisplayName("API Error Decoder Tests")
class ApiErrorDecoderTest {

	private final ApiErrorDecoder decoder = new ApiErrorDecoder();

	@Test
	@DisplayName("Should decode default Centrifugo error wrapper")
	void shouldDecodeDefaultCentrifugoErrorWrapper() {
		Exception exception = this.decoder.decode("method",
				response(200, "{\"error\":{\"code\":107,\"message\":\"bad request\"}}"));

		assertThat(exception).isInstanceOf(ApiException.class);
		ApiException apiException = (ApiException) exception;
		assertThat(apiException.getErrorCode()).isEqualTo(107);
		assertThat(apiException.getErrorMessage()).isEqualTo("bad request");
	}

	@Test
	@DisplayName("Should decode transport error mode body")
	void shouldDecodeTransportErrorModeBody() {
		Exception exception = this.decoder.decode("method",
				response(400, "{\"code\":107,\"message\":\"bad request\"}"));

		assertThat(exception).isInstanceOf(ApiException.class);
		ApiException apiException = (ApiException) exception;
		assertThat(apiException.getErrorCode()).isEqualTo(107);
		assertThat(apiException.getErrorMessage()).isEqualTo("bad request");
		assertThat(apiException.status()).isEqualTo(400);
	}

	private static Response response(int status, String body) {
		Request request = Request.create(Request.HttpMethod.POST, "http://localhost:8000/api/publish", Map.of(), null,
				StandardCharsets.UTF_8, null);

		return Response.builder()
			.status(status)
			.reason("reason")
			.request(request)
			.body(body, StandardCharsets.UTF_8)
			.build();
	}

}
