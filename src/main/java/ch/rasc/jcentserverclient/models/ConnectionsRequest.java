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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Request for getting connections information.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record ConnectionsRequest(@JsonProperty("user") String user, @JsonProperty("expression") String expression) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String user;

		private String expression;

		public Builder user(String user) {
			this.user = user;
			return this;
		}

		public Builder expression(String expression) {
			this.expression = expression;
			return this;
		}

		public ConnectionsRequest build() {
			return new ConnectionsRequest(this.user, this.expression);
		}

	}
}
