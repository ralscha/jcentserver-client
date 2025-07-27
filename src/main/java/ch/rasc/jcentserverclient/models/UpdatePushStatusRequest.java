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
 * Request to update push notification status.
 */
public record UpdatePushStatusRequest(@JsonProperty("uid") String uid, @JsonProperty("device_id") String deviceId,
		@JsonProperty("status") String status) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String uid;

		private String deviceId;

		private String status;

		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}

		public Builder deviceId(String deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public UpdatePushStatusRequest build() {
			return new UpdatePushStatusRequest(this.uid, this.deviceId, this.status);
		}

	}
}
