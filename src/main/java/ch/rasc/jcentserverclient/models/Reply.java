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
 * Reply object for batch operations.
 */
@SuppressWarnings({ "hiding" })
public record Reply(@JsonProperty("error") Error error, @JsonProperty("publish") PublishResult publish,
		@JsonProperty("broadcast") BroadcastResult broadcast, @JsonProperty("subscribe") SubscribeResult subscribe,
		@JsonProperty("unsubscribe") UnsubscribeResult unsubscribe,
		@JsonProperty("disconnect") DisconnectResult disconnect, @JsonProperty("presence") PresenceResult presence,
		@JsonProperty("presence_stats") PresenceStatsResult presenceStats,
		@JsonProperty("history") HistoryResult history,
		@JsonProperty("history_remove") HistoryRemoveResult historyRemove, @JsonProperty("info") InfoResult info,
		@JsonProperty("rpc") RpcResult rpc, @JsonProperty("refresh") RefreshResult refresh,
		@JsonProperty("channels") ChannelsResult channels, @JsonProperty("connections") ConnectionsResult connections,
		@JsonProperty("update_user_status") UpdateUserStatusResult updateUserStatus,
		@JsonProperty("get_user_status") GetUserStatusResult getUserStatus,
		@JsonProperty("delete_user_status") DeleteUserStatusResult deleteUserStatus,
		@JsonProperty("block_user") BlockUserResult blockUser,
		@JsonProperty("unblock_user") UnblockUserResult unblockUser,
		@JsonProperty("revoke_token") RevokeTokenResult revokeToken,
		@JsonProperty("invalidate_user_tokens") InvalidateUserTokensResult invalidateUserTokens,
		@JsonProperty("device_register") DeviceRegisterResult deviceRegister,
		@JsonProperty("device_update") DeviceUpdateResult deviceUpdate,
		@JsonProperty("device_remove") DeviceRemoveResult deviceRemove,
		@JsonProperty("device_list") DeviceListResult deviceList,
		@JsonProperty("device_topic_list") DeviceTopicListResult deviceTopicList,
		@JsonProperty("device_topic_update") DeviceTopicUpdateResult deviceTopicUpdate,
		@JsonProperty("user_topic_list") UserTopicListResult userTopicList,
		@JsonProperty("user_topic_update") UserTopicUpdateResult userTopicUpdate,
		@JsonProperty("send_push_notification") SendPushNotificationResult sendPushNotification,
		@JsonProperty("update_push_status") UpdatePushStatusResult updatePushStatus,
		@JsonProperty("cancel_push") CancelPushResult cancelPush,
		@JsonProperty("map_publish") MapPublishResult mapPublish, @JsonProperty("map_remove") MapRemoveResult mapRemove,
		@JsonProperty("map_read_state") MapReadStateResult mapReadState,
		@JsonProperty("map_read_stream") MapReadStreamResult mapReadStream,
		@JsonProperty("map_stats") MapStatsResult mapStats, @JsonProperty("map_clear") MapClearResult mapClear,
		@JsonProperty("shared_poll_publish") SharedPollPublishResult sharedPollPublish) {

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Check if the reply has an error.
	 * @return true if there is an error, false otherwise
	 */
	public boolean hasError() {
		return this.error != null;
	}

	public static class Builder {

		private Error error;

		private PublishResult publish;

		private BroadcastResult broadcast;

		private SubscribeResult subscribe;

		private UnsubscribeResult unsubscribe;

		private DisconnectResult disconnect;

		private PresenceResult presence;

		private PresenceStatsResult presenceStats;

		private HistoryResult history;

		private HistoryRemoveResult historyRemove;

		private InfoResult info;

		private RpcResult rpc;

		private RefreshResult refresh;

		private ChannelsResult channels;

		private ConnectionsResult connections;

		private UpdateUserStatusResult updateUserStatus;

		private GetUserStatusResult getUserStatus;

		private DeleteUserStatusResult deleteUserStatus;

		private BlockUserResult blockUser;

		private UnblockUserResult unblockUser;

		private RevokeTokenResult revokeToken;

		private InvalidateUserTokensResult invalidateUserTokens;

		private DeviceRegisterResult deviceRegister;

		private DeviceUpdateResult deviceUpdate;

		private DeviceRemoveResult deviceRemove;

		private DeviceListResult deviceList;

		private DeviceTopicListResult deviceTopicList;

		private DeviceTopicUpdateResult deviceTopicUpdate;

		private UserTopicListResult userTopicList;

		private UserTopicUpdateResult userTopicUpdate;

		private SendPushNotificationResult sendPushNotification;

		private UpdatePushStatusResult updatePushStatus;

		private CancelPushResult cancelPush;

		private MapPublishResult mapPublish;

		private MapRemoveResult mapRemove;

		private MapReadStateResult mapReadState;

		private MapReadStreamResult mapReadStream;

		private MapStatsResult mapStats;

		private MapClearResult mapClear;

		private SharedPollPublishResult sharedPollPublish;

		public Builder error(Error error) {
			this.error = error;
			return this;
		}

		public Builder publish(PublishResult publish) {
			this.publish = publish;
			return this;
		}

		public Builder broadcast(BroadcastResult broadcast) {
			this.broadcast = broadcast;
			return this;
		}

		public Builder subscribe(SubscribeResult subscribe) {
			this.subscribe = subscribe;
			return this;
		}

		public Builder unsubscribe(UnsubscribeResult unsubscribe) {
			this.unsubscribe = unsubscribe;
			return this;
		}

		public Builder disconnect(DisconnectResult disconnect) {
			this.disconnect = disconnect;
			return this;
		}

		public Builder presence(PresenceResult presence) {
			this.presence = presence;
			return this;
		}

		public Builder presenceStats(PresenceStatsResult presenceStats) {
			this.presenceStats = presenceStats;
			return this;
		}

		public Builder history(HistoryResult history) {
			this.history = history;
			return this;
		}

		public Builder historyRemove(HistoryRemoveResult historyRemove) {
			this.historyRemove = historyRemove;
			return this;
		}

		public Builder info(InfoResult info) {
			this.info = info;
			return this;
		}

		public Builder rpc(RpcResult rpc) {
			this.rpc = rpc;
			return this;
		}

		public Builder refresh(RefreshResult refresh) {
			this.refresh = refresh;
			return this;
		}

		public Builder channels(ChannelsResult channels) {
			this.channels = channels;
			return this;
		}

		public Builder connections(ConnectionsResult connections) {
			this.connections = connections;
			return this;
		}

		public Builder updateUserStatus(UpdateUserStatusResult updateUserStatus) {
			this.updateUserStatus = updateUserStatus;
			return this;
		}

		public Builder getUserStatus(GetUserStatusResult getUserStatus) {
			this.getUserStatus = getUserStatus;
			return this;
		}

		public Builder deleteUserStatus(DeleteUserStatusResult deleteUserStatus) {
			this.deleteUserStatus = deleteUserStatus;
			return this;
		}

		public Builder blockUser(BlockUserResult blockUser) {
			this.blockUser = blockUser;
			return this;
		}

		public Builder unblockUser(UnblockUserResult unblockUser) {
			this.unblockUser = unblockUser;
			return this;
		}

		public Builder revokeToken(RevokeTokenResult revokeToken) {
			this.revokeToken = revokeToken;
			return this;
		}

		public Builder invalidateUserTokens(InvalidateUserTokensResult invalidateUserTokens) {
			this.invalidateUserTokens = invalidateUserTokens;
			return this;
		}

		public Builder deviceRegister(DeviceRegisterResult deviceRegister) {
			this.deviceRegister = deviceRegister;
			return this;
		}

		public Builder deviceUpdate(DeviceUpdateResult deviceUpdate) {
			this.deviceUpdate = deviceUpdate;
			return this;
		}

		public Builder deviceRemove(DeviceRemoveResult deviceRemove) {
			this.deviceRemove = deviceRemove;
			return this;
		}

		public Builder deviceList(DeviceListResult deviceList) {
			this.deviceList = deviceList;
			return this;
		}

		public Builder deviceTopicList(DeviceTopicListResult deviceTopicList) {
			this.deviceTopicList = deviceTopicList;
			return this;
		}

		public Builder deviceTopicUpdate(DeviceTopicUpdateResult deviceTopicUpdate) {
			this.deviceTopicUpdate = deviceTopicUpdate;
			return this;
		}

		public Builder userTopicList(UserTopicListResult userTopicList) {
			this.userTopicList = userTopicList;
			return this;
		}

		public Builder userTopicUpdate(UserTopicUpdateResult userTopicUpdate) {
			this.userTopicUpdate = userTopicUpdate;
			return this;
		}

		public Builder sendPushNotification(SendPushNotificationResult sendPushNotification) {
			this.sendPushNotification = sendPushNotification;
			return this;
		}

		public Builder updatePushStatus(UpdatePushStatusResult updatePushStatus) {
			this.updatePushStatus = updatePushStatus;
			return this;
		}

		public Builder cancelPush(CancelPushResult cancelPush) {
			this.cancelPush = cancelPush;
			return this;
		}

		public Builder mapPublish(MapPublishResult mapPublish) {
			this.mapPublish = mapPublish;
			return this;
		}

		public Builder mapRemove(MapRemoveResult mapRemove) {
			this.mapRemove = mapRemove;
			return this;
		}

		public Builder mapReadState(MapReadStateResult mapReadState) {
			this.mapReadState = mapReadState;
			return this;
		}

		public Builder mapReadStream(MapReadStreamResult mapReadStream) {
			this.mapReadStream = mapReadStream;
			return this;
		}

		public Builder mapStats(MapStatsResult mapStats) {
			this.mapStats = mapStats;
			return this;
		}

		public Builder mapClear(MapClearResult mapClear) {
			this.mapClear = mapClear;
			return this;
		}

		public Builder sharedPollPublish(SharedPollPublishResult sharedPollPublish) {
			this.sharedPollPublish = sharedPollPublish;
			return this;
		}

		public Reply build() {
			return new Reply(this.error, this.publish, this.broadcast, this.subscribe, this.unsubscribe,
					this.disconnect, this.presence, this.presenceStats, this.history, this.historyRemove, this.info,
					this.rpc, this.refresh, this.channels, this.connections, this.updateUserStatus, this.getUserStatus,
					this.deleteUserStatus, this.blockUser, this.unblockUser, this.revokeToken,
					this.invalidateUserTokens, this.deviceRegister, this.deviceUpdate, this.deviceRemove,
					this.deviceList, this.deviceTopicList, this.deviceTopicUpdate, this.userTopicList,
					this.userTopicUpdate, this.sendPushNotification, this.updatePushStatus, this.cancelPush,
					this.mapPublish, this.mapRemove, this.mapReadState, this.mapReadStream, this.mapStats,
					this.mapClear, this.sharedPollPublish);
		}

	}
}
