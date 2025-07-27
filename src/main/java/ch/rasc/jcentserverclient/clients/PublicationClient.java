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

import ch.rasc.jcentserverclient.models.BroadcastRequest;
import ch.rasc.jcentserverclient.models.BroadcastResponse;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.PublishResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo publication API client for publishing data to channels.
 *
 * <p>
 * This client provides access to Centrifugo's core publication functionality, allowing
 * you to send real-time messages to connected clients through channels. It supports both
 * single-channel publishing and multi-channel broadcasting.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Publish JSON or binary data to channels</li>
 * <li>Broadcast same data to multiple channels efficiently</li>
 * <li>Publication tagging and metadata</li>
 * <li>History control and idempotency</li>
 * <li>Delta updates and versioning</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#publish">Publish API
 * Documentation</a>
 * @see <a href="https://centrifugal.dev/docs/server/server_api#broadcast">Broadcast API
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface PublicationClient {

	/**
	 * Publish data into a channel.
	 *
	 * <p>
	 * Sends data to all clients currently subscribed to the specified channel. This is
	 * the most commonly used operation for real-time messaging.
	 * </p>
	 *
	 * <p>
	 * Features:
	 * </p>
	 * <ul>
	 * <li>JSON or binary data support</li>
	 * <li>Optional history storage control</li>
	 * <li>Publication tags for metadata</li>
	 * <li>Idempotency keys for retry safety</li>
	 * <li>Delta updates for efficiency</li>
	 * <li>Version control for document state</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * PublishRequest request = PublishRequest.builder().channel("chat:room1")
	 * 		.data(Map.of("message", "Hello, World!", "user", "alice"))
	 * 		.tags(Map.of("type", "message")).build();
	 *
	 * PublishResponse response = client.publish(request);
	 * }</pre>
	 * @param request the publish request containing channel, data, and options
	 * @return the publish response containing publication offset and epoch
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#publish">Publish
	 * Documentation</a>
	 */
	@RequestLine("POST /publish")
	PublishResponse publish(PublishRequest request);

	/**
	 * Broadcast data to multiple channels.
	 *
	 * <p>
	 * Efficiently sends the same data to multiple channels in a single operation. This is
	 * more efficient than making multiple individual publish calls when you need to send
	 * identical data to several channels.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Sending notifications to multiple user channels</li>
	 * <li>Broadcasting system announcements</li>
	 * <li>Synchronizing data across related channels</li>
	 * <li>Fan-out messaging patterns</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * BroadcastRequest request = BroadcastRequest.builder()
	 * 		.channels(List.of("user:123", "user:456", "user:789"))
	 * 		.data(Map.of("notification", "System maintenance in 5 minutes")).build();
	 *
	 * BroadcastResponse response = client.broadcast(request);
	 * }</pre>
	 * @param request the broadcast request containing channels list, data, and options
	 * @return the broadcast response containing individual publish results for each
	 * channel
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#broadcast">Broadcast
	 * Documentation</a>
	 */
	@RequestLine("POST /broadcast")
	BroadcastResponse broadcast(BroadcastRequest request);

}
