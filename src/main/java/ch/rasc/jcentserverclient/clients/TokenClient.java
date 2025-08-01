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
package ch.rasc.jcentserverclient.clients;

import java.util.function.Function;

import ch.rasc.jcentserverclient.models.InvalidateUserTokensRequest;
import ch.rasc.jcentserverclient.models.InvalidateUserTokensResponse;
import ch.rasc.jcentserverclient.models.RevokeTokenRequest;
import ch.rasc.jcentserverclient.models.RevokeTokenResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo token API client for managing authentication tokens.
 *
 * <p>
 * This client provides access to Centrifugo's token management functionality, allowing
 * you to revoke individual tokens or invalidate all tokens for users. This is essential
 * for implementing secure authentication flows and session management.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Revoke specific tokens by UID</li>
 * <li>Invalidate all user tokens with filtering options</li>
 * <li>Time-based token invalidation</li>
 * <li>Channel-specific token invalidation</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#revoke_token">Token API
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface TokenClient {

	/**
	 * Revoke a token.
	 *
	 * <p>
	 * Revokes a specific token by its unique identifier (UID). Once revoked, the token
	 * will be considered invalid and connections using it will be terminated with an
	 * appropriate disconnect reason.
	 * </p>
	 *
	 * <p>
	 * Features:
	 * </p>
	 * <ul>
	 * <li>Immediate token invalidation</li>
	 * <li>Automatic connection termination</li>
	 * <li>Optional expiration time for revocation</li>
	 * <li>Precise token targeting by UID</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Implementing logout functionality</li>
	 * <li>Revoking compromised tokens</li>
	 * <li>Session management and cleanup</li>
	 * <li>Security incident response</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * RevokeTokenRequest request = RevokeTokenRequest.builder().uid("token-uuid-here")
	 * 		.build();
	 *
	 * RevokeTokenResponse response = client.revokeToken(request);
	 * }</pre>
	 *
	 * <p>
	 * Example with expiration:
	 * </p>
	 *
	 * <pre>{@code
	 * long futureTime = System.currentTimeMillis() / 1000 + 3600; // 1 hour from now
	 * RevokeTokenRequest request = RevokeTokenRequest.builder().uid("token-uuid-here")
	 * 		.expireAt(futureTime).build();
	 *
	 * RevokeTokenResponse response = client.revokeToken(request);
	 * }</pre>
	 * @param request the revoke token request containing token UID and optional
	 * expiration
	 * @return the revoke token response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#revoke_token">Revoke
	 * Token Documentation</a>
	 */
	@RequestLine("POST /revoke_token")
	RevokeTokenResponse revokeToken(RevokeTokenRequest request);

	/**
	 * Revoke a token.
	 *
	 * <p>
	 * Revokes a specific token by its unique identifier (UID). Once revoked, the token
	 * will be considered invalid and connections using it will be terminated with an
	 * appropriate disconnect reason.
	 * </p>
	 *
	 * <p>
	 * Features:
	 * </p>
	 * <ul>
	 * <li>Immediate token invalidation</li>
	 * <li>Automatic connection termination</li>
	 * <li>Optional expiration time for revocation</li>
	 * <li>Precise token targeting by UID</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Implementing logout functionality</li>
	 * <li>Revoking compromised tokens</li>
	 * <li>Session management and cleanup</li>
	 * <li>Security incident response</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * RevokeTokenRequest request = RevokeTokenRequest.builder().uid("token-uuid-here")
	 * 		.build();
	 *
	 * RevokeTokenResponse response = client.revokeToken(request);
	 * }</pre>
	 *
	 * <p>
	 * Example with expiration:
	 * </p>
	 *
	 * <pre>{@code
	 * long futureTime = System.currentTimeMillis() / 1000 + 3600; // 1 hour from now
	 * RevokeTokenRequest request = RevokeTokenRequest.builder().uid("token-uuid-here")
	 * 		.expireAt(futureTime).build();
	 *
	 * RevokeTokenResponse response = client.revokeToken(request);
	 * }</pre>
	 * @param uid the unique identifier of the token to revoke
	 * @return the revoke token response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#revoke_token">Revoke
	 * Token Documentation</a>
	 */
	default RevokeTokenResponse revokeToken(String uid) {
		return this.revokeToken(RevokeTokenRequest.of(uid));
	}

	/**
	 * Invalidate all tokens for a user.
	 *
	 * <p>
	 * Invalidates all tokens associated with a specific user. This is a powerful
	 * operation that can terminate all user sessions across all devices and applications.
	 * Supports various filtering options for fine-grained control.
	 * </p>
	 *
	 * <p>
	 * Invalidation options:
	 * </p>
	 * <ul>
	 * <li>All user tokens</li>
	 * <li>Tokens issued before a specific time</li>
	 * <li>Channel-specific tokens only</li>
	 * <li>Optional expiration time for invalidation</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Global user logout (all devices)</li>
	 * <li>Security breach response</li>
	 * <li>Account compromise mitigation</li>
	 * <li>Forced re-authentication</li>
	 * <li>Password change enforcement</li>
	 * </ul>
	 *
	 * <p>
	 * Example - invalidate all user tokens:
	 * </p>
	 *
	 * <pre>{@code
	 * InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
	 * 		.user("user123").build();
	 *
	 * InvalidateUserTokensResponse response = client.invalidateUserTokens(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - invalidate tokens issued before a specific time:
	 * </p>
	 *
	 * <pre>{@code
	 * long passwordChangeTime = System.currentTimeMillis() / 1000;
	 * InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
	 * 		.user("user123").issuedBefore(passwordChangeTime).build();
	 *
	 * InvalidateUserTokensResponse response = client.invalidateUserTokens(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - invalidate channel-specific tokens:
	 * </p>
	 *
	 * <pre>{@code
	 * InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
	 * 		.user("user123").channel("private:user123").build();
	 *
	 * InvalidateUserTokensResponse response = client.invalidateUserTokens(request);
	 * }</pre>
	 * @param request the invalidate user tokens request containing user ID and filtering
	 * options
	 * @return the invalidate user tokens response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#invalidate_user_tokens">Invalidate
	 * User Tokens Documentation</a>
	 */
	@RequestLine("POST /invalidate_user_tokens")
	InvalidateUserTokensResponse invalidateUserTokens(InvalidateUserTokensRequest request);

	/**
	 * Invalidate all tokens for a user.
	 *
	 * <p>
	 * Invalidates all tokens associated with a specific user. This is a powerful
	 * operation that can terminate all user sessions across all devices and applications.
	 * Supports various filtering options for fine-grained control.
	 * </p>
	 *
	 * <p>
	 * Invalidation options:
	 * </p>
	 * <ul>
	 * <li>All user tokens</li>
	 * <li>Tokens issued before a specific time</li>
	 * <li>Channel-specific tokens only</li>
	 * <li>Optional expiration time for invalidation</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Global user logout (all devices)</li>
	 * <li>Security breach response</li>
	 * <li>Account compromise mitigation</li>
	 * <li>Forced re-authentication</li>
	 * <li>Password change enforcement</li>
	 * </ul>
	 *
	 * <p>
	 * Example - invalidate all user tokens:
	 * </p>
	 *
	 * <pre>{@code
	 * InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
	 * 		.user("user123").build();
	 *
	 * InvalidateUserTokensResponse response = client.invalidateUserTokens(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - invalidate tokens issued before a specific time:
	 * </p>
	 *
	 * <pre>{@code
	 * long passwordChangeTime = System.currentTimeMillis() / 1000;
	 * InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
	 * 		.user("user123").issuedBefore(passwordChangeTime).build();
	 *
	 * InvalidateUserTokensResponse response = client.invalidateUserTokens(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - invalidate channel-specific tokens:
	 * </p>
	 *
	 * <pre>{@code
	 * InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
	 * 		.user("user123").channel("private:user123").build();
	 *
	 * InvalidateUserTokensResponse response = client.invalidateUserTokens(request);
	 * }</pre>
	 * @param fn the function to configure the invalidate user tokens request
	 * @return the invalidate user tokens response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#invalidate_user_tokens">Invalidate
	 * User Tokens Documentation</a>
	 */
	default InvalidateUserTokensResponse invalidateUserTokens(
			Function<InvalidateUserTokensRequest.Builder, InvalidateUserTokensRequest.Builder> fn) {
		return this.invalidateUserTokens(fn.apply(InvalidateUserTokensRequest.builder()).build());
	}

}
