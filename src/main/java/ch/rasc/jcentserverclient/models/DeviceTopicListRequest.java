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

@JsonInclude(Include.NON_EMPTY)
public record DeviceTopicListRequest(@JsonProperty("filter") DeviceTopicFilter filter,
		@JsonProperty("include_total_count") Boolean includeTotalCount,
		@JsonProperty("include_device") Boolean includeDevice, @JsonProperty("cursor") String cursor,
		@JsonProperty("limit") Integer limit) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private DeviceTopicFilter filter;

		private Boolean includeTotalCount;

		private Boolean includeDevice;

		private String cursor;

		private Integer limit;

		public Builder filter(DeviceTopicFilter filter) {
			this.filter = filter;
			return this;
		}

		public Builder includeTotalCount(Boolean includeTotalCount) {
			this.includeTotalCount = includeTotalCount;
			return this;
		}

		public Builder includeDevice(Boolean includeDevice) {
			this.includeDevice = includeDevice;
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

		public DeviceTopicListRequest build() {
			return new DeviceTopicListRequest(this.filter, this.includeTotalCount, this.includeDevice, this.cursor,
					this.limit);
		}

	}
}
