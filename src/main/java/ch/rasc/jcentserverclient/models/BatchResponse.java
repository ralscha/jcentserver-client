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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from a batch operation.
 */
public record BatchResponse(@JsonProperty("replies") List<Reply> replies) {

	/**
	 * Check if any reply in the response has an error.
	 * @return true if there is an error in any reply, false otherwise
	 */
	public boolean hasError() {
		return this.replies != null && this.replies.stream().anyMatch(Reply::hasError);
	}

	/**
	 * Get the first reply error if present.
	 * @return the first reply error or null if no reply has an error
	 */
	public Error error() {
		if (this.replies == null) {
			return null;
		}
		return this.replies.stream().filter(Reply::hasError).map(Reply::error).findFirst().orElse(null);
	}

	/**
	 * Get the first reply error if present.
	 * @return the first reply error or null if no reply has an error
	 */
	public Error getError() {
		return this.error();
	}

	/**
	 * Get the replies from the batch response.
	 * @return individual replies for each command
	 */
	public List<Reply> getReplies() {
		return this.replies;
	}

	/**
	 * Get the result wrapper for compatibility with older client versions.
	 * @return a result wrapper containing the replies
	 */
	public BatchResult result() {
		return new BatchResult(this.replies);
	}

	/**
	 * Get the result wrapper for compatibility with older client versions.
	 * @return a result wrapper containing the replies
	 */
	public BatchResult getResult() {
		return this.result();
	}
}
