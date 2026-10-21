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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ch.rasc.jcentserverclient.models.BatchResponse;
import ch.rasc.jcentserverclient.models.BroadcastResponse;
import ch.rasc.jcentserverclient.models.Error;
import ch.rasc.jcentserverclient.models.PublishResponse;
import tools.jackson.databind.json.JsonMapper;

class ErrorResponseTest {

	private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

	@ParameterizedTest
	@CsvSource({ "100, internal, true", "111, too many requests, true", "107, bad request, false" })
	void shouldDeserializeRetryAdvice(long code, String message, boolean temporary) {
		String body = """
				{"error":{"code":%d,"message":"%s","temporary":%s}}
				""".formatted(code, message, temporary);

		PublishResponse response = JSON_MAPPER.readValue(body, PublishResponse.class);

		assertThat(response.hasError()).isTrue();
		assertThat(response.error().code()).isEqualTo(code);
		assertThat(response.error().message()).isEqualTo(message);
		assertThat(response.error().temporary()).isEqualTo(temporary);
	}

	@Test
	void shouldDeserializeErrorsWithoutRetryAdvice() {
		PublishResponse response = JSON_MAPPER.readValue(
				"{\"error\":{\"code\":102,\"message\":\"unknown channel\"}}", PublishResponse.class);

		assertThat(response.error().code()).isEqualTo(102L);
		assertThat(response.error().temporary()).isNull();
	}

	@Test
	void shouldPreserveRetryAdviceForEachBatchReply() {
		String body = """
				{"replies":[{"publish":{}},
				{"error":{"code":111,"message":"too many requests","temporary":true}},
				{"error":{"code":107,"message":"bad request","temporary":false}}]}
				""";

		BatchResponse response = JSON_MAPPER.readValue(body, BatchResponse.class);

		assertThat(response.hasError()).isTrue();
		assertThat(response.replies().get(0).error()).isNull();
		assertThat(response.replies().get(1).error().temporary()).isTrue();
		assertThat(response.replies().get(2).error().temporary()).isFalse();
	}

	@Test
	void shouldPreserveRetryAdviceForEachBroadcastResponse() {
		String body = """
				{"result":{"responses":[{"result":{}},
				{"error":{"code":100,"message":"internal","temporary":true}},
				{"error":{"code":102,"message":"unknown channel"}}]}}
				""";

		BroadcastResponse response = JSON_MAPPER.readValue(body, BroadcastResponse.class);

		assertThat(response.hasError()).isTrue();
		assertThat(response.result().responses().get(0).hasError()).isFalse();
		assertThat(response.result().responses().get(1).error().temporary()).isTrue();
		assertThat(response.result().responses().get(2).error().temporary()).isNull();
	}

	@Test
	void shouldKeepTwoArgumentErrorConstructorsCompatible() {
		assertThat(new Error(107L, "bad request").temporary()).isNull();
		assertThat(new ApiError(107, "bad request").getTemporary()).isNull();
	}

}
