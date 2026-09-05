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
public record MapReadStateRequest(@JsonProperty("channel") String channel, @JsonProperty("cursor") String cursor,
		@JsonProperty("limit") Integer limit, @JsonProperty("key") String key, @JsonProperty("asc") Boolean asc,
		@JsonProperty("revision_offset") Long revisionOffset, @JsonProperty("revision_epoch") String revisionEpoch) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String channel;

		private String cursor;

		private Integer limit;

		private String key;

		private Boolean asc;

		private Long revisionOffset;

		private String revisionEpoch;

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder cursor(String cursor) {
			this.cursor = cursor;
			return this;
		}

		public Builder limit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Builder key(String key) {
			this.key = key;
			return this;
		}

		public Builder asc(Boolean asc) {
			this.asc = asc;
			return this;
		}

		public Builder revisionOffset(Long revisionOffset) {
			this.revisionOffset = revisionOffset;
			return this;
		}

		public Builder revisionEpoch(String revisionEpoch) {
			this.revisionEpoch = revisionEpoch;
			return this;
		}

		public MapReadStateRequest build() {
			if (this.channel == null || this.channel.isBlank()) {
				throw new IllegalArgumentException("'channel' is required and cannot be null or empty");
			}
			return new MapReadStateRequest(this.channel, this.cursor, this.limit, this.key, this.asc,
					this.revisionOffset, this.revisionEpoch);
		}

	}
}
