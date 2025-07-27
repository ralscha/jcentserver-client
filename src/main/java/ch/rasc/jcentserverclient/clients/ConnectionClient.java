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

import ch.rasc.jcentserverclient.models.DisconnectRequest;
import ch.rasc.jcentserverclient.models.DisconnectResponse;
import ch.rasc.jcentserverclient.models.RefreshRequest;
import ch.rasc.jcentserverclient.models.RefreshResponse;
import ch.rasc.jcentserverclient.models.SubscribeRequest;
import ch.rasc.jcentserverclient.models.SubscribeResponse;
import ch.rasc.jcentserverclient.models.UnsubscribeRequest;
import ch.rasc.jcentserverclient.models.UnsubscribeResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo connection management API client.
 *
 * <p>
 * This client provides server-side connection management capabilities, allowing you to
 * control client subscriptions and connections from your backend. These operations are
 * particularly useful for implementing server-side subscriptions and connection lifecycle
 * management.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Server-side subscription management</li>
 * <li>Client disconnection control</li>
 * <li>Connection refresh for unidirectional transports</li>
 * <li>Channel option overrides</li>
 * <li>Session and client-specific targeting</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#subscribe">Subscribe API
 * Documentation</a>
 * @see <a href="https://centrifugal.dev/docs/server/server_api#disconnect">Disconnect API
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface ConnectionClient {

	/**
	 * Subscribe a user to a channel.
	 *
	 * <p>
	 * Creates a server-side subscription for a user to a specific channel. This is not a
	 * real-time streaming subscription request, but a command to subscribe online user
	 * sessions to a channel from the server side.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Dynamic server-side subscriptions</li>
	 * <li>Auto-subscribing users to relevant channels</li>
	 * <li>Subscription with custom channel info</li>
	 * <li>Recovery from specific stream positions</li>
	 * </ul>
	 *
	 * <p>
	 * Features:
	 * </p>
	 * <ul>
	 * <li>Custom subscription data and info</li>
	 * <li>Channel option overrides (presence, join/leave, etc.)</li>
	 * <li>Stream position recovery</li>
	 * <li>Client or session-specific targeting</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * SubscribeRequest request = SubscribeRequest.builder().user("user123")
	 * 		.channel("notifications:user123").info(Map.of("role", "subscriber"))
	 * 		.build();
	 *
	 * SubscribeResponse response = client.subscribe(request);
	 * }</pre>
	 * @param request the subscribe request containing user, channel, and subscription
	 * options
	 * @return the subscribe response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#subscribe">Subscribe
	 * Documentation</a>
	 * @see <a href="https://centrifugal.dev/docs/server/server_subs">Server-side
	 * Subscriptions</a>
	 */
	@RequestLine("POST /subscribe")
	SubscribeResponse subscribe(SubscribeRequest request);

	/**
	 * Unsubscribe a user from a channel.
	 *
	 * <p>
	 * Removes a user's subscription to a specific channel. Can target all user
	 * connections or specific client/session connections.
	 * </p>
	 *
	 * <p>
	 * Targeting options:
	 * </p>
	 * <ul>
	 * <li>All user connections (by user ID only)</li>
	 * <li>Specific client connection</li>
	 * <li>Specific session</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * UnsubscribeRequest request = UnsubscribeRequest.builder().user("user123")
	 * 		.channel("notifications:user123").build();
	 *
	 * UnsubscribeResponse response = client.unsubscribe(request);
	 * }</pre>
	 * @param request the unsubscribe request containing user, channel, and optional
	 * client/session targeting
	 * @return the unsubscribe response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#unsubscribe">Unsubscribe
	 * Documentation</a>
	 */
	@RequestLine("POST /unsubscribe")
	UnsubscribeResponse unsubscribe(UnsubscribeRequest request);

	/**
	 * Disconnect a user.
	 *
	 * <p>
	 * Forcibly disconnects user connections from the server. Useful for implementing user
	 * bans, session management, or forcing re-authentication.
	 * </p>
	 *
	 * <p>
	 * Disconnect targeting:
	 * </p>
	 * <ul>
	 * <li>All user connections</li>
	 * <li>Specific client connection</li>
	 * <li>Specific session</li>
	 * <li>All except whitelisted clients</li>
	 * </ul>
	 *
	 * <p>
	 * Supports custom disconnect codes and reasons for client-side handling.
	 * </p>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * DisconnectRequest request = DisconnectRequest.builder().user("user123")
	 * 		.disconnect(Disconnect.builder().code(4000).reason("User banned").build())
	 * 		.build();
	 *
	 * DisconnectResponse response = client.disconnect(request);
	 * }</pre>
	 * @param request the disconnect request containing user targeting and disconnect
	 * options
	 * @return the disconnect response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#disconnect">Disconnect
	 * Documentation</a>
	 */
	@RequestLine("POST /disconnect")
	DisconnectResponse disconnect(DisconnectRequest request);

	/**
	 * Refresh a user connection.
	 *
	 * <p>
	 * Refreshes user connections, primarily useful for unidirectional transports or when
	 * you need to update connection metadata. Can mark connections as expired or set new
	 * expiration times.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Refreshing unidirectional transport connections</li>
	 * <li>Updating connection expiration times</li>
	 * <li>Marking connections as expired</li>
	 * <li>Triggering connection state updates</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * RefreshRequest request = RefreshRequest.builder().user("user123")
	 * 		.expireAt(System.currentTimeMillis() / 1000 + 3600) // 1 hour from now
	 * 		.build();
	 *
	 * RefreshResponse response = client.refresh(request);
	 * }</pre>
	 * @param request the refresh request containing user targeting and refresh options
	 * @return the refresh response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#refresh">Refresh
	 * Documentation</a>
	 */
	@RequestLine("POST /refresh")
	RefreshResponse refresh(RefreshRequest request);

}
