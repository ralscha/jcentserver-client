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

import ch.rasc.jcentserverclient.models.ChannelsRequest;
import ch.rasc.jcentserverclient.models.ChannelsResponse;

/**
 * Integration tests for Channels client operations. Tests channel listing and information
 * retrieval.
 */
@DisplayName("Channels Client Integration Tests")
class ChannelsClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should list all channels")
	void shouldListAllChannels() {
		// Given - First publish to some channels to ensure they exist
		publishToChannels(List.of("channel-1", "channel-2", "channel-3"));

		ChannelsRequest request = ChannelsRequest.builder().build();

		// When
		ChannelsResponse response = this.client.channels().channels(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().channels()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should list channels with pattern filter")
	void shouldListChannelsWithPattern() {
		// Given - Publish to channels with specific pattern
		publishToChannels(List.of("test-channel-1", "test-channel-2", "other-channel"));

		ChannelsRequest request = ChannelsRequest.builder().pattern("test-*").build();

		// When
		ChannelsResponse response = this.client.channels().channels(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().channels()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should list channels with limit")
	void shouldListChannelsWithLimit() {
		// Given - Publish to multiple channels
		publishToChannels(List.of("limited-1", "limited-2", "limited-3", "limited-4", "limited-5"));

		ChannelsRequest request = ChannelsRequest.builder().build();

		// When
		ChannelsResponse response = this.client.channels().channels(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().channels()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle empty channel list")
	void shouldHandleEmptyChannelList() {
		// Given - Use a pattern that matches no channels
		ChannelsRequest request = ChannelsRequest.builder().pattern("non-existent-pattern-*").build();

		// When
		ChannelsResponse response = this.client.channels().channels(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().channels()).isNotNull();
		assertThat(response.error()).isNull();
	}

	private void publishToChannels(List<String> channels) {
		Map<String, Object> data = Map.of("message", "test data");

		for (String channel : channels) {
			this.client.publication()
				.publish(ch.rasc.jcentserverclient.models.PublishRequest.builder().channel(channel).data(data).build());
		}

		// Small delay to ensure channels are registered
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
