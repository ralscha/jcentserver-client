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

import ch.rasc.jcentserverclient.models.BlockUserRequest;
import ch.rasc.jcentserverclient.models.BlockUserResponse;
import ch.rasc.jcentserverclient.models.UnblockUserRequest;
import ch.rasc.jcentserverclient.models.UnblockUserResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo user block API client for managing user blocking functionality.
 *
 * <p>
 * This client provides access to Centrifugo's user blocking functionality, allowing you
 * to temporarily or permanently prevent users from connecting to your Centrifugo server.
 * Blocked users will be unable to establish connections until unblocked.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Block users with optional expiration time</li>
 * <li>Unblock users to restore access</li>
 * <li>Permanent or temporary blocking</li>
 * <li>Immediate connection termination for blocked users</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#block_user">User Block API
 * Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface UserBlockClient {

	/**
	 * Block a user.
	 *
	 * <p>
	 * Blocks a user from connecting to Centrifugo. Once blocked, the user will be
	 * immediately disconnected if currently connected and will not be able to establish
	 * new connections until unblocked.
	 * </p>
	 *
	 * <p>
	 * Blocking features:
	 * </p>
	 * <ul>
	 * <li>Immediate disconnection of existing user connections</li>
	 * <li>Prevention of new connection attempts</li>
	 * <li>Optional expiration time for temporary blocks</li>
	 * <li>Permanent blocking when no expiration is set</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Implementing user bans and suspensions</li>
	 * <li>Temporary timeouts for misbehavior</li>
	 * <li>Administrative user management</li>
	 * <li>Abuse prevention and moderation</li>
	 * </ul>
	 *
	 * <p>
	 * Example - permanent block:
	 * </p>
	 *
	 * <pre>{@code
	 * BlockUserRequest request = BlockUserRequest.builder().user("user123").build();
	 *
	 * BlockUserResponse response = client.blockUser(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - temporary block (1 hour):
	 * </p>
	 *
	 * <pre>{@code
	 * long oneHourFromNow = System.currentTimeMillis() / 1000 + 3600;
	 * BlockUserRequest request = BlockUserRequest.builder().user("user123")
	 * 		.expireAt(oneHourFromNow).build();
	 *
	 * BlockUserResponse response = client.blockUser(request);
	 * }</pre>
	 * @param request the block user request containing user ID and optional expiration
	 * @return the block user response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#block_user">Block User
	 * Documentation</a>
	 */
	@RequestLine("POST /block_user")
	BlockUserResponse blockUser(BlockUserRequest request);

	/**
	 * Block a user.
	 *
	 * <p>
	 * Blocks a user from connecting to Centrifugo. Once blocked, the user will be
	 * immediately disconnected if currently connected and will not be able to establish
	 * new connections until unblocked.
	 * </p>
	 *
	 * <p>
	 * Blocking features:
	 * </p>
	 * <ul>
	 * <li>Immediate disconnection of existing user connections</li>
	 * <li>Prevention of new connection attempts</li>
	 * <li>Optional expiration time for temporary blocks</li>
	 * <li>Permanent blocking when no expiration is set</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Implementing user bans and suspensions</li>
	 * <li>Temporary timeouts for misbehavior</li>
	 * <li>Administrative user management</li>
	 * <li>Abuse prevention and moderation</li>
	 * </ul>
	 *
	 * <p>
	 * Example - permanent block:
	 * </p>
	 *
	 * <pre>{@code
	 * BlockUserRequest request = BlockUserRequest.builder().user("user123").build();
	 *
	 * BlockUserResponse response = client.blockUser(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - temporary block (1 hour):
	 * </p>
	 *
	 * <pre>{@code
	 * long oneHourFromNow = System.currentTimeMillis() / 1000 + 3600;
	 * BlockUserRequest request = BlockUserRequest.builder().user("user123")
	 * 		.expireAt(oneHourFromNow).build();
	 *
	 * BlockUserResponse response = client.blockUser(request);
	 * }</pre>
	 * @param fn the function to configure the block user request
	 * @return the block user response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#block_user">Block User
	 * Documentation</a>
	 */
	default BlockUserResponse blockUser(Function<BlockUserRequest.Builder, BlockUserRequest.Builder> fn) {
		return this.blockUser(fn.apply(BlockUserRequest.builder()).build());
	}

	/**
	 * Unblock a user.
	 *
	 * <p>
	 * Removes a block from a user, allowing them to connect to Centrifugo again. This
	 * immediately restores the user's ability to establish connections.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Ending user suspensions early</li>
	 * <li>Correcting mistaken blocks</li>
	 * <li>Administrative user restoration</li>
	 * <li>Appeal process resolution</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * UnblockUserRequest request = UnblockUserRequest.builder().user("user123").build();
	 *
	 * UnblockUserResponse response = client.unblockUser(request);
	 * }</pre>
	 * @param request the unblock user request containing the user ID to unblock
	 * @return the unblock user response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#unblock_user">Unblock
	 * User Documentation</a>
	 */
	@RequestLine("POST /unblock_user")
	UnblockUserResponse unblockUser(UnblockUserRequest request);

	/**
	 * Unblock a user.
	 *
	 * <p>
	 * Removes a block from a user, allowing them to connect to Centrifugo again. This
	 * immediately restores the user's ability to establish connections.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Ending user suspensions early</li>
	 * <li>Correcting mistaken blocks</li>
	 * <li>Administrative user restoration</li>
	 * <li>Appeal process resolution</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * UnblockUserRequest request = UnblockUserRequest.builder().user("user123").build();
	 *
	 * UnblockUserResponse response = client.unblockUser(request);
	 * }</pre>
	 * @param user the user to unblock
	 * @return the unblock user response
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#unblock_user">Unblock
	 * User Documentation</a>
	 */
	default UnblockUserResponse unblockUser(String user) {
		return this.unblockUser(UnblockUserRequest.of(user));
	}

}
