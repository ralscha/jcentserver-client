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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record MapPublishRequest(@JsonProperty("channel") String channel, @JsonProperty("key") String key,
		@JsonProperty("data") Object data, @JsonProperty("b64data") String b64data,
		@JsonProperty("stream_data") Object streamData, @JsonProperty("b64stream_data") String b64streamData,
		@JsonProperty("tags") Map<String, String> tags, @JsonProperty("idempotency_key") String idempotencyKey,
		@JsonProperty("delta") Boolean delta, @JsonProperty("version") Long version,
		@JsonProperty("version_epoch") String versionEpoch, @JsonProperty("score") Long score,
		@JsonProperty("key_mode") String keyMode) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		private String key;

		private Object data;

		private String b64data;

		private Object streamData;

		private String b64streamData;

		private Map<String, String> tags;

		private String idempotencyKey;

		private Boolean delta;

		private Long version;

		private String versionEpoch;

		private Long score;

		private String keyMode;

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder key(String key) {
			this.key = key;
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

		public Builder streamData(Object streamData) {
			this.streamData = streamData;
			return this;
		}

		public Builder b64streamData(String b64streamData) {
			this.b64streamData = b64streamData;
			return this;
		}

		public Builder tags(Map<String, String> tags) {
			this.tags = Map.copyOf(tags);
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

		public Builder version(Long version) {
			this.version = version;
			return this;
		}

		public Builder versionEpoch(String versionEpoch) {
			this.versionEpoch = versionEpoch;
			return this;
		}

		public Builder score(Long score) {
			this.score = score;
			return this;
		}

		public Builder keyMode(String keyMode) {
			this.keyMode = keyMode;
			return this;
		}

		public MapPublishRequest build() {
			if (this.channel == null || this.channel.trim().isEmpty()) {
				throw new IllegalArgumentException("'channel' is required and cannot be null or empty");
			}
			if (this.key == null || this.key.trim().isEmpty()) {
				throw new IllegalArgumentException("'key' is required and cannot be null or empty");
			}
			return new MapPublishRequest(this.channel, this.key, this.data, this.b64data, this.streamData,
					this.b64streamData, this.tags, this.idempotencyKey, this.delta, this.version, this.versionEpoch,
					this.score, this.keyMode);
		}

	}
}
