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
 * Request for refreshing a user connection.
 */
public record RefreshRequest(@JsonProperty("user") String user, @JsonProperty("client") String client,
		@JsonProperty("session") String session, @JsonProperty("expired") Boolean expired,
		@JsonProperty("expire_at") Long expireAt, @JsonProperty("info") Object info) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String user;

		private String client;

		private String session;

		private Boolean expired;

		private Long expireAt;

		private Object info;

		public Builder user(String user) {
			this.user = user;
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

		public Builder expired(Boolean expired) {
			this.expired = expired;
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

		public RefreshRequest build() {
			return new RefreshRequest(this.user, this.client, this.session, this.expired, this.expireAt, this.info);
		}

	}
}
