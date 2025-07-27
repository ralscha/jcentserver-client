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

import ch.rasc.jcentserverclient.models.PresenceRequest;
import ch.rasc.jcentserverclient.models.PresenceResponse;
import ch.rasc.jcentserverclient.models.PresenceStatsRequest;
import ch.rasc.jcentserverclient.models.PresenceStatsResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo presence API client for managing channel presence information.
 *
 * <p>
 * This client provides access to Centrifugo's presence functionality, allowing you to see
 * who is currently subscribed to channels. Presence must be enabled in channel
 * configuration for these operations to work.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Get detailed presence information (all connected clients)</li>
 * <li>Get presence statistics (client and user counts only)</li>
 * <li>Client and connection metadata</li>
 * <li>User identification and custom info</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#presence">Presence API
 * Documentation</a>
 * @see <a href="https://centrifugal.dev/docs/server/presence">Presence Feature
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface PresenceClient {

	/**
	 * Get channel presence information.
	 *
	 * <p>
	 * Returns detailed information about all clients currently subscribed to the
	 * specified channel, including client IDs, user IDs, connection info, and
	 * channel-specific metadata.
	 * </p>
	 *
	 * <p>
	 * Returned information includes:
	 * </p>
	 * <ul>
	 * <li>Client ID for each connected client</li>
	 * <li>User ID (if authenticated)</li>
	 * <li>Connection info (optional custom data)</li>
	 * <li>Channel info (subscription-specific data)</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Showing "who's online" in chat rooms</li>
	 * <li>Displaying active participants in collaborative apps</li>
	 * <li>Implementing user lists and activity indicators</li>
	 * <li>Building presence-aware features</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * PresenceRequest request = PresenceRequest.builder().channel("chat:room1").build();
	 *
	 * PresenceResponse response = client.presence(request);
	 * Map<String, ClientInfo> presence = response.getResult().getPresence();
	 *
	 * for (ClientInfo clientInfo : presence.values()) {
	 * 	System.out.println("User: " + clientInfo.getUser() + ", Client: "
	 * 			+ clientInfo.getClient());
	 * }
	 * }</pre>
	 * @param request the presence request containing the channel name
	 * @return the presence response containing detailed client information
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#presence">Presence
	 * Documentation</a>
	 */
	@RequestLine("POST /presence")
	PresenceResponse presence(PresenceRequest request);

	/**
	 * Get channel presence stats.
	 *
	 * <p>
	 * Returns lightweight presence statistics for a channel without detailed client
	 * information. This is more efficient than full presence when you only need counts.
	 * </p>
	 *
	 * <p>
	 * Statistics include:
	 * </p>
	 * <ul>
	 * <li>Number of connected clients</li>
	 * <li>Number of unique users (based on user ID)</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Displaying channel activity counters</li>
	 * <li>Monitoring channel usage</li>
	 * <li>Building channel browser interfaces</li>
	 * <li>Analytics and reporting</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * PresenceStatsRequest request = PresenceStatsRequest.builder().channel("chat:room1")
	 * 		.build();
	 *
	 * PresenceStatsResponse response = client.presenceStats(request);
	 * int numClients = response.getResult().getNumClients();
	 * int numUsers = response.getResult().getNumUsers();
	 *
	 * System.out.println("Channel has " + numClients + " connections from " + numUsers
	 * 		+ " unique users");
	 * }</pre>
	 * @param request the presence stats request containing the channel name
	 * @return the presence stats response containing client and user counts
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#presence_stats">Presence Stats
	 * Documentation</a>
	 */
	@RequestLine("POST /presence_stats")
	PresenceStatsResponse presenceStats(PresenceStatsRequest request);

}
