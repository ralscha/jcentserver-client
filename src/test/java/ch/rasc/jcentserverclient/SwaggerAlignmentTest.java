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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import ch.rasc.jcentserverclient.clients.MapClient;
import ch.rasc.jcentserverclient.clients.PresenceClient;
import ch.rasc.jcentserverclient.clients.PublicationClient;
import ch.rasc.jcentserverclient.clients.PushClient;
import ch.rasc.jcentserverclient.clients.RpcClient;
import ch.rasc.jcentserverclient.clients.StatsClient;
import ch.rasc.jcentserverclient.clients.TokenClient;
import ch.rasc.jcentserverclient.clients.UserBlockClient;
import ch.rasc.jcentserverclient.clients.UserStatusClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import feign.RequestLine;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@DisplayName("Swagger Alignment Tests")
class SwaggerAlignmentTest {

	private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

	private static final List<Class<?>> CLIENT_TYPES = List.of(BatchClient.class, ChannelsClient.class,
			ConnectionClient.class, DeviceClient.class, HistoryClient.class, PresenceClient.class,
			PublicationClient.class, PushClient.class, RpcClient.class, StatsClient.class, TokenClient.class,
			UserBlockClient.class, UserStatusClient.class, MapClient.class);

	@Test
	@DisplayName("Should expose all Swagger HTTP API endpoints")
	void shouldExposeAllSwaggerHttpApiEndpoints() throws Exception {
		JsonNode swagger = readSwagger();
		Set<String> swaggerPaths = new TreeSet<>();
		swagger.get("paths").propertyStream().forEach(path -> swaggerPaths.add(path.getKey().substring(1)));

		Set<String> clientPaths = clientPaths();

		assertThat(clientPaths).containsExactlyElementsOf(swaggerPaths);
	}

	@Test
	@DisplayName("Should bind every endpoint to its Swagger request and response models")
	void shouldBindEndpointsToSwaggerModels() throws Exception {
		JsonNode swagger = readSwagger();

		for (Class<?> clientType : CLIENT_TYPES) {
			for (Method method : clientType.getMethods()) {
				RequestLine requestLine = method.getAnnotation(RequestLine.class);
				if (requestLine == null || !requestLine.value().startsWith("POST /")) {
					continue;
				}

				String path = requestLine.value().substring("POST ".length());
				JsonNode operation = swagger.path("paths").path(path).path("post");
				assertThat(operation.isMissingNode()).as("Swagger operation for %s", requestLine.value()).isFalse();

				String requestDefinition = definitionName(
						operation.path("parameters").get(0).path("schema").path("$ref").asString());
				String responseDefinition = definitionName(
						operation.path("responses").path("200").path("schema").path("$ref").asString());

				assertThat(method.getParameterCount()).as("request parameter count for %s", requestLine.value())
					.isEqualTo(1);
				assertThat(method.getParameterTypes()[0].getSimpleName())
					.as("request model for %s", requestLine.value())
					.isEqualTo(javaClassName(requestDefinition));
				assertThat(method.getReturnType().getSimpleName()).as("response model for %s", requestLine.value())
					.isEqualTo(javaClassName(responseDefinition));
			}
		}
	}

	@Test
	@DisplayName("Should keep every JSON model property and type aligned with Swagger")
	void shouldKeepModelPropertiesAndTypesAligned() throws Exception {
		JsonNode definitions = readSwagger().path("definitions");

		for (Map.Entry<String, JsonNode> definition : definitions.properties()) {
			Class<?> modelType = Class
				.forName("ch.rasc.jcentserverclient.models." + javaClassName(definition.getKey()));
			Map<String, Type> actualProperties = jsonProperties(modelType);
			JsonNode expectedProperties = definition.getValue().path("properties");

			assertThat(actualProperties.keySet()).as("JSON properties of %s", definition.getKey())
				.containsExactlyInAnyOrderElementsOf(expectedProperties.propertyNames());

			for (Map.Entry<String, JsonNode> property : expectedProperties.properties()) {
				assertSchemaType(property.getValue(), actualProperties.get(property.getKey()),
						definition.getKey() + "." + property.getKey());
			}
		}
	}

	private static Set<String> clientPaths() {
		return CLIENT_TYPES.stream()
			.flatMap(client -> Arrays.stream(client.getMethods()))
			.map(method -> method.getAnnotation(RequestLine.class))
			.filter(annotation -> annotation != null && annotation.value().startsWith("POST /"))
			.map(annotation -> annotation.value().substring("POST /".length()))
			.collect(Collectors.toCollection(TreeSet::new));
	}

	private static JsonNode readSwagger() throws Exception {
		return JSON_MAPPER.readTree(Files.readString(Path.of("swagger.json")));
	}

	private static Map<String, Type> jsonProperties(Class<?> modelType) {
		Map<String, Type> properties = new LinkedHashMap<>();
		for (Field field : modelType.getDeclaredFields()) {
			JsonProperty annotation = field.getAnnotation(JsonProperty.class);
			if (annotation != null && !annotation.value().isEmpty()) {
				properties.put(annotation.value(), field.getGenericType());
			}
		}
		for (Method method : modelType.getDeclaredMethods()) {
			JsonProperty annotation = method.getAnnotation(JsonProperty.class);
			if (annotation != null && !annotation.value().isEmpty() && method.getParameterCount() == 0) {
				properties.put(annotation.value(), method.getGenericReturnType());
			}
		}
		return properties;
	}

	private static void assertSchemaType(JsonNode schema, Type actualType, String propertyPath) {
		assertThat(actualType).as("Java type for %s", propertyPath).isNotNull();

		JsonNode reference = schema.get("$ref");
		if (reference != null) {
			assertThat(rawType(actualType).getSimpleName()).as("Java type for %s", propertyPath)
				.isEqualTo(javaClassName(definitionName(reference.asString())));
			return;
		}

		String schemaType = schema.path("type").asString();
		switch (schemaType) {
			case "array" -> {
				ParameterizedType parameterizedType = parameterizedType(actualType, List.class, propertyPath);
				assertSchemaType(schema.path("items"), parameterizedType.getActualTypeArguments()[0],
						propertyPath + "[]");
			}
			case "object" -> {
				JsonNode valueSchema = schema.get("additionalProperties");
				if (valueSchema == null) {
					assertThat(actualType).as("Java type for %s", propertyPath).isEqualTo(Object.class);
				}
				else {
					ParameterizedType parameterizedType = parameterizedType(actualType, Map.class, propertyPath);
					assertThat(parameterizedType.getActualTypeArguments()[0]).as("map key type for %s", propertyPath)
						.isEqualTo(String.class);
					assertSchemaType(valueSchema, parameterizedType.getActualTypeArguments()[1], propertyPath + "{}");
				}
			}
			case "string" -> assertThat(actualType).as("Java type for %s", propertyPath).isEqualTo(String.class);
			case "boolean" -> assertThat(actualType).as("Java type for %s", propertyPath).isEqualTo(Boolean.class);
			case "integer" -> {
				Class<?> expectedType = "int32".equals(schema.path("format").asString()) ? Integer.class : Long.class;
				assertThat(actualType).as("Java type for %s", propertyPath).isEqualTo(expectedType);
			}
			case "number" -> assertThat(actualType).as("Java type for %s", propertyPath).isEqualTo(Double.class);
			default -> throw new AssertionError("Unsupported Swagger type '" + schemaType + "' for " + propertyPath);
		}
	}

	private static ParameterizedType parameterizedType(Type actualType, Class<?> expectedRawType, String propertyPath) {
		assertThat(actualType).as("parameterized Java type for %s", propertyPath).isInstanceOf(ParameterizedType.class);
		ParameterizedType parameterizedType = (ParameterizedType) actualType;
		assertThat(parameterizedType.getRawType()).as("raw Java type for %s", propertyPath).isEqualTo(expectedRawType);
		return parameterizedType;
	}

	private static Class<?> rawType(Type type) {
		return type instanceof ParameterizedType parameterizedType ? (Class<?>) parameterizedType.getRawType()
				: (Class<?>) type;
	}

	private static String definitionName(String reference) {
		return reference.substring(reference.lastIndexOf('/') + 1);
	}

	private static String javaClassName(String definitionName) {
		return switch (definitionName) {
			case "RPCRequest" -> "RpcRequest";
			case "RPCResponse" -> "RpcResponse";
			case "RPCResult" -> "RpcResult";
			case "api.Disconnect" -> "Disconnect";
			default -> definitionName;
		};
	}

}
