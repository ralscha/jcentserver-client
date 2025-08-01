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
package ch.rasc.jcentserverclient.clients;

import ch.rasc.jcentserverclient.models.ChannelsRequest;
import ch.rasc.jcentserverclient.models.ChannelsResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo Channels API client interface. Provides methods for listing and managing
 * channels.
 */
public interface ChannelsClient {

	/**
	 * List active channels. Returns information about currently active channels.
	 * @param request the channels request with optional pattern filter
	 * @return the channels response containing active channels
	 */
	@RequestLine("POST /channels")
	@Headers("Content-Type: application/json")
	ChannelsResponse channels(ChannelsRequest request);

	/**
	 * List active channels. Returns information about currently active channels.
	 * @param pattern the pattern to filter channels
	 * @return the channels response containing active channels
	 */
	default ChannelsResponse channels(String pattern) {
		return this.channels(ChannelsRequest.of(pattern));
	}

	/**
	 * List all channels without any filters.
	 * @return the channels response containing all active channels
	 */
	default ChannelsResponse channels() {
		return channels(ChannelsRequest.of(null));
	}

}
