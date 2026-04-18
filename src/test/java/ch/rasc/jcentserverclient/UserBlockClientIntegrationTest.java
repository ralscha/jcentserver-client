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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.BlockUserRequest;
import ch.rasc.jcentserverclient.models.UnblockUserRequest;

@DisplayName("User Block Client Integration Tests")
class UserBlockClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should surface transport error for block user when endpoint is unavailable")
	void shouldSurfaceTransportErrorForBlockUserWhenEndpointIsUnavailable() {
		String user = "blocked-user-" + System.currentTimeMillis();

		BlockUserRequest request = BlockUserRequest.builder().user(user).build();

		assertThatThrownBy(() -> this.client.userBlock().blockUser(request))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

	@Test
	@DisplayName("Should surface transport error for expiring block user when endpoint is unavailable")
	void shouldSurfaceTransportErrorForExpiringBlockUserWhenEndpointIsUnavailable() {
		String user = "temporary-block-user-" + System.currentTimeMillis();
		long oneHourFromNow = System.currentTimeMillis() / 1000 + 3600;

		BlockUserRequest request = BlockUserRequest.builder().user(user).expireAt(oneHourFromNow).build();

		assertThatThrownBy(() -> this.client.userBlock().blockUser(request))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

	@Test
	@DisplayName("Should surface transport error for unblock user when endpoint is unavailable")
	void shouldSurfaceTransportErrorForUnblockUserWhenEndpointIsUnavailable() {
		String user = "unblocked-user-" + System.currentTimeMillis();

		UnblockUserRequest request = UnblockUserRequest.builder().user(user).build();

		assertThatThrownBy(() -> this.client.userBlock().unblockUser(request))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

	@Test
	@DisplayName("Should surface transport error for unblock overload when endpoint is unavailable")
	void shouldSurfaceTransportErrorForUnblockOverloadWhenEndpointIsUnavailable() {
		String user = "unblocked-convenience-user-" + System.currentTimeMillis();

		assertThatThrownBy(() -> this.client.userBlock().unblockUser(user))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

}