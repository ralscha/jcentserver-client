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

import java.util.function.Function;

import ch.rasc.jcentserverclient.models.InfoRequest;
import ch.rasc.jcentserverclient.models.InfoResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo User Management API client interface. Provides methods for managing users.
 */
@Headers("Content-Type: application/json")
public interface UserManagementClient {

	/**
	 * Get information about connections.
	 * @param request the info request
	 * @return the info response containing connection information
	 */
	@RequestLine("POST /info")
	InfoResponse info(InfoRequest request);

	/**
	 * Get information about connections.
	 * @param fn the function to configure the info request
	 * @return the info response containing connection information
	 */
	default InfoResponse info(Function<InfoRequest.Builder, InfoRequest.Builder> fn) {
		return this.info(fn.apply(InfoRequest.builder()).build());
	}

}
