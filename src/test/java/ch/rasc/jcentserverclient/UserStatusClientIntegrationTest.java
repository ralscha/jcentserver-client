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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.GetUserStatusRequest;
import ch.rasc.jcentserverclient.models.GetUserStatusResponse;
import ch.rasc.jcentserverclient.models.UpdateUserStatusRequest;
import ch.rasc.jcentserverclient.models.UpdateUserStatusResponse;
import ch.rasc.jcentserverclient.models.UserStatus;

/**
 * Integration tests for UserStatus client operations. Tests user status management
 * functionality.
 */
@Disabled
@DisplayName("User Status Client Integration Tests")
class UserStatusClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should update user status")
	void shouldUpdateUserStatus() {
		// Given
		String user = "test-user-status-" + System.currentTimeMillis();
		UserStatus userStatus = UserStatus.builder().user(user).build();

		UpdateUserStatusRequest request = UpdateUserStatusRequest.of(userStatus);

		// When
		UpdateUserStatusResponse response = this.client.userStatus().updateUserStatus(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should handle get status for non-existent user")
	void shouldHandleGetStatusForNonExistentUser() {
		// Given
		String nonExistentUser = "non-existent-user-" + System.currentTimeMillis();

		GetUserStatusRequest request = GetUserStatusRequest.of(nonExistentUser);

		// When
		GetUserStatusResponse response = this.client.userStatus().getUserStatus(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.error()).isNull();
	}

}
