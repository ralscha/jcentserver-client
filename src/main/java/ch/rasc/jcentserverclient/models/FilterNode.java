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
@SuppressWarnings({ "hiding" })
public record FilterNode(@JsonProperty("op") String op, @JsonProperty("key") String key,
		@JsonProperty("cmp") String cmp, @JsonProperty("val") String val, @JsonProperty("vals") List<String> vals,
		@JsonProperty("nodes") List<FilterNode> nodes) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String op;

		private String key;

		private String cmp;

		private String val;

		private List<String> vals;

		private List<FilterNode> nodes;

		public Builder op(String op) {
			this.op = op;
			return this;
		}

		public Builder key(String key) {
			this.key = key;
			return this;
		}

		public Builder cmp(String cmp) {
			this.cmp = cmp;
			return this;
		}

		public Builder val(String val) {
			this.val = val;
			return this;
		}

		public Builder vals(List<String> vals) {
			this.vals = List.copyOf(vals);
			return this;
		}

		public Builder vals(String... vals) {
			this.vals = List.of(vals);
			return this;
		}

		public Builder nodes(List<FilterNode> nodes) {
			this.nodes = List.copyOf(nodes);
			return this;
		}

		public Builder nodes(FilterNode... nodes) {
			this.nodes = List.of(nodes);
			return this;
		}

		public FilterNode build() {
			return new FilterNode(this.op, this.key, this.cmp, this.val, this.vals, this.nodes);
		}

	}
}
