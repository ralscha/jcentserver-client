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
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request for broadcasting data to multiple channels.
 */
public record BroadcastRequest(@JsonProperty("channels") List<String> channels, @JsonProperty("data") Object data,
		@JsonProperty("b64data") String b64data, @JsonProperty("skip_history") Boolean skipHistory,
		@JsonProperty("tags") Map<String, String> tags, @JsonProperty("idempotency_key") String idempotencyKey,
		@JsonProperty("delta") Boolean delta) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private List<String> channels;

		private Object data;

		private String b64data;

		private Boolean skipHistory;

		private Map<String, String> tags;

		private String idempotencyKey;

		private Boolean delta;

		public Builder channels(List<String> channels) {
			this.channels = channels;
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

		public Builder skipHistory(Boolean skipHistory) {
			this.skipHistory = skipHistory;
			return this;
		}

		public Builder tags(Map<String, String> tags) {
			this.tags = tags;
			return this;
		}

		public Builder idempotencyKey(String idempotencyKey) {
			this.idempotencyKey = idempotencyKey;
			return this;
		}

		public Builder delta(Boolean delta) {
			this.delta = delta;
			return this;
		}

		public BroadcastRequest build() {
			return new BroadcastRequest(this.channels, this.data, this.b64data, this.skipHistory, this.tags,
					this.idempotencyKey, this.delta);
		}

	}
}
