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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.clients.BatchClient;
import ch.rasc.jcentserverclient.clients.ChannelsClient;
import ch.rasc.jcentserverclient.clients.ConnectionClient;
import ch.rasc.jcentserverclient.clients.DeviceClient;
import ch.rasc.jcentserverclient.clients.HistoryClient;
import ch.rasc.jcentserverclient.clients.PresenceClient;
import ch.rasc.jcentserverclient.clients.PublicationClient;
import ch.rasc.jcentserverclient.clients.PushClient;
import ch.rasc.jcentserverclient.clients.RpcClient;
import ch.rasc.jcentserverclient.clients.StatsClient;
import ch.rasc.jcentserverclient.clients.TokenClient;
import ch.rasc.jcentserverclient.clients.UserBlockClient;
import ch.rasc.jcentserverclient.clients.UserStatusClient;
import feign.RequestLine;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@DisplayName("Swagger Alignment Tests")
class SwaggerAlignmentTest {

	private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

	@Test
	@DisplayName("Should expose all Swagger HTTP API endpoints")
	void shouldExposeAllSwaggerHttpApiEndpoints() throws Exception {
		JsonNode swagger = JSON_MAPPER.readTree(Files.readString(Path.of("swagger.json")));
		Set<String> swaggerPaths = new TreeSet<>();
		swagger.get("paths").propertyStream().forEach(path -> swaggerPaths.add(path.getKey().substring(1)));

		Set<String> clientPaths = clientPaths();

		assertThat(clientPaths).containsAll(swaggerPaths);
		assertThat(clientPaths.stream().filter(path -> !swaggerPaths.contains(path)).collect(Collectors.toSet()))
			.containsOnly("rpc");
	}

	private static Set<String> clientPaths() {
		return Arrays
			.stream(new Class<?>[] { BatchClient.class, ChannelsClient.class, ConnectionClient.class,
					DeviceClient.class, HistoryClient.class, PresenceClient.class, PublicationClient.class,
					PushClient.class, RpcClient.class, StatsClient.class, TokenClient.class, UserBlockClient.class,
					UserStatusClient.class })
			.flatMap(client -> Arrays.stream(client.getMethods()))
			.map(method -> method.getAnnotation(RequestLine.class))
			.filter(annotation -> annotation != null && annotation.value().startsWith("POST /"))
			.map(annotation -> annotation.value().substring("POST /".length()))
			.collect(Collectors.toCollection(TreeSet::new));
	}

}
