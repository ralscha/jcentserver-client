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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to get user status.
 */
public record GetUserStatusRequest(@JsonProperty("users") List<String> users) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private List<String> users;

		public Builder users(List<String> users) {
			this.users = users;
			return this;
		}

		public GetUserStatusRequest build() {
			return new GetUserStatusRequest(this.users);
		}

	}
}
