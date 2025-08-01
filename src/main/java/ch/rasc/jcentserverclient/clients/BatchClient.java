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

import ch.rasc.jcentserverclient.models.BatchRequest;
import ch.rasc.jcentserverclient.models.BatchResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo batch API client for executing multiple commands efficiently.
 *
 * <p>
 * This client provides access to Centrifugo's batch functionality, allowing you to send
 * multiple API commands in a single HTTP request. This reduces network round-trip time
 * and can significantly improve performance when executing multiple operations.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Execute multiple commands in one request</li>
 * <li>Sequential or parallel command processing</li>
 * <li>Individual error handling for each command</li>
 * <li>Reduced network latency through batching</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#batch">Batch API
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface BatchClient {

	/**
	 * Execute multiple commands in a single batch request.
	 *
	 * <p>
	 * Sends multiple API commands in one HTTP request and receives individual responses
	 * for each command. Commands are processed sequentially by default, but can be
	 * processed in parallel by setting the parallel flag.
	 * </p>
	 *
	 * <p>
	 * Processing modes:
	 * </p>
	 * <ul>
	 * <li><strong>Sequential</strong> (default): Commands processed one by one in
	 * order</li>
	 * <li><strong>Parallel</strong>: Commands processed concurrently for better
	 * performance</li>
	 * </ul>
	 *
	 * <p>
	 * Benefits:
	 * </p>
	 * <ul>
	 * <li>Reduced network round-trip time</li>
	 * <li>Lower connection overhead</li>
	 * <li>Better performance for multiple operations</li>
	 * <li>Atomic-like operations when sequential</li>
	 * </ul>
	 *
	 * <p>
	 * Important notes:
	 * </p>
	 * <ul>
	 * <li>Each command result must be checked individually for errors</li>
	 * <li>Parallel processing may provide better latency with Redis engine</li>
	 * <li>Commands are independent - one failure doesn't stop others</li>
	 * </ul>
	 *
	 * <p>
	 * Example - sequential processing:
	 * </p>
	 *
	 * <pre>{@code
	 * BatchRequest request = BatchRequest.builder().commands(List.of(
	 * 		Command.builder()
	 * 				.publish(PublishRequest.builder().channel("chat:room1")
	 * 						.data(Map.of("message", "Hello")).build())
	 * 				.build(),
	 * 		Command.builder()
	 * 				.publish(PublishRequest.builder().channel("chat:room2")
	 * 						.data(Map.of("message", "World")).build())
	 * 				.build()))
	 * 		.build();
	 *
	 * BatchResponse response = client.batch(request);
	 * for (Reply reply : response.getReplies()) {
	 * 	if (reply.getError() != null) {
	 * 		System.err.println("Command failed: " + reply.getError().getMessage());
	 * 	}
	 * }
	 * }</pre>
	 *
	 * <p>
	 * Example - parallel processing:
	 * </p>
	 *
	 * <pre>{@code
	 * BatchRequest request = BatchRequest.builder().commands(commands).parallel(true) // Enable
	 * 																				// parallel
	 * 																				// processing
	 * 		.build();
	 *
	 * BatchResponse response = client.batch(request);
	 * }</pre>
	 * @param request the batch request containing multiple commands and processing
	 * options
	 * @return the batch response containing individual replies for each command
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#batch">Batch
	 * Documentation</a>
	 */
	@RequestLine("POST /batch")
	BatchResponse batch(BatchRequest request);

	/**
	 * Execute multiple commands in a single batch request.
	 *
	 * <p>
	 * Sends multiple API commands in one HTTP request and receives individual responses
	 * for each command. Commands are processed sequentially by default, but can be
	 * processed in parallel by setting the parallel flag.
	 * </p>
	 *
	 * <p>
	 * Processing modes:
	 * </p>
	 * <ul>
	 * <li><strong>Sequential</strong> (default): Commands processed one by one in
	 * order</li>
	 * <li><strong>Parallel</strong>: Commands processed concurrently for better
	 * performance</li>
	 * </ul>
	 *
	 * <p>
	 * Benefits:
	 * </p>
	 * <ul>
	 * <li>Reduced network round-trip time</li>
	 * <li>Lower connection overhead</li>
	 * <li>Better performance for multiple operations</li>
	 * <li>Atomic-like operations when sequential</li>
	 * </ul>
	 *
	 * <p>
	 * Important notes:
	 * </p>
	 * <ul>
	 * <li>Each command result must be checked individually for errors</li>
	 * <li>Parallel processing may provide better latency with Redis engine</li>
	 * <li>Commands are independent - one failure doesn't stop others</li>
	 * </ul>
	 *
	 * <p>
	 * Example - sequential processing:
	 * </p>
	 *
	 * <pre>{@code
	 * BatchRequest request = BatchRequest.builder().commands(List.of(
	 * 		Command.builder()
	 * 				.publish(PublishRequest.builder().channel("chat:room1")
	 * 						.data(Map.of("message", "Hello")).build())
	 * 				.build(),
	 * 		Command.builder()
	 * 				.publish(PublishRequest.builder().channel("chat:room2")
	 * 						.data(Map.of("message", "World")).build())
	 * 				.build()))
	 * 		.build();
	 *
	 * BatchResponse response = client.batch(request);
	 * for (Reply reply : response.getReplies()) {
	 * 	if (reply.getError() != null) {
	 * 		System.err.println("Command failed: " + reply.getError().getMessage());
	 * 	}
	 * }
	 * }</pre>
	 *
	 * <p>
	 * Example - parallel processing:
	 * </p>
	 *
	 * <pre>{@code
	 * BatchRequest request = BatchRequest.builder().commands(commands).parallel(true) // Enable
	 * 																				// parallel
	 * 																				// processing
	 * 		.build();
	 *
	 * BatchResponse response = client.batch(request);
	 * }</pre>
	 * @param fn the function to configure the batch request
	 * @return the batch response containing individual replies for each command
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#batch">Batch
	 * Documentation</a>
	 */
	default BatchResponse batch(Function<BatchRequest.Builder, BatchRequest.Builder> fn) {
		return this.batch(fn.apply(BatchRequest.builder()).build());
	}

}
