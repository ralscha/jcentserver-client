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
import ch.rasc.jcentserverclient.models.BlockUserRequest;
import ch.rasc.jcentserverclient.models.BroadcastRequest;
import ch.rasc.jcentserverclient.models.Command;
import ch.rasc.jcentserverclient.models.ConnectionsRequest;
import ch.rasc.jcentserverclient.models.InvalidateUserTokensRequest;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.RevokeTokenRequest;
import ch.rasc.jcentserverclient.models.RpcRequest;
import ch.rasc.jcentserverclient.models.SubscribeOptionOverride;
import tools.jackson.databind.json.JsonMapper;

@DisplayName("Request Serialization Tests")
class RequestSerializationTest {

	private static final JsonMapper OBJECT_MAPPER = JsonMapper.builder().build();

	@Test
	@DisplayName("Should serialize publish version fields")
	void shouldSerializePublishVersionFields() throws Exception {
		PublishRequest request = PublishRequest.builder()
			.channel("chat:versioned")
			.data(Map.of("text", "hello"))
			.version(7L)
			.versionEpoch("doc-epoch")
			.build();

		String json = OBJECT_MAPPER.writeValueAsString(request);

		assertThat(json).contains("\"version\":7");
		assertThat(json).contains("\"version_epoch\":\"doc-epoch\"");
	}

	@Test
	@DisplayName("Should serialize broadcast version fields")
	void shouldSerializeBroadcastVersionFields() throws Exception {
		BroadcastRequest request = BroadcastRequest.builder()
			.channels(List.of("chat:1", "chat:2"))
			.data(Map.of("text", "hello"))
			.version(11L)
			.versionEpoch("epoch-2")
			.build();

		String json = OBJECT_MAPPER.writeValueAsString(request);

		assertThat(json).contains("\"version\":11");
		assertThat(json).contains("\"version_epoch\":\"epoch-2\"");
	}

	@Test
	@DisplayName("Should serialize subscribe overrides as BoolValue objects")
	void shouldSerializeSubscribeOverridesAsBoolValueObjects() throws Exception {
		SubscribeOptionOverride override = SubscribeOptionOverride.builder()
			.presence(true)
			.joinLeave(false)
			.forcePushJoinLeave(true)
			.forcePositioning(false)
			.forceRecovery(true)
			.build();

		String json = OBJECT_MAPPER.writeValueAsString(override);

		assertThat(json).contains("\"presence\":{\"value\":true}");
		assertThat(json).contains("\"join_leave\":{\"value\":false}");
		assertThat(json).contains("\"force_push_join_leave\":{\"value\":true}");
		assertThat(json).contains("\"force_positioning\":{\"value\":false}");
		assertThat(json).contains("\"force_recovery\":{\"value\":true}");
	}

	@Test
	@DisplayName("Should serialize block user expire_at")
	void shouldSerializeBlockUserExpireAt() throws Exception {
		BlockUserRequest request = BlockUserRequest.builder().user("user-1").expireAt(123L).build();

		String json = OBJECT_MAPPER.writeValueAsString(request);

		assertThat(json).contains("\"expire_at\":123");
		assertThat(json).doesNotContain("expired_at");
	}

	@Test
	@DisplayName("Should serialize revoke token expire_at")
	void shouldSerializeRevokeTokenExpireAt() throws Exception {
		RevokeTokenRequest request = RevokeTokenRequest.builder().uid("token-1").expireAt(456L).build();

		String json = OBJECT_MAPPER.writeValueAsString(request);

		assertThat(json).contains("\"uid\":\"token-1\"");
		assertThat(json).contains("\"expire_at\":456");
	}

	@Test
	@DisplayName("Should serialize invalidate user tokens extra fields")
	void shouldSerializeInvalidateUserTokensExtraFields() throws Exception {
		InvalidateUserTokensRequest request = InvalidateUserTokensRequest.builder()
			.user("user-2")
			.issuedBefore(100L)
			.expireAt(200L)
			.channel("private:user-2")
			.build();

		String json = OBJECT_MAPPER.writeValueAsString(request);

		assertThat(json).contains("\"issued_before\":100");
		assertThat(json).contains("\"expire_at\":200");
		assertThat(json).contains("\"channel\":\"private:user-2\"");
	}

	@Test
	@DisplayName("Should serialize batch rpc and connections commands")
	void shouldSerializeBatchRpcAndConnectionsCommands() throws Exception {
		BatchRequest request = BatchRequest.builder()
			.commands(
					Command.builder().rpc(RpcRequest.builder().method("ping").params(Map.of("value", 1)).build()).build(),
					Command.builder().connections(ConnectionsRequest.builder().user("user-3").build()).build())
			.build();

		String json = OBJECT_MAPPER.writeValueAsString(request);

		assertThat(json).contains("\"rpc\":{\"method\":\"ping\",\"params\":{\"value\":1}}");
		assertThat(json).contains("\"connections\":{\"user\":\"user-3\"}");
	}

}
