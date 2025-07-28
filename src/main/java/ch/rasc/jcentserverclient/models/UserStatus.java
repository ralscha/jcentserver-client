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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User status information.
 */
@SuppressWarnings({ "hiding" })
public record UserStatus(@JsonProperty("user") String user, @JsonProperty("active") Integer active,
		@JsonProperty("online") Integer online, @JsonProperty("state") String state) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String user;

		private Integer active;

		private Integer online;

		private String state;

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder active(Integer active) {
			this.active = active;
			return this;
		}

		public Builder online(Integer online) {
			this.online = online;
			return this;
		}

		public Builder state(String state) {
			this.state = state;
			return this;
		}

		public UserStatus build() {
			if (this.user == null || this.user.trim().isEmpty()) {
				throw new IllegalArgumentException("'user' is required and cannot be null or empty");
			}
			return new UserStatus(this.user, this.active, this.online, this.state);
		}

	}
}
