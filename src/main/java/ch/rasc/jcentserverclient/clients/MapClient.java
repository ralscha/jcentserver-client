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

	default MapPublishResponse mapPublish(Function<MapPublishRequest.Builder, MapPublishRequest.Builder> fn) {
		return this.mapPublish(fn.apply(MapPublishRequest.builder()).build());
	}

	@RequestLine("POST /map_remove")
	MapRemoveResponse mapRemove(MapRemoveRequest request);

	default MapRemoveResponse mapRemove(Function<MapRemoveRequest.Builder, MapRemoveRequest.Builder> fn) {
		return this.mapRemove(fn.apply(MapRemoveRequest.builder()).build());
	}

	@RequestLine("POST /map_read_state")
	MapReadStateResponse mapReadState(MapReadStateRequest request);

	default MapReadStateResponse mapReadState(Function<MapReadStateRequest.Builder, MapReadStateRequest.Builder> fn) {
		return this.mapReadState(fn.apply(MapReadStateRequest.builder()).build());
	}

	@RequestLine("POST /map_read_stream")
	MapReadStreamResponse mapReadStream(MapReadStreamRequest request);

	default MapReadStreamResponse mapReadStream(
			Function<MapReadStreamRequest.Builder, MapReadStreamRequest.Builder> fn) {
		return this.mapReadStream(fn.apply(MapReadStreamRequest.builder()).build());
	}

	@RequestLine("POST /map_stats")
	MapStatsResponse mapStats(MapStatsRequest request);

	default MapStatsResponse mapStats(Function<MapStatsRequest.Builder, MapStatsRequest.Builder> fn) {
		return this.mapStats(fn.apply(MapStatsRequest.builder()).build());
	}

	@RequestLine("POST /map_clear")
	MapClearResponse mapClear(MapClearRequest request);

	default MapClearResponse mapClear(Function<MapClearRequest.Builder, MapClearRequest.Builder> fn) {
		return this.mapClear(fn.apply(MapClearRequest.builder()).build());
	}

	@RequestLine("POST /shared_poll_publish")
	SharedPollPublishResponse sharedPollPublish(SharedPollPublishRequest request);

	default SharedPollPublishResponse sharedPollPublish(
			Function<SharedPollPublishRequest.Builder, SharedPollPublishRequest.Builder> fn) {
		return this.sharedPollPublish(fn.apply(SharedPollPublishRequest.builder()).build());
	}

}
