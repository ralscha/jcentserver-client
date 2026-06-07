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
public record PushRecipient(@JsonProperty("filter") DeviceFilter filter,
		@JsonProperty("fcm_tokens") List<String> fcmTokens, @JsonProperty("fcm_topic") String fcmTopic,
		@JsonProperty("fcm_condition") String fcmCondition, @JsonProperty("hms_tokens") List<String> hmsTokens,
		@JsonProperty("hms_topic") String hmsTopic, @JsonProperty("hms_condition") String hmsCondition,
		@JsonProperty("apns_tokens") List<String> apnsTokens) {
}
