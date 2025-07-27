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
 * Publication object.
 */
public record Publication(@JsonProperty("data") Object data, @JsonProperty("info") ClientInfo info,
		@JsonProperty("offset") Long offset, @JsonProperty("tags") Map<String, String> tags) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Object data;

		private ClientInfo info;

		private Long offset;

		private Map<String, String> tags;

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public Builder info(ClientInfo info) {
			this.info = info;
			return this;
		}

		public Builder offset(Long offset) {
			this.offset = offset;
			return this;
		}

		public Builder tags(Map<String, String> tags) {
			this.tags = tags;
			return this;
		}

		public Publication build() {
			return new Publication(this.data, this.info, this.offset, this.tags);
		}

	}
}
