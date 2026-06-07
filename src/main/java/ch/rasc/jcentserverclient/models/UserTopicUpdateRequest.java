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
public record UserTopicUpdateRequest(@JsonProperty("user") String user, @JsonProperty("op") String op,
		@JsonProperty("topics") List<String> topics) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String user;

		private String op;

		private List<String> topics;

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder op(String op) {
			this.op = op;
			return this;
		}

		public Builder topics(List<String> topics) {
			this.topics = List.copyOf(topics);
			return this;
		}

		public Builder topics(String... topics) {
			this.topics = List.of(topics);
			return this;
		}

		public UserTopicUpdateRequest build() {
			return new UserTopicUpdateRequest(this.user, this.op, this.topics);
		}

	}
}
