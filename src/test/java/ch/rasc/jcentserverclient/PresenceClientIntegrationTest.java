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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.PresenceRequest;
import ch.rasc.jcentserverclient.models.PresenceResponse;
import ch.rasc.jcentserverclient.models.PresenceStatsRequest;
import ch.rasc.jcentserverclient.models.PresenceStatsResponse;

/**
 * Integration tests for Presence client operations. Tests channel presence information
 * retrieval.
 */
@DisplayName("Presence Client Integration Tests")
class PresenceClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should get presence information for channel")
	void shouldGetPresenceInformation() {
		// Given
		String channel = "presence-test-channel";

		PresenceRequest request = PresenceRequest.of(channel);

		// When
		PresenceResponse response = this.client.presence().presence(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().presence()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should get presence statistics for channel")
	void shouldGetPresenceStatistics() {
		// Given
		String channel = "presence-stats-test-channel";

		PresenceStatsRequest request = PresenceStatsRequest.of(channel);

		// When
		PresenceStatsResponse response = this.client.presence().presenceStats(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().numClients()).isNotNull();
		assertThat(response.result().numUsers()).isNotNull();
		assertThat(response.error()).isNull();

		// For a channel with no connected clients, these should be 0
		assertThat(response.result().numClients()).isEqualTo(0);
		assertThat(response.result().numUsers()).isEqualTo(0);
	}

	@Test
	@DisplayName("Should handle presence for non-existent channel")
	void shouldHandlePresenceForNonExistentChannel() {
		// Given
		String channel = "non-existent-channel-" + System.currentTimeMillis();

		PresenceRequest request = PresenceRequest.of(channel);

		// When
		PresenceResponse response = this.client.presence().presence(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().presence()).isNotNull();
		assertThat(response.result().presence()).isEmpty();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle presence stats for non-existent channel")
	void shouldHandlePresenceStatsForNonExistentChannel() {
		// Given
		String channel = "non-existent-stats-channel-" + System.currentTimeMillis();

		PresenceStatsRequest request = PresenceStatsRequest.of(channel);

		// When
		PresenceStatsResponse response = this.client.presence().presenceStats(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().numClients()).isEqualTo(0);
		assertThat(response.result().numUsers()).isEqualTo(0);
		assertThat(response.error()).isNull();
	}

}
