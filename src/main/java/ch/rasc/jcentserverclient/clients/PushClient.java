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

	/**
	 * Alias matching the Centrifugo {@code send_push_notification} operation name.
	 * @param request the notification request
	 * @return the Centrifugo response
	 */
	default SendPushNotificationResponse sendPushNotification(SendPushNotificationRequest request) {
		return this.sendNotification(request);
	}

	/**
	 * Builder overload for {@link #sendPushNotification(SendPushNotificationRequest)}.
	 * @param fn request builder configuration
	 * @return the Centrifugo response
	 */
	default SendPushNotificationResponse sendPushNotification(
			Function<SendPushNotificationRequest.Builder, SendPushNotificationRequest.Builder> fn) {
		return this.sendNotification(fn);
	}

	@RequestLine("POST /update_push_status")
	UpdatePushStatusResponse updateStatus(UpdatePushStatusRequest request);

	default UpdatePushStatusResponse updateStatus(
			Function<UpdatePushStatusRequest.Builder, UpdatePushStatusRequest.Builder> fn) {
		return this.updateStatus(fn.apply(UpdatePushStatusRequest.builder()).build());
	}

	default UpdatePushStatusResponse updatePushStatus(UpdatePushStatusRequest request) {
		return this.updateStatus(request);
	}

	default UpdatePushStatusResponse updatePushStatus(
			Function<UpdatePushStatusRequest.Builder, UpdatePushStatusRequest.Builder> fn) {
		return this.updateStatus(fn);
	}

	@RequestLine("POST /cancel_push")
	CancelPushResponse cancel(CancelPushRequest request);

	default CancelPushResponse cancel(Function<CancelPushRequest.Builder, CancelPushRequest.Builder> fn) {
		return this.cancel(fn.apply(CancelPushRequest.builder()).build());
	}

	default CancelPushResponse cancelPush(CancelPushRequest request) {
		return this.cancel(request);
	}

	default CancelPushResponse cancelPush(Function<CancelPushRequest.Builder, CancelPushRequest.Builder> fn) {
		return this.cancel(fn);
	}

}
