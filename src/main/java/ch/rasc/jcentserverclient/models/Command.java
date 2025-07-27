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
 * Command object for batch operations.
 */
public record Command(@JsonProperty("publish") PublishRequest publish,
		@JsonProperty("broadcast") BroadcastRequest broadcast, @JsonProperty("subscribe") SubscribeRequest subscribe,
		@JsonProperty("unsubscribe") UnsubscribeRequest unsubscribe,
		@JsonProperty("disconnect") DisconnectRequest disconnect, @JsonProperty("presence") PresenceRequest presence,
		@JsonProperty("presence_stats") PresenceStatsRequest presenceStats,
		@JsonProperty("history") HistoryRequest history,
		@JsonProperty("history_remove") HistoryRemoveRequest historyRemove, @JsonProperty("info") InfoRequest info,
		@JsonProperty("refresh") RefreshRequest refresh, @JsonProperty("channels") ChannelsRequest channels) {

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

		private RefreshRequest refresh;

		private ChannelsRequest channels;

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

		public Builder refresh(RefreshRequest refresh) {
			this.refresh = refresh;
			return this;
		}

		public Builder channels(ChannelsRequest channels) {
			this.channels = channels;
			return this;
		}

		public Command build() {
			return new Command(this.publish, this.broadcast, this.subscribe, this.unsubscribe, this.disconnect,
					this.presence, this.presenceStats, this.history, this.historyRemove, this.info, this.refresh,
					this.channels);
		}

	}
}
