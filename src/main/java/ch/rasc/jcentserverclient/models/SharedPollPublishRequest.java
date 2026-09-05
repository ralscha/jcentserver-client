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
public record SharedPollPublishRequest(@JsonProperty("channel") String channel, @JsonProperty("key") String key,
		@JsonProperty("data") Object data, @JsonProperty("b64data") String b64data,
		@JsonProperty("version") Long version, @JsonProperty("epoch") String epoch) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		private String key;

		private Object data;

		private String b64data;

		private Long version;

		private String epoch;

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

		public Builder version(Long version) {
			this.version = version;
			return this;
		}

		public Builder epoch(String epoch) {
			this.epoch = epoch;
			return this;
		}

		public SharedPollPublishRequest build() {
			if (this.channel == null || this.channel.isBlank()) {
				throw new IllegalArgumentException("'channel' is required and cannot be null or empty");
			}
			if (this.key == null || this.key.isBlank()) {
				throw new IllegalArgumentException("'key' is required and cannot be null or empty");
			}
			if (this.data == null && (this.b64data == null || this.b64data.isBlank())) {
				throw new IllegalArgumentException("either 'data' or 'b64data' is required");
			}
			return new SharedPollPublishRequest(this.channel, this.key, this.data, this.b64data, this.version,
					this.epoch);
		}

	}
}
