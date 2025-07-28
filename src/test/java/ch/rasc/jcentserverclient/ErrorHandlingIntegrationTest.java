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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.PublishResponse;

/**
 * Integration tests for error handling and edge cases. Tests how the client handles
 * various error conditions.
 */
@DisplayName("Error Handling Integration Tests")
class ErrorHandlingIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should handle invalid API key gracefully")
	void shouldHandleInvalidApiKeyGracefully() {
		// Given - Create client with invalid API key
		CentrifugoServerApiClient invalidClient = CentrifugoServerApiClient
			.create(config -> config.apiKey("invalid-api-key").baseUrl(this.baseUrl));

		PublishRequest request = PublishRequest.builder()
			.channel("test-channel")
			.data(Map.of("message", "test"))
			.build();

		// When & Then - Should throw an exception due to authentication failure
		assertThatThrownBy(() -> invalidClient.publication().publish(request)).isInstanceOf(Exception.class);
	}

	@Test
	@DisplayName("Should handle empty channel name with exception")
	void shouldHandleEmptyChannelName() {
		assertThatThrownBy(() -> PublishRequest.builder().channel("").data(Map.of("message", "test")).build())
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("Should handle null data with exception")
	void shouldHandleNullDataWithException() {
		assertThatThrownBy(() -> PublishRequest.builder().channel("test-channel").data(null).build())
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("Should handle very long channel names")
	void shouldHandleVeryLongChannelNames() {
		// Given - Create a very long channel name
		String longChannelName = "a".repeat(1000);

		// When & Then - Should handle long channel names without error
		PublishRequest request = PublishRequest.builder()
			.channel(longChannelName)
			.data(Map.of("message", "test"))
			.build();

		PublishResponse response = this.client.publication().publish(request);
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle large message payload")
	void shouldHandleLargeMessagePayload() {
		// Given - Create a large message payload
		String largeMessage = "x".repeat(10000);
		Map<String, Object> largeData = Map.of("message", largeMessage, "type", "large-message-test");

		PublishRequest request = PublishRequest.builder().channel("large-message-channel").data(largeData).build();

		// When
		var response = this.client.publication().publish(request);

		// Then - Should either succeed or fail gracefully
		assertThat(response).isNotNull();
		// The response might succeed or contain an error depending on Centrifugo's limits
	}

	@Test
	@DisplayName("Should handle special characters in channel names")
	void shouldHandleSpecialCharactersInChannelNames() {
		// Given - Channel names with special characters
		String[] specialChannels = { "test-channel_with_underscores", "test.channel.with.dots",
				"test:channel:with:colons" };

		for (String channel : specialChannels) {
			PublishRequest request = PublishRequest.builder()
				.channel(channel)
				.data(Map.of("message", "Special character test"))
				.build();

			// When
			var response = this.client.publication().publish(request);

			// Then - Should handle each channel name appropriately
			assertThat(response).isNotNull();
		}
	}

	@Test
	@DisplayName("Should handle malformed requests gracefully")
	void shouldHandleMalformedRequestsGracefully() {
		// Given - Request with minimal required fields
		PublishRequest validRequest = PublishRequest.builder().channel("minimal-test-channel").data(Map.of()).build();

		// When
		var response = this.client.publication().publish(validRequest);

		// Then - Should handle minimal valid request
		assertThat(response).isNotNull();
	}

	@Test
	@DisplayName("Should maintain client state after errors")
	void shouldMaintainClientStateAfterErrors() {
		// Given - First make a request that might cause an error
		try {
			PublishRequest badRequest = PublishRequest.builder().channel("").data(Map.of("test", "data")).build();

			this.client.publication().publish(badRequest);
		}
		catch (Exception e) {
			// Expected to fail
		}

		// When - Make a valid request after the error
		PublishRequest goodRequest = PublishRequest.builder()
			.channel("recovery-test-channel")
			.data(Map.of("message", "Recovery test"))
			.build();

		var response = this.client.publication().publish(goodRequest);

		// Then - Client should still work after previous error
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle unicode characters in messages")
	void shouldHandleUnicodeCharactersInMessages() {
		// Given - Message with unicode characters
		Map<String, Object> unicodeData = Map.of("message", "Hello 世界! 🌍 Здравствуй мир! مرحبا بالعالم!", "emoji",
				"🎉🚀✨", "symbols", "α β γ δ ε ζ η θ");

		PublishRequest request = PublishRequest.builder().channel("unicode-test-channel").data(unicodeData).build();

		// When
		var response = this.client.publication().publish(request);

		// Then - Should handle unicode properly
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

}
