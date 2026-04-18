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

/**
 * Request to revoke a token.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record RevokeTokenRequest(@JsonProperty("uid") String uid,
		@JsonProperty("expire_at") Long expireAt) {

	public static Builder builder() {
		return new Builder();
	}

	public static RevokeTokenRequest of(String uid) {
		if (uid == null || uid.trim().isEmpty()) {
			throw new IllegalArgumentException("'uid' is required and cannot be null or empty");
		}
		return new RevokeTokenRequest(uid, null);
	}

	public static class Builder {

		private String uid;

		private Long expireAt;

		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}

		public Builder expireAt(Long expireAt) {
			this.expireAt = expireAt;
			return this;
		}

		public RevokeTokenRequest build() {
			if (this.uid == null || this.uid.trim().isEmpty()) {
				throw new IllegalArgumentException("'uid' is required and cannot be null or empty");
			}
			return new RevokeTokenRequest(this.uid, this.expireAt);
		}

	}
}
