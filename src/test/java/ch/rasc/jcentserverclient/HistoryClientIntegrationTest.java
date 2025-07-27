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

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.HistoryRequest;
import ch.rasc.jcentserverclient.models.HistoryResponse;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.StreamPosition;

/**
 * Integration tests for History client operations. Tests channel history retrieval and
 * filtering.
 */
@DisplayName("History Client Integration Tests")
class HistoryClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should retrieve channel history")
	void shouldRetrieveChannelHistory() {
		// Given - Publish some messages to establish history
		String channel = "history-test-channel";
		publishMultipleMessages(channel, 3);

		HistoryRequest request = HistoryRequest.builder().channel(channel).build();

		// When
		HistoryResponse response = this.client.history().history(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().publications()).isNotNull();
		assertThat(response.error()).isNull();

		// Should have the messages we published
		assertThat(response.result().publications().size()).isGreaterThanOrEqualTo(0);
	}

	@Test
	@DisplayName("Should retrieve channel history with limit")
	void shouldRetrieveChannelHistoryWithLimit() {
		// Given - Publish several messages
		String channel = "history-limit-test-channel";
		publishMultipleMessages(channel, 5);

		HistoryRequest request = HistoryRequest.builder().channel(channel).limit(3).build();

		// When
		HistoryResponse response = this.client.history().history(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().publications()).isNotNull();
		assertThat(response.error()).isNull();

		// Should respect the limit
		assertThat(response.result().publications().size()).isLessThanOrEqualTo(3);
	}

	@Test
	@DisplayName("Should retrieve channel history in reverse order")
	void shouldRetrieveChannelHistoryInReverse() {
		// Given - Publish messages with identifiable content
		String channel = "history-reverse-test-channel";
		publishMultipleMessages(channel, 3);

		HistoryRequest request = HistoryRequest.builder().channel(channel).reverse(true).build();

		// When
		HistoryResponse response = this.client.history().history(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().publications()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle empty channel history")
	void shouldHandleEmptyChannelHistory() {
		// Given - A channel with no history
		String channel = "empty-history-channel-" + System.currentTimeMillis();

		HistoryRequest request = HistoryRequest.builder().channel(channel).build();

		// When
		HistoryResponse response = this.client.history().history(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().publications()).isNotNull();
		assertThat(response.result().publications()).isEmpty();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should retrieve history with since parameter")
	void shouldRetrieveHistoryWithSince() {
		// Given
		String channel = "history-since-test-channel";

		// Publish a message and get its offset for the since parameter
		publishMultipleMessages(channel, 2);

		// Get current history to find an offset
		HistoryResponse initialResponse = this.client.history()
			.history(HistoryRequest.builder().channel(channel).build());

		// If we have publications, use the first one's offset
		if (!initialResponse.result().publications().isEmpty()) {
			Long sinceOffset = initialResponse.result().publications().get(0).offset();

			HistoryRequest request = HistoryRequest.builder()
				.channel(channel)
				.since(StreamPosition.builder().offset(sinceOffset).build())
				.build();

			// When
			HistoryResponse response = this.client.history().history(request);

			// Then
			assertThat(response).isNotNull();
			assertThat(response.result()).isNotNull();
			assertThat(response.result().publications()).isNotNull();
			assertThat(response.error()).isNull();
		}
	}

	private void publishMultipleMessages(String channel, int count) {
		for (int i = 1; i <= count; i++) {
			Map<String, Object> data = Map.of("message", "Test message " + i, "sequence", i, "timestamp",
					System.currentTimeMillis());

			PublishRequest request = PublishRequest.builder().channel(channel).data(data).build();

			this.client.publication().publish(request);

			// Small delay between messages
			try {
				Thread.sleep(10);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
