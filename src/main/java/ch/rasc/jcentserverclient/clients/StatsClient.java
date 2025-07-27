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

import ch.rasc.jcentserverclient.models.ChannelsRequest;
import ch.rasc.jcentserverclient.models.ChannelsResponse;
import ch.rasc.jcentserverclient.models.ConnectionsRequest;
import ch.rasc.jcentserverclient.models.ConnectionsResponse;
import ch.rasc.jcentserverclient.models.InfoRequest;
import ch.rasc.jcentserverclient.models.InfoResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo stats API client for retrieving server and channel statistics.
 *
 * <p>
 * This client provides access to Centrifugo's statistics and information endpoints,
 * allowing you to monitor server state, active channels, and connection information.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Server node information and cluster status</li>
 * <li>Active channels listing and filtering</li>
 * <li>Connection information and user sessions</li>
 * <li>Performance metrics and uptime data</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#info">Info API
 * Documentation</a>
 * @see <a href="https://centrifugal.dev/docs/server/server_api#channels">Channels API
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface StatsClient {

	/**
	 * Get active channels information.
	 *
	 * <p>
	 * Returns information about all currently active channels (channels with one or more
	 * active subscribers). Can be filtered using glob patterns to match specific channel
	 * names.
	 * </p>
	 *
	 * <p>
	 * Channel information includes:
	 * </p>
	 * <ul>
	 * <li>Channel name</li>
	 * <li>Number of currently connected clients</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Monitoring active channels</li>
	 * <li>Building channel browsers</li>
	 * <li>Administrative dashboards</li>
	 * <li>Debugging subscription issues</li>
	 * </ul>
	 *
	 * <p>
	 * <strong>Warning:</strong> For large deployments with many active channels (>10k),
	 * this operation can be expensive. Consider using real-time analytics approaches for
	 * massive setups.
	 * </p>
	 *
	 * <p>
	 * Example - get all channels:
	 * </p>
	 *
	 * <pre>{@code
	 * ChannelsRequest request = ChannelsRequest.builder().build();
	 * ChannelsResponse response = client.channels(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - filter channels by pattern:
	 * </p>
	 *
	 * <pre>{@code
	 * ChannelsRequest request = ChannelsRequest.builder().pattern("chat:*") // Only
	 * 																		// channels
	 * 																		// starting
	 * 																		// with
	 * 																		// "chat:"
	 * 		.build();
	 * ChannelsResponse response = client.channels(request);
	 * }</pre>
	 * @param request the channels request containing optional pattern filter
	 * @return the channels response containing active channels and their client counts
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#channels">Channels
	 * Documentation</a>
	 */
	@RequestLine("POST /channels")
	ChannelsResponse channels(ChannelsRequest request);

	/**
	 * Get connections information.
	 *
	 * <p>
	 * Returns information about currently connected clients. Can filter connections by
	 * user ID or use expressions for more complex filtering.
	 * </p>
	 *
	 * <p>
	 * Connection information includes:
	 * </p>
	 * <ul>
	 * <li>Client ID and user ID</li>
	 * <li>Connection metadata and state</li>
	 * <li>Subscribed channels</li>
	 * <li>Connection and subscription tokens</li>
	 * <li>Application name and version</li>
	 * <li>Transport and protocol information</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Monitoring user connections</li>
	 * <li>Debugging connection issues</li>
	 * <li>Administrative user management</li>
	 * <li>Session auditing and analysis</li>
	 * </ul>
	 *
	 * <p>
	 * Example - get all connections:
	 * </p>
	 *
	 * <pre>{@code
	 * ConnectionsRequest request = ConnectionsRequest.builder().build();
	 * ConnectionsResponse response = client.connections(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - get connections for specific user:
	 * </p>
	 *
	 * <pre>{@code
	 * ConnectionsRequest request = ConnectionsRequest.builder().user("user123").build();
	 * ConnectionsResponse response = client.connections(request);
	 * }</pre>
	 * @param request the connections request containing optional user or expression
	 * filters
	 * @return the connections response containing matching connection information
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#connections">Connections
	 * Documentation</a>
	 */
	@RequestLine("POST /connections")
	ConnectionsResponse connections(ConnectionsRequest request);

	/**
	 * Get server information.
	 *
	 * <p>
	 * Returns detailed information about all Centrifugo nodes in the cluster, including
	 * performance metrics, version information, and runtime statistics.
	 * </p>
	 *
	 * <p>
	 * Node information includes:
	 * </p>
	 * <ul>
	 * <li>Node ID, name, and version</li>
	 * <li>Uptime and process information</li>
	 * <li>Number of clients, users, and channels</li>
	 * <li>Number of subscriptions</li>
	 * <li>Performance metrics (CPU, memory)</li>
	 * <li>Custom metrics if configured</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Health monitoring and alerting</li>
	 * <li>Performance analysis</li>
	 * <li>Cluster management</li>
	 * <li>Capacity planning</li>
	 * <li>Administrative dashboards</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * InfoRequest request = InfoRequest.builder().build();
	 * InfoResponse response = client.info(request);
	 *
	 * for (NodeResult node : response.getResult().getNodes()) {
	 * 	System.out.println("Node: " + node.getName() + ", Clients: "
	 * 			+ node.getNumClients() + ", Uptime: " + node.getUptime());
	 * }
	 * }</pre>
	 * @param request the info request (empty object)
	 * @return the info response containing information about all cluster nodes
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#info">Info
	 * Documentation</a>
	 */
	@RequestLine("POST /info")
	InfoResponse info(InfoRequest request);

}
