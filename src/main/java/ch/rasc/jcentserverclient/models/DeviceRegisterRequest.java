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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public record DeviceRegisterRequest(@JsonProperty("id") String id, @JsonProperty("provider") String provider,
		@JsonProperty("token") String token, @JsonProperty("platform") String platform,
		@JsonProperty("user") String user, @JsonProperty("meta") Map<String, String> meta,
		@JsonProperty("topics") List<String> topics, @JsonProperty("timezone") String timezone,
		@JsonProperty("locale") String locale) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String id;

		private String provider;

		private String token;

		private String platform;

		private String user;

		private Map<String, String> meta;

		private List<String> topics;

		private String timezone;

		private String locale;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder provider(String provider) {
			this.provider = provider;
			return this;
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public Builder platform(String platform) {
			this.platform = platform;
			return this;
		}

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder meta(Map<String, String> meta) {
			this.meta = meta;
			return this;
		}

		public Builder topics(List<String> topics) {
			this.topics = List.copyOf(topics);
			return this;
		}

		public Builder topics(String... topics) {
			this.topics = List.of(topics);
			return this;
		}

		public Builder timezone(String timezone) {
			this.timezone = timezone;
			return this;
		}

		public Builder locale(String locale) {
			this.locale = locale;
			return this;
		}

		public DeviceRegisterRequest build() {
			return new DeviceRegisterRequest(this.id, this.provider, this.token, this.platform, this.user, this.meta,
					this.topics, this.timezone, this.locale);
		}

	}
}
