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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ch.rasc.jcentserverclient.clients.BatchClient;
import ch.rasc.jcentserverclient.clients.ChannelsClient;
import ch.rasc.jcentserverclient.clients.ConnectionClient;
import ch.rasc.jcentserverclient.clients.HistoryClient;
import ch.rasc.jcentserverclient.clients.PresenceClient;
import ch.rasc.jcentserverclient.clients.PublicationClient;
import ch.rasc.jcentserverclient.clients.StatsClient;
import ch.rasc.jcentserverclient.clients.TokenClient;
import ch.rasc.jcentserverclient.clients.UserBlockClient;
import ch.rasc.jcentserverclient.clients.UserStatusClient;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * Centrifugo Server HTTP API Client
 *
 * <p>
 * This client provides a Java interface to interact with Centrifugo's HTTP API.
 * Centrifugo is a real-time messaging server that provides publish-subscribe
 * functionality for building scalable real-time applications.
 * </p>
 *
 * <p>
 * The API supports various operations including:
 * </p>
 * <ul>
 * <li>Publishing messages to channels</li>
 * <li>Managing client connections and subscriptions</li>
 * <li>Retrieving channel history and presence information</li>
 * <li>User status management</li>
 * <li>Push notifications</li>
 * <li>Token management</li>
 * <li>Batch operations</li>
 * </ul>
 *
 * <p>
 * Usage example:
 * </p>
 *
 * <pre>{@code
 * CentrifugoServerApiClient client = CentrifugoServerApiClient.create(
 * 		config -> config.apiKey("your-api-key").baseUrl("http://localhost:8000/api"));
 *
 * PublishResponse response = client.publication().publish(PublishRequest.builder()
 * 		.channel("chat").data(Map.of("message", "Hello World")).build());
 * }</pre>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api">Centrifugo Server API
 * Documentation</a>
 * @since 1.0.0
 */
public class CentrifugoServerApiClient {

	// Publication API endpoints
	private PublicationClient publication;

	// Connection management API endpoints
	private ConnectionClient connection;

	// History API endpoints
	private HistoryClient history;

	// Presence API endpoints
	private PresenceClient presence;

	// Stats API endpoints
	private StatsClient stats;

	// Channels API endpoints
	private ChannelsClient channels;

	// User block API endpoints
	private UserBlockClient userBlock;

	// User status API endpoints
	private UserStatusClient userStatus;

	// Token API endpoints
	public TokenClient token;

	// Batch API endpoints
	private BatchClient batch;

	/**
	 * Create a new Centrifugo Server API client with custom configuration.
	 * @param fn function to configure the client builder
	 * @return configured client instance
	 */
	public static CentrifugoServerApiClient create(Function<Configuration.Builder, Configuration.Builder> fn) {
		return create(fn.apply(Configuration.builder()).build());
	}

	/**
	 * Create a new Centrifugo Server API client with the given configuration.
	 * @param configuration the client configuration
	 * @return configured client instance
	 */
	public static CentrifugoServerApiClient create(Configuration configuration) {

		CentrifugoServerApiClient client = new CentrifugoServerApiClient();
		JacksonDecoder jsonDecoder = new JacksonDecoder();
		JacksonEncoder jsonEncoder = new JacksonEncoder();

		List<RequestInterceptor> interceptors = new ArrayList<>();

		if (configuration.additionalRequestInterceptor() != null) {
			interceptors.add(configuration.additionalRequestInterceptor());
		}

		// Add API key interceptor for Centrifugo authentication
		if (configuration.apiKey() != null && !configuration.apiKey().isBlank()) {
			interceptors.add(new ApiKeyRequestInterceptor(configuration.apiKey()));
		}

		String baseUrl = configuration.baseUrl();

		client.publication = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(PublicationClient.class, baseUrl);

		client.connection = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(ConnectionClient.class, baseUrl);

		client.history = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(HistoryClient.class, baseUrl);

		client.presence = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(PresenceClient.class, baseUrl);

		client.stats = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(StatsClient.class, baseUrl);

		client.channels = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(ChannelsClient.class, baseUrl);

		client.userBlock = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(UserBlockClient.class, baseUrl);

		client.userStatus = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(UserStatusClient.class, baseUrl);

		client.token = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(TokenClient.class, baseUrl);

		client.batch = jsonClientBuilder(configuration, jsonDecoder, jsonEncoder, interceptors)
			.target(BatchClient.class, baseUrl);

		return client;
	}

	/**
	 * Get the Publication API client for publishing data to channels.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Publishing data to a single channel</li>
	 * <li>Broadcasting data to multiple channels</li>
	 * </ul>
	 * @return the publication client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#publish">Publish API
	 * Documentation</a>
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#broadcast">Broadcast
	 * API Documentation</a>
	 */
	public PublicationClient publication() {
		return this.publication;
	}

	/**
	 * Get the Connection Management API client for managing client connections.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Subscribing users to channels</li>
	 * <li>Unsubscribing users from channels</li>
	 * <li>Disconnecting users</li>
	 * <li>Refreshing user connections</li>
	 * </ul>
	 * @return the connection client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#subscribe">Subscribe
	 * API Documentation</a>
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#disconnect">Disconnect
	 * API Documentation</a>
	 */
	public ConnectionClient connection() {
		return this.connection;
	}

	/**
	 * Get the History API client for managing channel history.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Retrieving channel message history</li>
	 * <li>Removing channel history</li>
	 * </ul>
	 * @return the history client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#history">History API
	 * Documentation</a>
	 */
	public HistoryClient history() {
		return this.history;
	}

	/**
	 * Get the Presence API client for managing channel presence information.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Getting channel presence information (all connected clients)</li>
	 * <li>Getting channel presence statistics (counts only)</li>
	 * </ul>
	 * @return the presence client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#presence">Presence API
	 * Documentation</a>
	 */
	public PresenceClient presence() {
		return this.presence;
	}

	/**
	 * Get the Stats API client for retrieving server statistics.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Getting active channels information</li>
	 * <li>Getting connections information</li>
	 * <li>Getting server node information</li>
	 * </ul>
	 * @return the stats client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#channels">Channels API
	 * Documentation</a>
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#info">Info API
	 * Documentation</a>
	 */
	public StatsClient stats() {
		return this.stats;
	}

	/**
	 * Get the Channels API client for managing channel information.
	 *
	 * <p>
	 * This is an alias for the stats client that provides channel-specific operations.
	 * </p>
	 * @return the channels client (same as stats client)
	 */
	public ChannelsClient channels() {
		return this.channels;
	}

	/**
	 * Get the User Block API client for managing user blocking functionality.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Blocking users</li>
	 * <li>Unblocking users</li>
	 * </ul>
	 * @return the user block client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#block_user">Block User
	 * API Documentation</a>
	 */
	public UserBlockClient userBlock() {
		return this.userBlock;
	}

	/**
	 * Get the User Status API client for managing user status information.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Updating user status</li>
	 * <li>Getting user status</li>
	 * <li>Deleting user status</li>
	 * </ul>
	 * @return the user status client
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#update_user_status">User Status API
	 * Documentation</a>
	 */
	public UserStatusClient userStatus() {
		return this.userStatus;
	}

	/**
	 * Get the Token API client for managing authentication tokens.
	 *
	 * <p>
	 * Supports operations like:
	 * </p>
	 * <ul>
	 * <li>Revoking individual tokens</li>
	 * <li>Invalidating all user tokens</li>
	 * </ul>
	 * @return the token client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#revoke_token">Token
	 * API Documentation</a>
	 */
	public TokenClient token() {
		return this.token;
	}

	/**
	 * Get the Batch API client for sending multiple commands in a single request.
	 *
	 * <p>
	 * The batch API allows you to execute multiple API commands efficiently in a single
	 * HTTP request, reducing network round-trip time.
	 * </p>
	 * @return the batch client
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#batch">Batch API
	 * Documentation</a>
	 */
	public BatchClient batch() {
		return this.batch;
	}

	private static Builder jsonClientBuilder(Configuration configuration, JacksonDecoder jsonDecoder,
			JacksonEncoder jsonEncoder, List<RequestInterceptor> interceptors) {
		return Feign.builder()
			.client(configuration.client())
			.errorDecoder(configuration.errorDecoder())
			.retryer(configuration.retryer())
			.options(configuration.feignOptions())
			.logger(configuration.logger())
			.logLevel(configuration.logLevel())
			.decoder(jsonDecoder)
			.encoder(jsonEncoder)
			.requestInterceptors(interceptors);
	}

}
