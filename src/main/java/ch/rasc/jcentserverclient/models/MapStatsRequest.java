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
public record MapStatsRequest(@JsonProperty("channel") String channel) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public MapStatsRequest build() {
			if (this.channel == null || this.channel.isBlank()) {
				throw new IllegalArgumentException("'channel' is required and cannot be null or empty");
			}
			return new MapStatsRequest(this.channel);
		}

	}
}
