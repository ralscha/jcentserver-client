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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public record DeviceUpdateRequest(@JsonProperty("ids") List<String> ids, @JsonProperty("users") List<String> users,
		@JsonProperty("user_update") DeviceUserUpdate userUpdate,
		@JsonProperty("meta_update") DeviceMetaUpdate metaUpdate,
		@JsonProperty("topics_update") DeviceTopicsUpdate topicsUpdate,
		@JsonProperty("timezone_update") DeviceTimezoneUpdate timezoneUpdate,
		@JsonProperty("locale_update") DeviceLocaleUpdate localeUpdate) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private List<String> ids;

		private List<String> users;

		private DeviceUserUpdate userUpdate;

		private DeviceMetaUpdate metaUpdate;

		private DeviceTopicsUpdate topicsUpdate;

		private DeviceTimezoneUpdate timezoneUpdate;

		private DeviceLocaleUpdate localeUpdate;

		public Builder ids(List<String> ids) {
			this.ids = List.copyOf(ids);
			return this;
		}

		public Builder ids(String... ids) {
			this.ids = List.of(ids);
			return this;
		}

		public Builder users(List<String> users) {
			this.users = List.copyOf(users);
			return this;
		}

		public Builder users(String... users) {
			this.users = List.of(users);
			return this;
		}

		public Builder userUpdate(DeviceUserUpdate userUpdate) {
			this.userUpdate = userUpdate;
			return this;
		}

		public Builder metaUpdate(DeviceMetaUpdate metaUpdate) {
			this.metaUpdate = metaUpdate;
			return this;
		}

		public Builder topicsUpdate(DeviceTopicsUpdate topicsUpdate) {
			this.topicsUpdate = topicsUpdate;
			return this;
		}

		public Builder timezoneUpdate(DeviceTimezoneUpdate timezoneUpdate) {
			this.timezoneUpdate = timezoneUpdate;
			return this;
		}

		public Builder localeUpdate(DeviceLocaleUpdate localeUpdate) {
			this.localeUpdate = localeUpdate;
			return this;
		}

		public DeviceUpdateRequest build() {
			return new DeviceUpdateRequest(this.ids, this.users, this.userUpdate, this.metaUpdate, this.topicsUpdate,
					this.timezoneUpdate, this.localeUpdate);
		}

	}
}
