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
package ch.rasc.jcentserverclient.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ch.rasc.jcentserverclient.ApiException;
import ch.rasc.jcentserverclient.CentrifugoServerApiClient;
import ch.rasc.jcentserverclient.Configuration;
import ch.rasc.jcentserverclient.models.BroadcastRequest;
import ch.rasc.jcentserverclient.models.BroadcastResponse;
import ch.rasc.jcentserverclient.models.DisconnectRequest;
import ch.rasc.jcentserverclient.models.DisconnectResponse;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.PublishResponse;
import ch.rasc.jcentserverclient.models.SubscribeRequest;
import ch.rasc.jcentserverclient.models.SubscribeResponse;
import ch.rasc.jcentserverclient.models.UnsubscribeRequest;
import ch.rasc.jcentserverclient.models.UnsubscribeResponse;

/**
 * Example demonstrating how to use the Centrifugo Java client.
 */
public class ClientExample {

	public static void main(String[] args) {
		String apiKey = "1BdKfKI_r6Krx6OaB65d62DrBUBGeILvADkOeWQRCpkawv8gzogkasgzlIuj6_hD99hZxvTbueJdjUU6PD7dkg";

		// Create client with builder pattern
		CentrifugoServerApiClient client = CentrifugoServerApiClient
			.create(config -> config.apiKey(apiKey).baseUrl("http://localhost:8000/api"));

		// Alternative way to create client
		Configuration configuration = Configuration.builder()
			.apiKey(apiKey)
			.baseUrl("http://localhost:8000/api")
			.build();

		CentrifugoServerApiClient.create(configuration);

		// Example: Publish message to a channel
		publishExample(client);

		// Example: Broadcast message to multiple channels
		broadcastExample(client);

		// Example: Subscribe user to channel
		subscribeExample(client);

		// Example: List channels
		listChannelsExample(client);

		// Example: Unsubscribe user from channel
		unsubscribeExample(client);

		// Example: Disconnect user
		disconnectExample(client);
	}

	private static void publishExample(CentrifugoServerApiClient client) {
		System.out.println("=== Publish Example ===");

		// Create message data
		Map<String, Object> messageData = new HashMap<>();
		messageData.put("text", "Hello, Centrifugo!");
		messageData.put("timestamp", System.currentTimeMillis());

		// Build publish request using builder pattern
		PublishRequest publishRequest = PublishRequest.builder().channel("chat:general").data(messageData).build();

		try {
			PublishResponse response = client.publication().publish(publishRequest);

			if (response.hasError()) {
				System.err.println("Publish error: " + response.error().message());
			}
			else {
				System.out.println("Message published successfully!");
				System.out.println("Offset: " + response.result().offset());
				System.out.println("Epoch: " + response.result().epoch());
			}
		}
		catch (ApiException e) {
			System.err.println("API Exception: " + e.getMessage());
			System.err.println("Error code: " + e.getErrorCode());
		}
	}

	private static void listChannelsExample(CentrifugoServerApiClient client) {
		System.out.println("=== List Channels Example ===");

		try {
			// Fetch list of channels
			var response = client.channels().channels();

			if (response.hasError()) {
				System.err.println("List channels error: " + response.error().message());
			}
			else {
				System.out.println("Channels:");
				response.result().channels().forEach((name, info) -> {
					System.out.println(" - " + name + " (subscribers: " + info.numClients() + ")");
				});
			}
		}
		catch (ApiException e) {
			System.err.println("API Exception: " + e.getMessage());
		}
	}

	private static void broadcastExample(CentrifugoServerApiClient client) {
		System.out.println("=== Broadcast Example ===");

		// Create broadcast data
		Map<String, Object> broadcastData = new HashMap<>();
		broadcastData.put("announcement", "Server maintenance in 10 minutes");
		broadcastData.put("priority", "high");

		// Build broadcast request
		BroadcastRequest broadcastRequest = BroadcastRequest.builder()
			.channels(Arrays.asList("chat:general", "chat:announcements", "notifications"))
			.data(broadcastData)
			.build();

		try {
			BroadcastResponse response = client.publication().broadcast(broadcastRequest);

			if (response.hasError()) {
				System.err.println("Broadcast error: " + response.error().message());
			}
			else {
				System.out.println("Broadcast sent successfully!");
				System.out.println("Individual responses: " + response.result().responses().size());
				for (PublishResponse r : response.result().responses()) {
					System.out.println(r);
				}
			}
		}
		catch (ApiException e) {
			System.err.println("API Exception: " + e.getMessage());
		}
	}

	private static void subscribeExample(CentrifugoServerApiClient client) {
		System.out.println("=== Subscribe Example ===");

		// Subscribe user to channel
		SubscribeRequest subscribeRequest = SubscribeRequest.builder()
			.user("user123")
			.channel("personal:user123")
			.build();

		try {
			SubscribeResponse response = client.connection().subscribe(subscribeRequest);

			if (response.hasError()) {
				System.err.println("Subscribe error: " + response.error().message());
			}
			else {
				System.out.println("User subscribed successfully!");
			}
		}
		catch (ApiException e) {
			System.err.println("API Exception: " + e.getMessage());
		}
	}

	private static void unsubscribeExample(CentrifugoServerApiClient client) {
		System.out.println("=== Unsubscribe Example ===");

		// Unsubscribe user from channel
		UnsubscribeRequest unsubscribeRequest = UnsubscribeRequest.builder()
			.user("user123")
			.channel("personal:user123")
			.build();

		try {
			UnsubscribeResponse response = client.connection().unsubscribe(unsubscribeRequest);

			if (response.hasError()) {
				System.err.println("Unsubscribe error: " + response.error().message());
			}
			else {
				System.out.println("User unsubscribed successfully!");
			}
		}
		catch (ApiException e) {
			System.err.println("API Exception: " + e.getMessage());
		}
	}

	private static void disconnectExample(CentrifugoServerApiClient client) {
		System.out.println("=== Disconnect Example ===");

		// Disconnect user
		DisconnectRequest disconnectRequest = DisconnectRequest.builder().user("user123").build();

		try {
			DisconnectResponse response = client.connection().disconnect(disconnectRequest);

			if (response.hasError()) {
				System.err.println("Disconnect error: " + response.error().message());
			}
			else {
				System.out.println("User disconnected successfully!");
			}
		}
		catch (ApiException e) {
			System.err.println("API Exception: " + e.getMessage());
		}
	}

}
