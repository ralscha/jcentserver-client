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
package ch.rasc.jcentserverclient;

/**
 * Exception thrown when Centrifugo API returns an error.
 */
public class ApiException extends RuntimeException {

	private final ApiError error;

	private final String responseBody;

	private final int statusCode;

	public ApiException(ApiError error, String responseBody, int statusCode) {
		super(formatMessage(error));
		this.error = error;
		this.responseBody = responseBody;
		this.statusCode = statusCode;
	}

	private static String formatMessage(ApiError error) {
		if (error != null && error.getMessage() != null) {
			return String.format("Centrifugo API error (code %d): %s", error.getCode(), error.getMessage());
		}
		return "Centrifugo API error";
	}

	public ApiError getError() {
		return this.error;
	}

	public String getResponseBody() {
		return this.responseBody;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public Integer getErrorCode() {
		return this.error != null ? this.error.getCode() : null;
	}

	public String getErrorMessage() {
		return this.error != null ? this.error.getMessage() : null;
	}

}
