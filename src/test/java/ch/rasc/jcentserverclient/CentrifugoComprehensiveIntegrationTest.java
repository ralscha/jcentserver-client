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
import ch.rasc.jcentserverclient.models.HistoryRequest;
import ch.rasc.jcentserverclient.models.HistoryResponse;
import ch.rasc.jcentserverclient.models.InfoRequest;
import ch.rasc.jcentserverclient.models.InfoResponse;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.PublishResponse;

/**
 * Comprehensive integration tests that test all major client operations together. This
 * tests real-world scenarios using multiple clients in sequence.
 */
@DisplayName("Comprehensive Integration Tests")
class CentrifugoComprehensiveIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should handle multiple channel broadcast and verification")
	void shouldHandleMultipleChannelBroadcastAndVerification() {
		// Given
		List<String> channels = List.of("broadcast-channel-1", "broadcast-channel-2", "broadcast-channel-3");
		Map<String, Object> broadcastData = Map.of("announcement", "System maintenance notification", "severity",
				"medium", "timestamp", System.currentTimeMillis());

		// When - Broadcast to multiple channels
		BroadcastRequest broadcastRequest = BroadcastRequest.builder().channels(channels).data(broadcastData).build();

		BroadcastResponse broadcastResponse = this.client.publication().broadcast(broadcastRequest);

		// Then - Verify broadcast succeeded
		assertThat(broadcastResponse).isNotNull();
		assertThat(broadcastResponse.result()).isNotNull();
		assertThat(broadcastResponse.result().responses()).hasSize(channels.size());
		assertThat(broadcastResponse.error()).isNull();

		// Verify each channel received the broadcast
		broadcastResponse.result().responses().forEach(result -> {
			assertThat(result.result()).isNotNull();
			assertThat(result.error()).isNull();
		});

		// When - Verify history for each channel
		for (String channel : channels) {
			HistoryRequest historyRequest = HistoryRequest.builder().channel(channel).limit(1).build();

			HistoryResponse historyResponse = this.client.history().history(historyRequest);

			// Then - Each channel should have the broadcast message in history
			assertThat(historyResponse).isNotNull();
			assertThat(historyResponse.result()).isNotNull();
			assertThat(historyResponse.result().publications()).isNotEmpty();
			assertThat(historyResponse.error()).isNull();
		}
	}

	@Test
	@DisplayName("Should handle high-frequency publishing and retrieval")
	void shouldHandleHighFrequencyPublishing() {
		// Given
		String channel = "high-frequency-channel";
		int messageCount = 10;

		// When - Publish multiple messages quickly
		for (int i = 1; i <= messageCount; i++) {
			Map<String, Object> data = Map.of("messageId", i, "content", "High frequency message " + i, "timestamp",
					System.currentTimeMillis());

			PublishRequest request = PublishRequest.builder().channel(channel).data(data).build();

			PublishResponse response = this.client.publication().publish(request);

			// Then - Each publish should succeed
			assertThat(response).isNotNull();
			assertThat(response.result()).isNotNull();
			assertThat(response.error()).isNull();

			// Small delay to avoid overwhelming the server
			try {
				Thread.sleep(10);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// When - Retrieve history
		HistoryRequest historyRequest = HistoryRequest.builder().channel(channel).limit(messageCount).build();

		HistoryResponse historyResponse = this.client.history().history(historyRequest);

		// Then - Should have received messages
		assertThat(historyResponse).isNotNull();
		assertThat(historyResponse.result()).isNotNull();
		assertThat(historyResponse.result().publications()).isNotEmpty();
		assertThat(historyResponse.error()).isNull();

		// Should have at least some of our messages
		assertThat(historyResponse.result().publications().size()).isLessThanOrEqualTo(messageCount);
	}

	@Test
	@DisplayName("Should verify client configuration and connectivity")
	void shouldVerifyClientConfigurationAndConnectivity() {
		// When - Test basic connectivity with stats
		InfoResponse statsResponse = this.client.stats().info(InfoRequest.builder().build());

		// Then - Should connect successfully
		assertThat(statsResponse).isNotNull();
		assertThat(statsResponse.result()).isNotNull();
		assertThat(statsResponse.error()).isNull();

		// Verify we can access all major client interfaces
		assertThat(this.client.publication()).isNotNull();
		assertThat(this.client.channels()).isNotNull();
		assertThat(this.client.history()).isNotNull();
		assertThat(this.client.presence()).isNotNull();
		assertThat(this.client.stats()).isNotNull();
		assertThat(this.client.connection()).isNotNull();
		assertThat(this.client.batch()).isNotNull();

		// Verify base URL is correctly set
		assertThat(this.baseUrl).contains("http://");
		assertThat(this.baseUrl).contains("/api");
	}

	@Test
	@DisplayName("Should handle concurrent operations safely")
	void shouldHandleConcurrentOperationsSafely() throws InterruptedException {
		// Given
		String baseChannel = "concurrent-test-channel-";
		int threadCount = 5;
		Thread[] threads = new Thread[threadCount];

		// When - Run concurrent operations
		for (int i = 0; i < threadCount; i++) {
			final int threadId = i;
			threads[i] = new Thread(() -> {
				String channel = baseChannel + threadId;

				// Publish a message
				Map<String, Object> data = Map.of("threadId", threadId, "message",
						"Concurrent message from thread " + threadId);

				PublishRequest request = PublishRequest.builder().channel(channel).data(data).build();

				PublishResponse response = this.client.publication().publish(request);

				// Verify success
				assertThat(response).isNotNull();
				assertThat(response.result()).isNotNull();
				assertThat(response.error()).isNull();
			});
		}

		// Start all threads
		for (Thread thread : threads) {
			thread.start();
		}

		// Wait for all threads to complete
		for (Thread thread : threads) {
			thread.join();
		}

		// Then - Verify all operations completed successfully
		// Check that we can still perform operations after concurrent access
		InfoResponse statsResponse = this.client.stats().info(InfoRequest.builder().build());
		assertThat(statsResponse).isNotNull();
		assertThat(statsResponse.result()).isNotNull();
		assertThat(statsResponse.error()).isNull();
	}

}
