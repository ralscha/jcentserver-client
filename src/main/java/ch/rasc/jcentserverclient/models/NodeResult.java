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
 * Node information.
 */
public record NodeResult(@JsonProperty("uid") String uid, @JsonProperty("name") String name,
		@JsonProperty("version") String version, @JsonProperty("num_clients") Long numClients,
		@JsonProperty("num_users") Long numUsers, @JsonProperty("num_channels") Long numChannels,
		@JsonProperty("uptime") Long uptime, @JsonProperty("metrics") Metrics metrics,
		@JsonProperty("process") Process process, @JsonProperty("num_subs") Long numSubs) {
}
