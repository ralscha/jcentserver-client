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

import java.util.List;
import java.util.function.Function;

import ch.rasc.jcentserverclient.models.DeleteUserStatusRequest;
import ch.rasc.jcentserverclient.models.DeleteUserStatusResponse;
import ch.rasc.jcentserverclient.models.GetUserStatusRequest;
import ch.rasc.jcentserverclient.models.GetUserStatusResponse;
import ch.rasc.jcentserverclient.models.UpdateUserStatusRequest;
import ch.rasc.jcentserverclient.models.UpdateUserStatusResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo user status API client for managing user status information.
 *
 * <p>
 * This client provides access to Centrifugo's user status functionality, allowing you to
 * track and manage user online/offline status and custom state information across your
 * application.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Set and update user status with custom state</li>
 * <li>Retrieve current status for multiple users</li>
 * <li>Delete user status information</li>
 * <li>Track online/offline and last activity times</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#update_user_status">User
 * Status API Documentation</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface UserStatusClient {

	/**
	 * Update user status.
	 *
	 * <p>
	 * Sets or updates status information for one or more users. This allows you to track
	 * custom user states like "online", "away", "busy", etc., along with automatic
	 * online/offline tracking.
	 * </p>
	 *
	 * <p>
	 * Status features:
	 * </p>
	 * <ul>
	 * <li>Custom state strings (e.g., "online", "away", "busy")</li>
	 * <li>Automatic last activity timestamp tracking</li>
	 * <li>Bulk updates for multiple users</li>
	 * <li>Integration with presence system</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Implementing user status indicators</li>
	 * <li>Building activity-based features</li>
	 * <li>User availability systems</li>
	 * <li>Social presence features</li>
	 * </ul>
	 *
	 * <p>
	 * Example - single user:
	 * </p>
	 *
	 * <pre>{@code
	 * UpdateUserStatusRequest request = UpdateUserStatusRequest.builder()
	 * 		.users(List.of("user123")).state("online").build();
	 *
	 * UpdateUserStatusResponse response = client.updateUserStatus(request);
	 * }</pre>
	 *
	 * <p>
	 * Example - multiple users:
	 * </p>
	 *
	 * <pre>{@code
	 * UpdateUserStatusRequest request = UpdateUserStatusRequest.builder()
	 * 		.users(List.of("user1", "user2", "user3")).state("away").build();
	 *
	 * UpdateUserStatusResponse response = client.updateUserStatus(request);
	 * }</pre>
	 * @param request the update user status request containing user IDs and state
	 * @return the update user status response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#update_user_status">Update User
	 * Status Documentation</a>
	 */
	@RequestLine("POST /update_user_status")
	UpdateUserStatusResponse updateUserStatus(UpdateUserStatusRequest request);

	/**
	 * Get user status.
	 *
	 * <p>
	 * Retrieves current status information for one or more users, including their custom
	 * state, online status, and activity timestamps.
	 * </p>
	 *
	 * <p>
	 * Returned information includes:
	 * </p>
	 * <ul>
	 * <li>User ID</li>
	 * <li>Custom state string</li>
	 * <li>Online timestamp (when user came online)</li>
	 * <li>Active timestamp (last activity time)</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Displaying user status in UI</li>
	 * <li>Building user lists with status</li>
	 * <li>Checking user availability</li>
	 * <li>Analytics and reporting</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * GetUserStatusRequest request = GetUserStatusRequest.builder()
	 * 		.users(List.of("user123", "user456")).build();
	 *
	 * GetUserStatusResponse response = client.getUserStatus(request);
	 * for (UserStatus status : response.getResult().getStatuses()) {
	 * 	System.out.println("User: " + status.getUser() + ", State: "
	 * 			+ status.getState() + ", Online: " + (status.getOnline() > 0));
	 * }
	 * }</pre>
	 * @param request the get user status request containing user IDs to query
	 * @return the get user status response containing status information
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#get_user_status">Get
	 * User Status Documentation</a>
	 */
	@RequestLine("POST /get_user_status")
	GetUserStatusResponse getUserStatus(GetUserStatusRequest request);

	/**
	 * Get user status.
	 *
	 * <p>
	 * Retrieves current status information for one or more users, including their custom
	 * state, online status, and activity timestamps.
	 * </p>
	 *
	 * <p>
	 * Returned information includes:
	 * </p>
	 * <ul>
	 * <li>User ID</li>
	 * <li>Custom state string</li>
	 * <li>Online timestamp (when user came online)</li>
	 * <li>Active timestamp (last activity time)</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Displaying user status in UI</li>
	 * <li>Building user lists with status</li>
	 * <li>Checking user availability</li>
	 * <li>Analytics and reporting</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * GetUserStatusRequest request = GetUserStatusRequest.builder()
	 * 		.users(List.of("user123", "user456")).build();
	 *
	 * GetUserStatusResponse response = client.getUserStatus(request);
	 * for (UserStatus status : response.getResult().getStatuses()) {
	 * 	System.out.println("User: " + status.getUser() + ", State: "
	 * 			+ status.getState() + ", Online: " + (status.getOnline() > 0));
	 * }
	 * }</pre>
	 * @param users the users to get status for
	 * @return the get user status response containing status information
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#get_user_status">Get
	 * User Status Documentation</a>
	 */
	default GetUserStatusResponse getUserStatus(String... users) {
		return this.getUserStatus(GetUserStatusRequest.of(users));
	}

	/**
	 * Get user status.
	 *
	 * <p>
	 * Retrieves current status information for one or more users, including their custom
	 * state, online status, and activity timestamps.
	 * </p>
	 *
	 * <p>
	 * Returned information includes:
	 * </p>
	 * <ul>
	 * <li>User ID</li>
	 * <li>Custom state string</li>
	 * <li>Online timestamp (when user came online)</li>
	 * <li>Active timestamp (last activity time)</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Displaying user status in UI</li>
	 * <li>Building user lists with status</li>
	 * <li>Checking user availability</li>
	 * <li>Analytics and reporting</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * GetUserStatusRequest request = GetUserStatusRequest.builder()
	 * 		.users(List.of("user123", "user456")).build();
	 *
	 * GetUserStatusResponse response = client.getUserStatus(request);
	 * for (UserStatus status : response.getResult().getStatuses()) {
	 * 	System.out.println("User: " + status.getUser() + ", State: "
	 * 			+ status.getState() + ", Online: " + (status.getOnline() > 0));
	 * }
	 * }</pre>
	 * @param users the users to get status for
	 * @return the get user status response containing status information
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#get_user_status">Get
	 * User Status Documentation</a>
	 */
	default GetUserStatusResponse getUserStatus(List<String> users) {
		return this.getUserStatus(GetUserStatusRequest.of(users));
	}

	/**
	 * Delete user status.
	 *
	 * <p>
	 * Removes status information for one or more users. This clears their custom state
	 * and activity tracking data from the system.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>User account cleanup</li>
	 * <li>Privacy compliance (data deletion)</li>
	 * <li>Resetting user status</li>
	 * <li>Administrative operations</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * DeleteUserStatusRequest request = DeleteUserStatusRequest.builder()
	 * 		.users(List.of("user123", "user456")).build();
	 *
	 * DeleteUserStatusResponse response = client.deleteUserStatus(request);
	 * }</pre>
	 * @param request the delete user status request containing user IDs to delete
	 * @return the delete user status response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#delete_user_status">Delete User
	 * Status Documentation</a>
	 */
	@RequestLine("POST /delete_user_status")
	DeleteUserStatusResponse deleteUserStatus(DeleteUserStatusRequest request);

	/**
	 * Delete user status.
	 *
	 * <p>
	 * Removes status information for one or more users. This clears their custom state
	 * and activity tracking data from the system.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>User account cleanup</li>
	 * <li>Privacy compliance (data deletion)</li>
	 * <li>Resetting user status</li>
	 * <li>Administrative operations</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * DeleteUserStatusRequest request = DeleteUserStatusRequest.builder()
	 * 		.users(List.of("user123", "user456")).build();
	 *
	 * DeleteUserStatusResponse response = client.deleteUserStatus(request);
	 * }</pre>
	 * @param users the users to delete status for
	 * @return the delete user status response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#delete_user_status">Delete User
	 * Status Documentation</a>
	 */
	default DeleteUserStatusResponse deleteUserStatus(String... users) {
		return this.deleteUserStatus(DeleteUserStatusRequest.of(users));
	}

	/**
	 * Delete user status.
	 *
	 * <p>
	 * Removes status information for one or more users. This clears their custom state
	 * and activity tracking data from the system.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>User account cleanup</li>
	 * <li>Privacy compliance (data deletion)</li>
	 * <li>Resetting user status</li>
	 * <li>Administrative operations</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * DeleteUserStatusRequest request = DeleteUserStatusRequest.builder()
	 * 		.users(List.of("user123", "user456")).build();
	 *
	 * DeleteUserStatusResponse response = client.deleteUserStatus(request);
	 * }</pre>
	 * @param users the users to delete status for
	 * @return the delete user status response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#delete_user_status">Delete User
	 * Status Documentation</a>
	 */
	default DeleteUserStatusResponse deleteUserStatus(List<String> users) {
		return this.deleteUserStatus(DeleteUserStatusRequest.of(users));
	}

}
