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
 * Request for subscribing a user to a channel.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record SubscribeRequest(@JsonProperty("channel") String channel, @JsonProperty("user") String user,
		@JsonProperty("expire_at") Long expireAt, @JsonProperty("info") Object info,
		@JsonProperty("b64info") String b64info, @JsonProperty("client") String client,
		@JsonProperty("data") Object data, @JsonProperty("b64data") String b64data,
		@JsonProperty("recover_since") StreamPosition recoverSince,
		@JsonProperty("override") SubscribeOptionOverride override, @JsonProperty("session") String session) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		private String user;

		private Long expireAt;

		private Object info;

		private String b64info;

		private String client;

		private Object data;

		private String b64data;

		private StreamPosition recoverSince;

		private SubscribeOptionOverride override;

		private String session;

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder expireAt(Long expireAt) {
			this.expireAt = expireAt;
			return this;
		}

		public Builder info(Object info) {
			this.info = info;
			return this;
		}

		public Builder b64info(String b64info) {
			this.b64info = b64info;
			return this;
		}

		public Builder client(String client) {
			this.client = client;
			return this;
		}

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public Builder b64data(String b64data) {
			this.b64data = b64data;
			return this;
		}

		public Builder recoverSince(StreamPosition recoverSince) {
			this.recoverSince = recoverSince;
			return this;
		}

		public Builder override(SubscribeOptionOverride override) {
			this.override = override;
			return this;
		}

		public Builder session(String session) {
			this.session = session;
			return this;
		}

		public SubscribeRequest build() {
			if (this.user == null || this.user.trim().isEmpty()) {
				throw new IllegalArgumentException("'user' is required and cannot be null or empty");
			}
			if (this.channel == null || this.channel.trim().isEmpty()) {
				throw new IllegalArgumentException("'channel' is required and cannot be null or empty");
			}
			return new SubscribeRequest(this.channel, this.user, this.expireAt, this.info, this.b64info, this.client,
					this.data, this.b64data, this.recoverSince, this.override, this.session);
		}

	}
}
