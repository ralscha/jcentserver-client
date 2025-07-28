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
package ch.rasc.jcentserverclient.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Request to delete user status.
 */
@JsonInclude(Include.NON_EMPTY)
public record DeleteUserStatusRequest(@JsonProperty("users") List<String> users) {

	public static DeleteUserStatusRequest of(List<String> users) {
		if (users == null || users.isEmpty()) {
			throw new IllegalArgumentException("'users' is required and cannot be null or empty");
		}
		return new DeleteUserStatusRequest(List.copyOf(users));
	}

	public static DeleteUserStatusRequest of(String... users) {
		if (users == null || users.length == 0) {
			throw new IllegalArgumentException("'users' is required and cannot be null or empty");
		}
		return new DeleteUserStatusRequest(List.of(users));
	}
}
