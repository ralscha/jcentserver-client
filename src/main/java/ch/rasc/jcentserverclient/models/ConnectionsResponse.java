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
 * Response from a connections operation.
 */
public record ConnectionsResponse(@JsonProperty("error") Error error,
		@JsonProperty("result") ConnectionsResult result) {

	/**
	 * Check if the response has an error.
	 * @return true if there is an error, false otherwise
	 */
	public boolean hasError() {
		return this.error != null;
	}

	/**
	 * Get the error if present.
	 * @return the error or null if no error
	 */
	@Override
	public Error error() {
		return this.error;
	}

	/**
	 * Get the result if successful.
	 * @return the result or null if there was an error
	 */
	@Override
	public ConnectionsResult result() {
		return this.result;
	}
}
