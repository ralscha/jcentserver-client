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
 * Connection information.
 */
@SuppressWarnings({ "hiding" })
public record ConnectionInfo(@JsonProperty("app_name") String appName, @JsonProperty("app_version") String appVersion,
		@JsonProperty("transport") String transport, @JsonProperty("protocol") String protocol,
		@JsonProperty("user") String user, @JsonProperty("state") ConnectionState state) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String appName;

		private String appVersion;

		private String transport;

		private String protocol;

		private String user;

		private ConnectionState state;

		public Builder appName(String appName) {
			this.appName = appName;
			return this;
		}

		public Builder appVersion(String appVersion) {
			this.appVersion = appVersion;
			return this;
		}

		public Builder transport(String transport) {
			this.transport = transport;
			return this;
		}

		public Builder protocol(String protocol) {
			this.protocol = protocol;
			return this;
		}

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder state(ConnectionState state) {
			this.state = state;
			return this;
		}

		public ConnectionInfo build() {
			return new ConnectionInfo(this.appName, this.appVersion, this.transport, this.protocol, this.user,
					this.state);
		}

	}
}
