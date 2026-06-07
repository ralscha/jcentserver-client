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

import ch.rasc.jcentserverclient.models.DeviceListRequest;
import ch.rasc.jcentserverclient.models.DeviceListResponse;
import ch.rasc.jcentserverclient.models.DeviceRegisterRequest;
import ch.rasc.jcentserverclient.models.DeviceRegisterResponse;
import ch.rasc.jcentserverclient.models.DeviceRemoveRequest;
import ch.rasc.jcentserverclient.models.DeviceRemoveResponse;
import ch.rasc.jcentserverclient.models.DeviceTopicListRequest;
import ch.rasc.jcentserverclient.models.DeviceTopicListResponse;
import ch.rasc.jcentserverclient.models.DeviceTopicUpdateRequest;
import ch.rasc.jcentserverclient.models.DeviceTopicUpdateResponse;
import ch.rasc.jcentserverclient.models.DeviceUpdateRequest;
import ch.rasc.jcentserverclient.models.DeviceUpdateResponse;
import ch.rasc.jcentserverclient.models.UserTopicListRequest;
import ch.rasc.jcentserverclient.models.UserTopicListResponse;
import ch.rasc.jcentserverclient.models.UserTopicUpdateRequest;
import ch.rasc.jcentserverclient.models.UserTopicUpdateResponse;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface DeviceClient {

	@RequestLine("POST /device_register")
	DeviceRegisterResponse register(DeviceRegisterRequest request);

	default DeviceRegisterResponse register(Function<DeviceRegisterRequest.Builder, DeviceRegisterRequest.Builder> fn) {
		return this.register(fn.apply(DeviceRegisterRequest.builder()).build());
	}

	@RequestLine("POST /device_update")
	DeviceUpdateResponse update(DeviceUpdateRequest request);

	default DeviceUpdateResponse update(Function<DeviceUpdateRequest.Builder, DeviceUpdateRequest.Builder> fn) {
		return this.update(fn.apply(DeviceUpdateRequest.builder()).build());
	}

	@RequestLine("POST /device_remove")
	DeviceRemoveResponse remove(DeviceRemoveRequest request);

	default DeviceRemoveResponse remove(Function<DeviceRemoveRequest.Builder, DeviceRemoveRequest.Builder> fn) {
		return this.remove(fn.apply(DeviceRemoveRequest.builder()).build());
	}

	@RequestLine("POST /device_list")
	DeviceListResponse list(DeviceListRequest request);

	default DeviceListResponse list(Function<DeviceListRequest.Builder, DeviceListRequest.Builder> fn) {
		return this.list(fn.apply(DeviceListRequest.builder()).build());
	}

	@RequestLine("POST /device_topic_list")
	DeviceTopicListResponse listDeviceTopics(DeviceTopicListRequest request);

	default DeviceTopicListResponse listDeviceTopics(
			Function<DeviceTopicListRequest.Builder, DeviceTopicListRequest.Builder> fn) {
		return this.listDeviceTopics(fn.apply(DeviceTopicListRequest.builder()).build());
	}

	@RequestLine("POST /device_topic_update")
	DeviceTopicUpdateResponse updateDeviceTopics(DeviceTopicUpdateRequest request);

	default DeviceTopicUpdateResponse updateDeviceTopics(
			Function<DeviceTopicUpdateRequest.Builder, DeviceTopicUpdateRequest.Builder> fn) {
		return this.updateDeviceTopics(fn.apply(DeviceTopicUpdateRequest.builder()).build());
	}

	@RequestLine("POST /user_topic_list")
	UserTopicListResponse listUserTopics(UserTopicListRequest request);

	default UserTopicListResponse listUserTopics(
			Function<UserTopicListRequest.Builder, UserTopicListRequest.Builder> fn) {
		return this.listUserTopics(fn.apply(UserTopicListRequest.builder()).build());
	}

	@RequestLine("POST /user_topic_update")
	UserTopicUpdateResponse updateUserTopics(UserTopicUpdateRequest request);

	default UserTopicUpdateResponse updateUserTopics(
			Function<UserTopicUpdateRequest.Builder, UserTopicUpdateRequest.Builder> fn) {
		return this.updateUserTopics(fn.apply(UserTopicUpdateRequest.builder()).build());
	}

}
