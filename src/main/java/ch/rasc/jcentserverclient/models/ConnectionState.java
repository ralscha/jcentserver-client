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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Connection state information.
 */
public record ConnectionState(@JsonProperty("channels") Map<String, ChannelContext> channels,
		@JsonProperty("connection_token") ConnectionTokenInfo connectionToken,
		@JsonProperty("subscription_tokens") Map<String, SubscriptionTokenInfo> subscriptionTokens,
		@JsonProperty("meta") Object meta) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Map<String, ChannelContext> channels;

		private ConnectionTokenInfo connectionToken;

		private Map<String, SubscriptionTokenInfo> subscriptionTokens;

		private Object meta;

		public Builder channels(Map<String, ChannelContext> channels) {
			this.channels = channels;
			return this;
		}

		public Builder connectionToken(ConnectionTokenInfo connectionToken) {
			this.connectionToken = connectionToken;
			return this;
		}

		public Builder subscriptionTokens(Map<String, SubscriptionTokenInfo> subscriptionTokens) {
			this.subscriptionTokens = subscriptionTokens;
			return this;
		}

		public Builder meta(Object meta) {
			this.meta = meta;
			return this;
		}

		public ConnectionState build() {
			return new ConnectionState(this.channels, this.connectionToken, this.subscriptionTokens, this.meta);
		}

	}
}
