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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Request for unsubscribing a user from a channel.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record UnsubscribeRequest(@JsonProperty("user") String user, @JsonProperty("channel") String channel,
		@JsonProperty("client") String client, @JsonProperty("session") String session) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String user;

		private String channel;

		private String client;

		private String session;

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder client(String client) {
			this.client = client;
			return this;
		}

		public Builder session(String session) {
			this.session = session;
			return this;
		}

		public UnsubscribeRequest build() {
			if (this.user == null || this.user.trim().isEmpty()) {
				throw new IllegalArgumentException("'user' is required and cannot be null or empty");
			}
			if (this.channel == null || this.channel.trim().isEmpty()) {
				throw new IllegalArgumentException("'channel' is required and cannot be null or empty");
			}
			return new UnsubscribeRequest(this.user, this.channel, this.client, this.session);
		}

	}
}
