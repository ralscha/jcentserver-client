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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@DisplayName("Live Swagger Alignment Integration Tests")
class LiveSwaggerAlignmentIntegrationTest extends CentrifugoIntegrationTestBase {

	private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

	@Test
	@DisplayName("Should match Swagger served by the live Centrifugo container")
	void shouldMatchLiveSwagger() throws Exception {
		String swaggerUrl = this.baseUrl.substring(0, this.baseUrl.length() - "/api".length())
				+ "/swagger/swagger.json";
		HttpRequest request = HttpRequest.newBuilder(URI.create(swaggerUrl)).GET().build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		assertThat(response.statusCode()).isEqualTo(200);
		JsonNode liveSwagger = JSON_MAPPER.readTree(response.body());
		JsonNode checkedInSwagger = JSON_MAPPER.readTree(Files.readString(Path.of("swagger.json")));
		assertThat(liveSwagger).isEqualTo(checkedInSwagger);
	}

}
