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

import ch.rasc.jcentserverclient.models.InvalidateUserTokensRequest;
import ch.rasc.jcentserverclient.models.RevokeTokenRequest;

@DisplayName("Token Client Integration Tests")
class TokenClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should surface transport error for revoke token when endpoint is unavailable")
	void shouldSurfaceTransportErrorForRevokeTokenWhenEndpointIsUnavailable() {
		String uid = "token-uid-" + System.currentTimeMillis();

		assertThatThrownBy(() -> this.client.token().revokeToken(RevokeTokenRequest.builder().uid(uid).build()))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

	@Test
	@DisplayName("Should surface transport error for expiring revoke token when endpoint is unavailable")
	void shouldSurfaceTransportErrorForExpiringRevokeTokenWhenEndpointIsUnavailable() {
		String uid = "expiring-token-uid-" + System.currentTimeMillis();
		long oneHourFromNow = System.currentTimeMillis() / 1000 + 3600;

		assertThatThrownBy(() -> this.client.token()
			.revokeToken(RevokeTokenRequest.builder().uid(uid).expireAt(oneHourFromNow).build()))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

	@Test
	@DisplayName("Should surface transport error for invalidate user tokens when endpoint is unavailable")
	void shouldSurfaceTransportErrorForInvalidateUserTokensWhenEndpointIsUnavailable() {
		String user = "token-user-" + System.currentTimeMillis();

		assertThatThrownBy(
				() -> this.client.token().invalidateUserTokens(InvalidateUserTokensRequest.builder().user(user).build()))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

	@Test
	@DisplayName("Should surface transport error for filtered invalidate user tokens when endpoint is unavailable")
	void shouldSurfaceTransportErrorForFilteredInvalidateUserTokensWhenEndpointIsUnavailable() {
		String user = "filtered-token-user-" + System.currentTimeMillis();
		long now = System.currentTimeMillis() / 1000;

		InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
			.user(user)
			.issuedBefore(now)
			.expireAt(now + 3600)
			.channel("private:" + user)
			.build();

		assertThatThrownBy(() -> this.client.token().invalidateUserTokens(request))
			.isInstanceOfSatisfying(ApiException.class, e -> {
				assertThat(e.getStatusCode()).isEqualTo(404);
				assertThat(e.getError()).isNull();
				assertThat(e.getResponseBody()).containsIgnoringCase("page");
			});
	}

}