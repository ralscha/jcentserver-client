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
 * Disconnect object for specifying custom disconnect information.
 */
@SuppressWarnings({ "hiding" })
public record Disconnect(@JsonProperty("code") Long code, @JsonProperty("reason") String reason) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Long code;

		private String reason;

		public Builder code(Long code) {
			this.code = code;
			return this;
		}

		public Builder reason(String reason) {
			this.reason = reason;
			return this;
		}

		public Disconnect build() {
			if (this.code == null) {
				throw new IllegalArgumentException("'code' is required and cannot be null");
			}
			if (this.reason == null || this.reason.trim().isEmpty()) {
				throw new IllegalArgumentException("'reason' is required and cannot be null or empty");
			}
			return new Disconnect(this.code, this.reason);
		}

	}
}
