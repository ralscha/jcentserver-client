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

import ch.rasc.jcentserverclient.models.CancelPushRequest;
import ch.rasc.jcentserverclient.models.CancelPushResponse;
import ch.rasc.jcentserverclient.models.SendPushNotificationRequest;
import ch.rasc.jcentserverclient.models.SendPushNotificationResponse;
import ch.rasc.jcentserverclient.models.UpdatePushStatusRequest;
import ch.rasc.jcentserverclient.models.UpdatePushStatusResponse;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface PushClient {

	@RequestLine("POST /send_push_notification")
	SendPushNotificationResponse sendNotification(SendPushNotificationRequest request);

	default SendPushNotificationResponse sendNotification(
			Function<SendPushNotificationRequest.Builder, SendPushNotificationRequest.Builder> fn) {
		return this.sendNotification(fn.apply(SendPushNotificationRequest.builder()).build());
	}

	@RequestLine("POST /update_push_status")
	UpdatePushStatusResponse updateStatus(UpdatePushStatusRequest request);

	default UpdatePushStatusResponse updateStatus(
			Function<UpdatePushStatusRequest.Builder, UpdatePushStatusRequest.Builder> fn) {
		return this.updateStatus(fn.apply(UpdatePushStatusRequest.builder()).build());
	}

	@RequestLine("POST /cancel_push")
	CancelPushResponse cancel(CancelPushRequest request);

	default CancelPushResponse cancel(Function<CancelPushRequest.Builder, CancelPushRequest.Builder> fn) {
		return this.cancel(fn.apply(CancelPushRequest.builder()).build());
	}

}
