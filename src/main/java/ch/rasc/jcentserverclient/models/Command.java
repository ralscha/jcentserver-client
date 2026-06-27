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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Command object for batch operations.
 */
@JsonInclude(Include.NON_EMPTY)
@SuppressWarnings({ "hiding" })
public record Command(@JsonProperty("publish") PublishRequest publish,
		@JsonProperty("broadcast") BroadcastRequest broadcast, @JsonProperty("subscribe") SubscribeRequest subscribe,
		@JsonProperty("unsubscribe") UnsubscribeRequest unsubscribe,
		@JsonProperty("disconnect") DisconnectRequest disconnect, @JsonProperty("presence") PresenceRequest presence,
		@JsonProperty("presence_stats") PresenceStatsRequest presenceStats,
		@JsonProperty("history") HistoryRequest history,
		@JsonProperty("history_remove") HistoryRemoveRequest historyRemove, @JsonProperty("info") InfoRequest info,
		@JsonProperty("rpc") RpcRequest rpc, @JsonProperty("refresh") RefreshRequest refresh,
		@JsonProperty("channels") ChannelsRequest channels, @JsonProperty("connections") ConnectionsRequest connections,
		@JsonProperty("update_user_status") UpdateUserStatusRequest updateUserStatus,
		@JsonProperty("get_user_status") GetUserStatusRequest getUserStatus,
		@JsonProperty("delete_user_status") DeleteUserStatusRequest deleteUserStatus,
		@JsonProperty("block_user") BlockUserRequest blockUser,
		@JsonProperty("unblock_user") UnblockUserRequest unblockUser,
		@JsonProperty("revoke_token") RevokeTokenRequest revokeToken,
		@JsonProperty("invalidate_user_tokens") InvalidateUserTokensRequest invalidateUserTokens,
		@JsonProperty("device_register") DeviceRegisterRequest deviceRegister,
		@JsonProperty("device_update") DeviceUpdateRequest deviceUpdate,
		@JsonProperty("device_remove") DeviceRemoveRequest deviceRemove,
		@JsonProperty("device_list") DeviceListRequest deviceList,
		@JsonProperty("device_topic_list") DeviceTopicListRequest deviceTopicList,
		@JsonProperty("device_topic_update") DeviceTopicUpdateRequest deviceTopicUpdate,
		@JsonProperty("user_topic_list") UserTopicListRequest userTopicList,
		@JsonProperty("user_topic_update") UserTopicUpdateRequest userTopicUpdate,
		@JsonProperty("send_push_notification") SendPushNotificationRequest sendPushNotification,
		@JsonProperty("update_push_status") UpdatePushStatusRequest updatePushStatus,
		@JsonProperty("cancel_push") CancelPushRequest cancelPush,
		@JsonProperty("map_publish") MapPublishRequest mapPublish,
		@JsonProperty("map_remove") MapRemoveRequest mapRemove,
		@JsonProperty("map_read_state") MapReadStateRequest mapReadState,
		@JsonProperty("map_read_stream") MapReadStreamRequest mapReadStream,
		@JsonProperty("map_stats") MapStatsRequest mapStats, @JsonProperty("map_clear") MapClearRequest mapClear,
		@JsonProperty("shared_poll_publish") SharedPollPublishRequest sharedPollPublish) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private PublishRequest publish;

		private BroadcastRequest broadcast;

		private SubscribeRequest subscribe;

		private UnsubscribeRequest unsubscribe;

		private DisconnectRequest disconnect;

		private PresenceRequest presence;

		private PresenceStatsRequest presenceStats;

		private HistoryRequest history;

		private HistoryRemoveRequest historyRemove;

		private InfoRequest info;

		private RpcRequest rpc;

		private RefreshRequest refresh;

		private ChannelsRequest channels;

		private ConnectionsRequest connections;

		private UpdateUserStatusRequest updateUserStatus;

		private GetUserStatusRequest getUserStatus;

		private DeleteUserStatusRequest deleteUserStatus;

		private BlockUserRequest blockUser;

		private UnblockUserRequest unblockUser;

		private RevokeTokenRequest revokeToken;

		private InvalidateUserTokensRequest invalidateUserTokens;

		private DeviceRegisterRequest deviceRegister;

		private DeviceUpdateRequest deviceUpdate;

		private DeviceRemoveRequest deviceRemove;

		private DeviceListRequest deviceList;

		private DeviceTopicListRequest deviceTopicList;

		private DeviceTopicUpdateRequest deviceTopicUpdate;

		private UserTopicListRequest userTopicList;

		private UserTopicUpdateRequest userTopicUpdate;

		private SendPushNotificationRequest sendPushNotification;

		private UpdatePushStatusRequest updatePushStatus;

		private CancelPushRequest cancelPush;

		private MapPublishRequest mapPublish;

		private MapRemoveRequest mapRemove;

		private MapReadStateRequest mapReadState;

		private MapReadStreamRequest mapReadStream;

		private MapStatsRequest mapStats;

		private MapClearRequest mapClear;

		private SharedPollPublishRequest sharedPollPublish;

		public Builder publish(PublishRequest publish) {
			this.publish = publish;
			return this;
		}

		public Builder broadcast(BroadcastRequest broadcast) {
			this.broadcast = broadcast;
			return this;
		}

		public Builder subscribe(SubscribeRequest subscribe) {
			this.subscribe = subscribe;
			return this;
		}

		public Builder unsubscribe(UnsubscribeRequest unsubscribe) {
			this.unsubscribe = unsubscribe;
			return this;
		}

		public Builder disconnect(DisconnectRequest disconnect) {
			this.disconnect = disconnect;
			return this;
		}

		public Builder presence(PresenceRequest presence) {
			this.presence = presence;
			return this;
		}

		public Builder presenceStats(PresenceStatsRequest presenceStats) {
			this.presenceStats = presenceStats;
			return this;
		}

		public Builder history(HistoryRequest history) {
			this.history = history;
			return this;
		}

		public Builder historyRemove(HistoryRemoveRequest historyRemove) {
			this.historyRemove = historyRemove;
			return this;
		}

		public Builder info(InfoRequest info) {
			this.info = info;
			return this;
		}

		public Builder rpc(RpcRequest rpc) {
			this.rpc = rpc;
			return this;
		}

		public Builder refresh(RefreshRequest refresh) {
			this.refresh = refresh;
			return this;
		}

		public Builder channels(ChannelsRequest channels) {
			this.channels = channels;
			return this;
		}

		public Builder connections(ConnectionsRequest connections) {
			this.connections = connections;
			return this;
		}

		public Builder updateUserStatus(UpdateUserStatusRequest updateUserStatus) {
			this.updateUserStatus = updateUserStatus;
			return this;
		}

		public Builder getUserStatus(GetUserStatusRequest getUserStatus) {
			this.getUserStatus = getUserStatus;
			return this;
		}

		public Builder deleteUserStatus(DeleteUserStatusRequest deleteUserStatus) {
			this.deleteUserStatus = deleteUserStatus;
			return this;
		}

		public Builder blockUser(BlockUserRequest blockUser) {
			this.blockUser = blockUser;
			return this;
		}

		public Builder unblockUser(UnblockUserRequest unblockUser) {
			this.unblockUser = unblockUser;
			return this;
		}

		public Builder revokeToken(RevokeTokenRequest revokeToken) {
			this.revokeToken = revokeToken;
			return this;
		}

		public Builder invalidateUserTokens(InvalidateUserTokensRequest invalidateUserTokens) {
			this.invalidateUserTokens = invalidateUserTokens;
			return this;
		}

		public Builder deviceRegister(DeviceRegisterRequest deviceRegister) {
			this.deviceRegister = deviceRegister;
			return this;
		}

		public Builder deviceUpdate(DeviceUpdateRequest deviceUpdate) {
			this.deviceUpdate = deviceUpdate;
			return this;
		}

		public Builder deviceRemove(DeviceRemoveRequest deviceRemove) {
			this.deviceRemove = deviceRemove;
			return this;
		}

		public Builder deviceList(DeviceListRequest deviceList) {
			this.deviceList = deviceList;
			return this;
		}

		public Builder deviceTopicList(DeviceTopicListRequest deviceTopicList) {
			this.deviceTopicList = deviceTopicList;
			return this;
		}

		public Builder deviceTopicUpdate(DeviceTopicUpdateRequest deviceTopicUpdate) {
			this.deviceTopicUpdate = deviceTopicUpdate;
			return this;
		}

		public Builder userTopicList(UserTopicListRequest userTopicList) {
			this.userTopicList = userTopicList;
			return this;
		}

		public Builder userTopicUpdate(UserTopicUpdateRequest userTopicUpdate) {
			this.userTopicUpdate = userTopicUpdate;
			return this;
		}

		public Builder sendPushNotification(SendPushNotificationRequest sendPushNotification) {
			this.sendPushNotification = sendPushNotification;
			return this;
		}

		public Builder updatePushStatus(UpdatePushStatusRequest updatePushStatus) {
			this.updatePushStatus = updatePushStatus;
			return this;
		}

		public Builder cancelPush(CancelPushRequest cancelPush) {
			this.cancelPush = cancelPush;
			return this;
		}

		public Builder mapPublish(MapPublishRequest mapPublish) {
			this.mapPublish = mapPublish;
			return this;
		}

		public Builder mapRemove(MapRemoveRequest mapRemove) {
			this.mapRemove = mapRemove;
			return this;
		}

		public Builder mapReadState(MapReadStateRequest mapReadState) {
			this.mapReadState = mapReadState;
			return this;
		}

		public Builder mapReadStream(MapReadStreamRequest mapReadStream) {
			this.mapReadStream = mapReadStream;
			return this;
		}

		public Builder mapStats(MapStatsRequest mapStats) {
			this.mapStats = mapStats;
			return this;
		}

		public Builder mapClear(MapClearRequest mapClear) {
			this.mapClear = mapClear;
			return this;
		}

		public Builder sharedPollPublish(SharedPollPublishRequest sharedPollPublish) {
			this.sharedPollPublish = sharedPollPublish;
			return this;
		}

		public Command build() {
			return new Command(this.publish, this.broadcast, this.subscribe, this.unsubscribe, this.disconnect,
					this.presence, this.presenceStats, this.history, this.historyRemove, this.info, this.rpc,
					this.refresh, this.channels, this.connections, this.updateUserStatus, this.getUserStatus,
					this.deleteUserStatus, this.blockUser, this.unblockUser, this.revokeToken,
					this.invalidateUserTokens, this.deviceRegister, this.deviceUpdate, this.deviceRemove,
					this.deviceList, this.deviceTopicList, this.deviceTopicUpdate, this.userTopicList,
					this.userTopicUpdate, this.sendPushNotification, this.updatePushStatus, this.cancelPush,
					this.mapPublish, this.mapRemove, this.mapReadState, this.mapReadStream, this.mapStats,
					this.mapClear, this.sharedPollPublish);
		}

	}
}
