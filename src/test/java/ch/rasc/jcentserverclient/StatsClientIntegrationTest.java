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

import ch.rasc.jcentserverclient.models.InfoRequest;
import ch.rasc.jcentserverclient.models.InfoResponse;

/**
 * Integration tests for Stats client operations. Tests server statistics retrieval.
 */
@DisplayName("Stats Client Integration Tests")
class StatsClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should retrieve server statistics")
	void shouldRetrieveServerStatistics() {
		// Given
		InfoRequest request = InfoRequest.builder().build();

		// When
		InfoResponse response = this.client.stats().info(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();

		// Verify basic stats structure
		assertThat(response.result().nodes()).isNotNull();
		assertThat(response.result().nodes()).isNotEmpty();

		// Check that we have at least one node with basic stats
		response.result().nodes().forEach(node -> {
			assertThat(node.name()).isNotNull();
			assertThat(node.numClients()).isNotNull();
			assertThat(node.numUsers()).isNotNull();
			assertThat(node.numChannels()).isNotNull();
		});
	}

	@Test
	@DisplayName("Should retrieve detailed server statistics")
	void shouldRetrieveDetailedServerStatistics() {
		// Given - Request stats multiple times to verify consistency
		InfoRequest request = InfoRequest.builder().build();

		// When - Make multiple calls
		InfoResponse response1 = this.client.stats().info(request);
		InfoResponse response2 = this.client.stats().info(request);

		// Then - Both should succeed
		assertThat(response1).isNotNull();
		assertThat(response1.result()).isNotNull();
		assertThat(response1.error()).isNull();

		assertThat(response2).isNotNull();
		assertThat(response2.result()).isNotNull();
		assertThat(response2.error()).isNull();

		// Both should have the same node structure
		assertThat(response1.result().nodes().size()).isEqualTo(response2.result().nodes().size());
	}

	@Test
	@DisplayName("Should verify stats contain expected fields")
	void shouldVerifyStatsContainExpectedFields() {
		// Given
		InfoRequest request = InfoRequest.builder().build();

		// When
		InfoResponse response = this.client.stats().info(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();

		// Verify we have at least one node
		assertThat(response.result().nodes()).isNotEmpty();

		// Verify each node has required fields
		response.result().nodes().forEach(node -> {
			assertThat(node.name()).isNotBlank();
			assertThat(node.numClients()).isNotNull();
			assertThat(node.numUsers()).isNotNull();
			assertThat(node.numChannels()).isNotNull();

			// For a fresh test instance, these should be reasonable values
			assertThat(node.numClients()).isGreaterThanOrEqualTo(0);
			assertThat(node.numUsers()).isGreaterThanOrEqualTo(0);
			assertThat(node.numChannels()).isGreaterThanOrEqualTo(0);
		});
	}

}
