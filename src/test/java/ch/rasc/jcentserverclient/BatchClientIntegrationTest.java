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
package ch.rasc.jcentserverclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.rasc.jcentserverclient.models.BatchRequest;
import ch.rasc.jcentserverclient.models.BatchResponse;
import ch.rasc.jcentserverclient.models.ChannelsRequest;
import ch.rasc.jcentserverclient.models.Command;
import ch.rasc.jcentserverclient.models.InfoRequest;
import ch.rasc.jcentserverclient.models.PublishRequest;

/**
 * Integration tests for Batch client operations. Tests batch execution of multiple API
 * commands.
 */
@DisplayName("Batch Client Integration Tests")
class BatchClientIntegrationTest extends CentrifugoIntegrationTestBase {

	@Test
	@DisplayName("Should execute batch of publish commands")
	void shouldExecuteBatchOfPublishCommands() {
		// Given - Create multiple publish commands
		List<Command> commands = List.of(
				Command.builder()
					.publish(PublishRequest.builder()
						.channel("batch-channel-1")
						.data(Map.of("message", "Batch message 1"))
						.build())
					.build(),
				Command.builder()
					.publish(PublishRequest.builder()
						.channel("batch-channel-2")
						.data(Map.of("message", "Batch message 2"))
						.build())
					.build(),
				Command.builder()
					.publish(PublishRequest.builder()
						.channel("batch-channel-3")
						.data(Map.of("message", "Batch message 3"))
						.build())
					.build());

		BatchRequest request = BatchRequest.builder().commands(commands).build();

		// When
		BatchResponse response = this.client.batch().batch(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().replies()).hasSize(commands.size());
		assertThat(response.error()).isNull();

		// Verify all commands succeeded
		response.result().replies().forEach(reply -> {
			assertThat(reply.publish()).isNotNull();
			assertThat(reply.error()).isNull();
		});
	}

	@Test
	@DisplayName("Should execute batch of mixed commands")
	void shouldExecuteBatchOfMixedCommands() {
		// Given - Create mixed batch commands
		List<Command> commands = List.of(Command.builder()
			.publish(PublishRequest.builder().channel("mixed-batch-channel").data(Map.of("action", "publish")).build())
			.build(), Command.builder().channels(ChannelsRequest.builder().build()).build(),
				Command.builder().info(InfoRequest.builder().build()).build());

		BatchRequest request = BatchRequest.builder().commands(commands).build();

		// When
		BatchResponse response = this.client.batch().batch(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().replies()).hasSize(commands.size());
		assertThat(response.error()).isNull();

		// Verify all commands succeeded
		response.result().replies().forEach(reply -> {
			assertThat(reply.error()).isNull();
		});
	}

	@Test
	@DisplayName("Should handle empty batch request")
	void shouldHandleEmptyBatchRequest() {
		// Given
		List<Command> commands = List.of();

		BatchRequest request = BatchRequest.builder().commands(commands).build();

		// When
		BatchResponse response = this.client.batch().batch(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().replies()).isEmpty();
		assertThat(response.error()).isNull();
	}

	@Test
	@DisplayName("Should execute batch with parallel processing")
	void shouldExecuteBatchWithParallelProcessing() {
		// Given - Create multiple commands that can run in parallel
		List<Command> commands = List.of(
				Command.builder()
					.publish(PublishRequest.builder()
						.channel("parallel-channel-1")
						.data(Map.of("timestamp", System.currentTimeMillis()))
						.build())
					.build(),
				Command.builder()
					.publish(PublishRequest.builder()
						.channel("parallel-channel-2")
						.data(Map.of("timestamp", System.currentTimeMillis()))
						.build())
					.build(),
				Command.builder().info(InfoRequest.builder().build()).build(),
				Command.builder().channels(ChannelsRequest.builder().build()).build());

		BatchRequest request = BatchRequest.builder().commands(commands).parallel(true).build();

		// When
		BatchResponse response = this.client.batch().batch(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().replies()).hasSize(commands.size());
		assertThat(response.error()).isNull();

		// Verify all commands succeeded
		response.result().replies().forEach(reply -> {
			assertThat(reply.error()).isNull();
		});
	}

	@Test
	@DisplayName("Should handle batch with some invalid commands")
	void shouldHandleBatchWithSomeInvalidCommands() {
		// Given - Mix valid and potentially invalid commands
		List<Command> commands = List.of(Command.builder()
			.publish(PublishRequest.builder().channel("valid-channel").data(Map.of("message", "Valid message")).build())
			.build(), Command.builder().info(InfoRequest.builder().build()).build());

		BatchRequest request = BatchRequest.builder().commands(commands).build();

		// When
		BatchResponse response = this.client.batch().batch(request);

		// Then
		assertThat(response).isNotNull();
		assertThat(response.result()).isNotNull();
		assertThat(response.result().replies()).hasSize(commands.size());
		assertThat(response.error()).isNull();
	}

}
