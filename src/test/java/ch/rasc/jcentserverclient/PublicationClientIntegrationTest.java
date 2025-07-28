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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.BroadcastRequest;
import ch.rasc.jcentserverclient.models.BroadcastResponse;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.PublishResponse;

/**
 * Integration tests for Publication client operations. Tests publishing and broadcasting
 * messages to channels.
 */
@DisplayName("Publication Client Integration Tests")
class PublicationClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should publish message to channel successfully")
	void shouldPublishMessageToChannel() {
		// Given
		String channel = "test-channel";
		Map<String, Object> data = Map.of("message", "Hello World", "timestamp", System.currentTimeMillis(), "user",
				"test-user");

		PublishRequest request = PublishRequest.builder().channel(channel).data(data).build();

		// When
		PublishResponse response = this.client.publication().publish(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should publish message with tags and idempotency key")
	void shouldPublishMessageWithTagsAndIdempotencyKey() {
		// Given
		String channel = "test-channel-with-tags";
		Map<String, Object> data = Map.of("content", "Message with metadata");
		Map<String, String> tags = Map.of("source", "integration-test", "priority", "high");
		String idempotencyKey = "unique-key-" + System.currentTimeMillis();

		PublishRequest request = PublishRequest.builder()
			.channel(channel)
			.data(data)
			.tags(tags)
			.idempotencyKey(idempotencyKey)
			.build();

		// When
		PublishResponse response = this.client.publication().publish(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should publish message skipping history")
	void shouldPublishMessageSkippingHistory() {
		// Given
		String channel = "test-channel-no-history";
		Map<String, Object> data = Map.of("ephemeral", "This won't be saved in history");

		PublishRequest request = PublishRequest.builder().channel(channel).data(data).skipHistory(true).build();

		// When
		PublishResponse response = this.client.publication().publish(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should broadcast message to multiple channels")
	void shouldBroadcastMessageToMultipleChannels() {
		// Given
		List<String> channels = List.of("channel-1", "channel-2", "channel-3");
		Map<String, Object> data = Map.of("broadcast", "This message goes to all channels", "timestamp",
				System.currentTimeMillis());

		BroadcastRequest request = BroadcastRequest.builder().channels(channels).data(data).build();

		// When
		BroadcastResponse response = this.client.publication().broadcast(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().responses()).hasSize(channels.size());
		assertThat(response.error()).isNull();

		// Verify all channels received the broadcast
		response.result().responses().forEach(broadcastResult -> {
			assertThat(broadcastResult.result()).isNotNull();
			assertThat(broadcastResult.error()).isNull();
		});
	}

	@Test
	@DisplayName("Should broadcast message with tags to multiple channels")
	void shouldBroadcastMessageWithTagsToMultipleChannels() {
		// Given
		List<String> channels = List.of("tagged-channel-1", "tagged-channel-2");
		Map<String, Object> data = Map.of("announcement", "System maintenance scheduled");
		Map<String, String> tags = Map.of("type", "system-announcement", "severity", "medium");

		BroadcastRequest request = BroadcastRequest.builder().channels(channels).data(data).tags(tags).build();

		// When
		BroadcastResponse response = this.client.publication().broadcast(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().responses()).hasSize(channels.size());
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should return error if empty data publication")
	void shouldHandleEmptyDataPublication() {
		// Given
		String channel = "empty-data-channel";
		Map<String, Object> emptyData = Map.of();

		PublishRequest request = PublishRequest.builder().channel(channel).data(emptyData).build();

		// When
		PublishResponse response = this.client.publication().publish(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNull();
		assertThat(response.error()).isNotNull();
	}

}
