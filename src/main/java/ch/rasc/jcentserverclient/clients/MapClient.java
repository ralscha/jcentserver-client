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

import ch.rasc.jcentserverclient.models.MapClearRequest;
import ch.rasc.jcentserverclient.models.MapClearResponse;
import ch.rasc.jcentserverclient.models.MapPublishRequest;
import ch.rasc.jcentserverclient.models.MapPublishResponse;
import ch.rasc.jcentserverclient.models.MapReadStateRequest;
import ch.rasc.jcentserverclient.models.MapReadStateResponse;
import ch.rasc.jcentserverclient.models.MapReadStreamRequest;
import ch.rasc.jcentserverclient.models.MapReadStreamResponse;
import ch.rasc.jcentserverclient.models.MapRemoveRequest;
import ch.rasc.jcentserverclient.models.MapRemoveResponse;
import ch.rasc.jcentserverclient.models.MapStatsRequest;
import ch.rasc.jcentserverclient.models.MapStatsResponse;
import ch.rasc.jcentserverclient.models.SharedPollPublishRequest;
import ch.rasc.jcentserverclient.models.SharedPollPublishResponse;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface MapClient {

	@RequestLine("POST /map_publish")
	MapPublishResponse mapPublish(MapPublishRequest request);

	@RequestLine("POST /map_remove")
	MapRemoveResponse mapRemove(MapRemoveRequest request);

	@RequestLine("POST /map_read_state")
	MapReadStateResponse mapReadState(MapReadStateRequest request);

	@RequestLine("POST /map_read_stream")
	MapReadStreamResponse mapReadStream(MapReadStreamRequest request);

	@RequestLine("POST /map_stats")
	MapStatsResponse mapStats(MapStatsRequest request);

	@RequestLine("POST /map_clear")
	MapClearResponse mapClear(MapClearRequest request);

	@RequestLine("POST /shared_poll_publish")
	SharedPollPublishResponse sharedPollPublish(SharedPollPublishRequest request);

}
