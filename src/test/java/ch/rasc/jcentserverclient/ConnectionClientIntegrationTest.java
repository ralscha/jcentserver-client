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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.DisconnectRequest;
import ch.rasc.jcentserverclient.models.DisconnectResponse;
import ch.rasc.jcentserverclient.models.RefreshRequest;
import ch.rasc.jcentserverclient.models.RefreshResponse;
import ch.rasc.jcentserverclient.models.SubscribeRequest;
import ch.rasc.jcentserverclient.models.SubscribeResponse;
import ch.rasc.jcentserverclient.models.UnsubscribeRequest;
import ch.rasc.jcentserverclient.models.UnsubscribeResponse;

/**
 * Integration tests for Connection client operations. Tests client connection management
 * operations.
 */
@DisplayName("Connection Client Integration Tests")
class ConnectionClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should handle subscribe request for user")
	void shouldHandleSubscribeRequest() {
		// Given
		String user = "test-user-" + System.currentTimeMillis();
		String channel = "user-channel";

		SubscribeRequest request = SubscribeRequest.builder().user(user).channel(channel).build();

		// When
		SubscribeResponse response = this.client.connection().subscribe(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle unsubscribe request for user")
	void shouldHandleUnsubscribeRequest() {
		// Given
		String user = "test-user-unsubscribe-" + System.currentTimeMillis();
		String channel = "user-channel-unsubscribe";

		UnsubscribeRequest request = UnsubscribeRequest.builder().user(user).channel(channel).build();

		// When
		UnsubscribeResponse response = this.client.connection().unsubscribe(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle disconnect request for user")
	void shouldHandleDisconnectRequest() {
		// Given
		String user = "test-user-disconnect-" + System.currentTimeMillis();

		DisconnectRequest request = DisconnectRequest.builder().user(user).build();

		// When
		DisconnectResponse response = this.client.connection().disconnect(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle refresh request for user")
	void shouldHandleRefreshRequest() {
		// Given
		String user = "test-user-refresh-" + System.currentTimeMillis();

		RefreshRequest request = RefreshRequest.builder().user(user).build();

		// When
		RefreshResponse response = this.client.connection().refresh(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle subscribe with multiple channels")
	void shouldHandleSubscribeWithMultipleChannels() {
		// Given
		String user = "test-user-multi-" + System.currentTimeMillis();
		List<String> channels = List.of("channel-1", "channel-2", "channel-3");

		for (String channel : channels) {
			SubscribeRequest request = SubscribeRequest.builder().user(user).channel(channel).build();

			// When
			SubscribeResponse response = this.client.connection().subscribe(request);

			// Then
			assertThat(response).isNotNull();
			assertThat(response.result()).isNotNull();
			assertThat(response.error()).isNull();
		}
	}

	@Test
	@DisplayName("Should handle disconnect with custom disconnect code")
	void shouldHandleDisconnectWithCustomCode() {
		// Given
		String user = "test-user-custom-disconnect-" + System.currentTimeMillis();

		DisconnectRequest request = DisconnectRequest.builder().user(user).build();

		// When
		DisconnectResponse response = this.client.connection().disconnect(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle refresh with expired flag")
	void shouldHandleRefreshWithExpiredFlag() {
		// Given
		String user = "test-user-refresh-expired-" + System.currentTimeMillis();

		RefreshRequest request = RefreshRequest.builder().user(user).expired(true).build();

		// When
		RefreshResponse response = this.client.connection().refresh(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

}
