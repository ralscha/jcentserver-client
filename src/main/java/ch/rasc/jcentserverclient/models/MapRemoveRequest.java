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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public record MapRemoveRequest(@JsonProperty("channel") String channel, @JsonProperty("key") String key,
		@JsonProperty("idempotency_key") String idempotencyKey) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		private String key;

		private String idempotencyKey;

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder key(String key) {
			this.key = key;
			return this;
		}

		public Builder idempotencyKey(String idempotencyKey) {
			this.idempotencyKey = idempotencyKey;
			return this;
		}

		public MapRemoveRequest build() {
			requireText(this.channel, "channel");
			requireText(this.key, "key");
			return new MapRemoveRequest(this.channel, this.key, this.idempotencyKey);
		}

	}

	private static void requireText(String value, String name) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("'" + name + "' is required and cannot be null or empty");
		}
	}
}
