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

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import feign.Client;
import feign.Logger;
import feign.Logger.Level;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.http2client.Http2Client;
import feign.slf4j.Slf4jLogger;

/**
 * Configuration class for Centrifugo Server API Client.
 *
 * <p>
 * This class holds all configuration settings needed to connect to and interact with a
 * Centrifugo server. It uses the builder pattern for easy and flexible configuration
 * setup.
 * </p>
 *
 * <p>
 * Key configuration options:
 * </p>
 * <ul>
 * <li>API key for authentication</li>
 * <li>Base URL of the Centrifugo server</li>
 * <li>HTTP client configuration</li>
 * <li>Retry policies and timeouts</li>
 * <li>Logging and error handling</li>
 * </ul>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>{@code
 * Configuration config = Configuration.builder().apiKey("your-api-key")
 * 		.baseUrl("http://localhost:8000/api").logLevel(Logger.Level.BASIC).build();
 * }</pre>
 *
 * @see CentrifugoServerApiClient#create(Configuration)
 * @since 1.0.0
 */
@SuppressWarnings({ "hiding", "unused" })
public class Configuration {

	private final String apiKey;

	private final String baseUrl;

	private final Client client;

	private final Retryer retryer;

	private final Request.Options feignOptions;

	private final Logger logger;

	private final ErrorDecoder errorDecoder;

	private final RequestInterceptor additionalRequestInterceptor;

	private final Level logLevel;

	private Configuration(Builder builder) {
		if (builder.apiKey == null) {
			throw new IllegalArgumentException("apiKey must not be null");
		}

		this.apiKey = builder.apiKey;

		this.baseUrl = Objects.requireNonNullElse(builder.baseUrl, "http://localhost:8000/api");
		this.client = Objects.requireNonNullElse(builder.client, new Http2Client());
		this.retryer = Objects.requireNonNullElse(builder.retryer,
				new Retryer.Default(TimeUnit.SECONDS.toMillis(2), TimeUnit.SECONDS.toMillis(2), 3));
		this.feignOptions = Objects.requireNonNullElse(builder.feignOptions,
				new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true));
		this.logger = Objects.requireNonNullElse(builder.logger, new Slf4jLogger());
		this.errorDecoder = Objects.requireNonNullElse(builder.errorDecoder, new ApiErrorDecoder());
		this.additionalRequestInterceptor = builder.additionalRequestInterceptor;
		this.logLevel = Objects.requireNonNullElse(builder.logLevel, Level.NONE);
	}

	/**
	 * Creates a new configuration builder.
	 * @return a new builder instance
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder class for creating Configuration instances.
	 *
	 * <p>
	 * Provides a fluent API for configuring all aspects of the Centrifugo client. Default
	 * values are provided for most settings to ensure the client works out-of-the-box
	 * with minimal configuration.
	 * </p>
	 */
	public static final class Builder {

		private String apiKey;

		private String baseUrl;

		private Client client;

		private Retryer retryer;

		private Request.Options feignOptions;

		private Logger logger;

		private ErrorDecoder errorDecoder;

		private RequestInterceptor additionalRequestInterceptor;

		private Level logLevel;

		private Builder() {
		}

		/**
		 * Sets the API key for authenticating with Centrifugo.
		 *
		 * <p>
		 * The API key is sent in the {@code X-API-Key} header with each request. This key
		 * must match the {@code http_api.key} configuration in your Centrifugo server
		 * configuration.
		 * </p>
		 * @param apiKey the API key for authentication
		 * @return this builder instance
		 * @see <a href=
		 * "https://centrifugal.dev/docs/server/server_api#http-api-authorization">API Key
		 * Authentication</a>
		 */
		public Builder apiKey(String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		/**
		 * Sets the base URL for the Centrifugo server API.
		 *
		 * <p>
		 * Should include the protocol, host, port, and path to the API endpoint.
		 * Typically this will be something like {@code http://localhost:8000/api} or
		 * {@code https://centrifugo.example.com/api}.
		 * </p>
		 * @param baseUrl the base URL for API calls
		 * @return this builder instance
		 */
		public Builder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		/**
		 * Sets the HTTP client implementation to use.
		 *
		 * <p>
		 * Allows customization of the underlying HTTP client. If not set, a default
		 * HTTP/2 client will be used.
		 * </p>
		 * @param client the HTTP client implementation
		 * @return this builder instance
		 */
		public Builder client(Client client) {
			this.client = client;
			return this;
		}

		/**
		 * Sets the retry policy for failed requests.
		 *
		 * <p>
		 * Configures how the client should handle request failures and retries. If not
		 * set, a default retry policy will be used.
		 * </p>
		 * @param retryer the retry policy implementation
		 * @return this builder instance
		 */
		public Builder retryer(Retryer retryer) {
			this.retryer = retryer;
			return this;
		}

		/**
		 * Sets the HTTP request options including timeouts.
		 *
		 * <p>
		 * Configures connection and read timeouts for HTTP requests. Default timeouts are
		 * usually sufficient for most use cases.
		 * </p>
		 * @param feignOptions the HTTP request options
		 * @return this builder instance
		 */
		public Builder feignOptions(Request.Options feignOptions) {
			this.feignOptions = feignOptions;
			return this;
		}

		/**
		 * Sets the logger implementation for HTTP request/response logging.
		 *
		 * <p>
		 * Useful for debugging API interactions. If not set, SLF4J logger will be used by
		 * default.
		 * </p>
		 * @param logger the logger implementation
		 * @return this builder instance
		 */
		public Builder logger(Logger logger) {
			this.logger = logger;
			return this;
		}

		/**
		 * Sets the error decoder for handling HTTP error responses.
		 *
		 * <p>
		 * Customizes how HTTP error responses are converted to Java exceptions. If not
		 * set, a default error decoder will be used.
		 * </p>
		 * @param errorDecoder the error decoder implementation
		 * @return this builder instance
		 */
		public Builder errorDecoder(ErrorDecoder errorDecoder) {
			this.errorDecoder = errorDecoder;
			return this;
		}

		/**
		 * Sets an additional request interceptor.
		 *
		 * <p>
		 * Allows adding custom headers or modifying requests before they are sent to the
		 * server. This is applied in addition to the built-in API key interceptor.
		 * </p>
		 * @param additionalRequestInterceptor the request interceptor
		 * @return this builder instance
		 */
		public Builder additionalRequestInterceptor(RequestInterceptor additionalRequestInterceptor) {
			this.additionalRequestInterceptor = additionalRequestInterceptor;
			return this;
		}

		/**
		 * Sets the logging level for HTTP requests and responses.
		 *
		 * <p>
		 * Controls how much detail is logged about HTTP interactions. Options include
		 * NONE, BASIC, HEADERS, and FULL.
		 * </p>
		 * @param logLevel the logging level
		 * @return this builder instance
		 */
		public Builder logLevel(Level logLevel) {
			this.logLevel = logLevel;
			return this;
		}

		/**
		 * Builds the configuration instance with the specified settings.
		 *
		 * <p>
		 * Applies default values for any settings that were not explicitly configured.
		 * </p>
		 * @return a new Configuration instance
		 */
		public Configuration build() {
			return new Configuration(this);
		}

	}

	/**
	 * Gets the configured API key.
	 * @return the API key for authentication
	 */
	public String apiKey() {
		return this.apiKey;
	}

	/**
	 * Gets the configured base URL.
	 * @return the base URL for API calls
	 */
	public String baseUrl() {
		return this.baseUrl;
	}

	/**
	 * Gets the configured HTTP client.
	 * @return the HTTP client implementation
	 */
	public Client client() {
		return this.client;
	}

	/**
	 * Gets the configured retry policy.
	 * @return the retry policy implementation
	 */
	public Retryer retryer() {
		return this.retryer;
	}

	/**
	 * Gets the configured HTTP request options.
	 * @return the HTTP request options
	 */
	public Request.Options feignOptions() {
		return this.feignOptions;
	}

	/**
	 * Gets the configured logger.
	 * @return the logger implementation
	 */
	public Logger logger() {
		return this.logger;
	}

	/**
	 * Gets the configured error decoder.
	 * @return the error decoder implementation
	 */
	public ErrorDecoder errorDecoder() {
		return this.errorDecoder;
	}

	/**
	 * Gets the configured additional request interceptor.
	 * @return the additional request interceptor
	 */
	public RequestInterceptor additionalRequestInterceptor() {
		return this.additionalRequestInterceptor;
	}

	/**
	 * Gets the configured logging level.
	 * @return the logging level
	 */
	public Level logLevel() {
		return this.logLevel;
	}

}
