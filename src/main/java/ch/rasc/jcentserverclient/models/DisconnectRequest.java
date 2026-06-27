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

/**
 * Request for disconnecting a user.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record DisconnectRequest(@JsonProperty("user") String user, @JsonProperty("client") String client,
		@JsonProperty("session") String session, @JsonProperty("whitelist") List<String> whitelist,
		@JsonProperty("disconnect") Disconnect disconnect, @JsonProperty("label_filter") FilterNode labelFilter,
		@JsonProperty("all_users") Boolean allUsers) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String user;

		private String client;

		private String session;

		private List<String> whitelist;

		private Disconnect disconnect;

		private FilterNode labelFilter;

		private Boolean allUsers;

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

		public Builder whitelist(List<String> whitelist) {
			this.whitelist = List.copyOf(whitelist);
			return this;
		}

		public Builder whitelist(String... whitelist) {
			this.whitelist = List.of(whitelist);
			return this;
		}

		public Builder disconnect(Disconnect disconnect) {
			this.disconnect = disconnect;
			return this;
		}

		public Builder labelFilter(FilterNode labelFilter) {
			this.labelFilter = labelFilter;
			return this;
		}

		public Builder allUsers(Boolean allUsers) {
			this.allUsers = allUsers;
			return this;
		}

		public DisconnectRequest build() {
			if ((this.user == null || this.user.trim().isEmpty()) && !Boolean.TRUE.equals(this.allUsers)) {
				throw new IllegalArgumentException("'user' is required and cannot be null or empty");
			}
			return new DisconnectRequest(this.user, this.client, this.session, this.whitelist, this.disconnect,
					this.labelFilter, this.allUsers);
		}

	}
}
