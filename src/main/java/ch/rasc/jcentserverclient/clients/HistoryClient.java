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

import ch.rasc.jcentserverclient.models.HistoryRemoveRequest;
import ch.rasc.jcentserverclient.models.HistoryRemoveResponse;
import ch.rasc.jcentserverclient.models.HistoryRequest;
import ch.rasc.jcentserverclient.models.HistoryResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo history API client for managing channel message history.
 *
 * <p>
 * This client provides access to Centrifugo's channel history functionality, allowing you
 * to retrieve and manage message history stored in channels. History must be enabled in
 * channel configuration for these operations to work.
 * </p>
 *
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Retrieve channel message history with pagination</li>
 * <li>Get current stream position information</li>
 * <li>History iteration with since/limit parameters</li>
 * <li>Reverse chronological ordering</li>
 * <li>History cleanup and removal</li>
 * </ul>
 *
 * @see <a href="https://centrifugal.dev/docs/server/server_api#history">History API
 * Documentation</a>
 * @see <a href="https://centrifugal.dev/docs/server/history_and_recovery">History and
 * Recovery</a>
 * @since 1.0.0
 */
@Headers("Content-Type: application/json")
public interface HistoryClient {

	/**
	 * Get channel history.
	 *
	 * <p>
	 * Retrieves message history for a specific channel. By default, if no limit parameter
	 * is set, only current stream position information is returned (offset and epoch). To
	 * get actual publications, you must explicitly provide a limit parameter.
	 * </p>
	 *
	 * <p>
	 * Features:
	 * </p>
	 * <ul>
	 * <li>Pagination with limit parameter</li>
	 * <li>Stream position-based iteration</li>
	 * <li>Reverse chronological ordering</li>
	 * <li>Current stream position metadata</li>
	 * </ul>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Loading chat history for new participants</li>
	 * <li>Implementing message recovery</li>
	 * <li>Building message pagination</li>
	 * <li>Synchronizing client state</li>
	 * </ul>
	 *
	 * <p>
	 * Example for getting recent messages:
	 * </p>
	 *
	 * <pre>{@code
	 * HistoryRequest request = HistoryRequest.builder().channel("chat:room1").limit(50)
	 * 		.reverse(true) // Get latest messages first
	 * 		.build();
	 *
	 * HistoryResponse response = client.history(request);
	 * }</pre>
	 *
	 * <p>
	 * Example for pagination:
	 * </p>
	 *
	 * <pre>{@code
	 * HistoryRequest request = HistoryRequest.builder().channel("chat:room1").limit(20)
	 * 		.since(StreamPosition.builder().offset(100).epoch("abc123").build())
	 * 		.build();
	 *
	 * HistoryResponse response = client.history(request);
	 * }</pre>
	 * @param request the history request containing channel, pagination, and filtering
	 * options
	 * @return the history response containing publications and stream position
	 * information
	 * @see <a href="https://centrifugal.dev/docs/server/server_api#history">History
	 * Documentation</a>
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/history_and_recovery#history-iteration-api">History
	 * Iteration API</a>
	 */
	@RequestLine("POST /history")
	HistoryResponse history(HistoryRequest request);

	/**
	 * Remove channel history.
	 *
	 * <p>
	 * Removes all publications from channel history while keeping the current stream
	 * position metadata intact. This prevents client disconnects due to insufficient
	 * state while clearing historical data.
	 * </p>
	 *
	 * <p>
	 * Use cases:
	 * </p>
	 * <ul>
	 * <li>Implementing message retention policies</li>
	 * <li>Clearing sensitive historical data</li>
	 * <li>Maintaining history storage limits</li>
	 * <li>Channel cleanup operations</li>
	 * </ul>
	 *
	 * <p>
	 * Important notes:
	 * </p>
	 * <ul>
	 * <li>Only publications are removed, stream metadata is preserved</li>
	 * <li>Connected clients will not be disconnected</li>
	 * <li>New publications continue normally after removal</li>
	 * <li>Operation cannot be undone</li>
	 * </ul>
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>{@code
	 * HistoryRemoveRequest request = HistoryRemoveRequest.builder().channel("chat:room1")
	 * 		.build();
	 *
	 * HistoryRemoveResponse response = client.historyRemove(request);
	 * }</pre>
	 * @param request the history remove request containing the channel to clear
	 * @return the history remove response
	 * @see <a href=
	 * "https://centrifugal.dev/docs/server/server_api#history_remove">History Remove
	 * Documentation</a>
	 */
	@RequestLine("POST /history_remove")
	HistoryRemoveResponse historyRemove(HistoryRemoveRequest request);

}
