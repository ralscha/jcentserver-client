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
 * Connection token information.
 */
public record ConnectionTokenInfo(@JsonProperty("uid") String uid, @JsonProperty("issued_at") Long issuedAt) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String uid;

		private Long issuedAt;

		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}

		public Builder issuedAt(Long issuedAt) {
			this.issuedAt = issuedAt;
			return this;
		}

		public ConnectionTokenInfo build() {
			return new ConnectionTokenInfo(this.uid, this.issuedAt);
		}

	}
}
