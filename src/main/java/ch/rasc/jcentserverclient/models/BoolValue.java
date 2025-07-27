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
 * BoolValue is a wrapper for boolean values in override objects.
 */
public record BoolValue(@JsonProperty("value") Boolean value) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Boolean value;

		public Builder value(Boolean value) {
			this.value = value;
			return this;
		}

		public BoolValue build() {
			return new BoolValue(this.value);
		}

	}
}
