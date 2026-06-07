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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public record UpdateUserStatusRequest(@JsonProperty("users") List<String> users, @JsonProperty("state") String state) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private List<String> users;

		private String state;

		public Builder users(List<String> users) {
			this.users = List.copyOf(users);
			return this;
		}

		public Builder users(String... users) {
			this.users = List.of(users);
			return this;
		}

		public Builder state(String state) {
			this.state = state;
			return this;
		}

		public UpdateUserStatusRequest build() {
			return new UpdateUserStatusRequest(this.users, this.state);
		}

	}
}
