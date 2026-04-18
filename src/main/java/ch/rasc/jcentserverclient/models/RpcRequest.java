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
 * Request for calling a custom RPC method.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record RpcRequest(@JsonProperty("method") String method, @JsonProperty("params") Object params) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String method;

		private Object params;

		public Builder method(String method) {
			this.method = method;
			return this;
		}

		public Builder params(Object params) {
			this.params = params;
			return this;
		}

		public RpcRequest build() {
			if (this.method == null || this.method.trim().isEmpty()) {
				throw new IllegalArgumentException("'method' is required and cannot be null or empty");
			}
			return new RpcRequest(this.method, this.params);
		}

	}

}
