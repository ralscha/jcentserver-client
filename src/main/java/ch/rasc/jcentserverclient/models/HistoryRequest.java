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
 * Request for getting channel history.
 */
public record HistoryRequest(@JsonProperty("channel") String channel, @JsonProperty("limit") Integer limit,
		@JsonProperty("since") StreamPosition since, @JsonProperty("reverse") Boolean reverse) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		private Integer limit;

		private StreamPosition since;

		private Boolean reverse;

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder limit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Builder since(StreamPosition since) {
			this.since = since;
			return this;
		}

		public Builder reverse(Boolean reverse) {
			this.reverse = reverse;
			return this;
		}

		public HistoryRequest build() {
			return new HistoryRequest(this.channel, this.limit, this.since, this.reverse);
		}

	}
}
