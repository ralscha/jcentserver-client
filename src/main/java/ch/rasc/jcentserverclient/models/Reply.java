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
		@JsonProperty("refresh") RefreshResult refresh, @JsonProperty("channels") ChannelsResult channels) {

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

		private RefreshResult refresh;

		private ChannelsResult channels;

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

		public Builder refresh(RefreshResult refresh) {
			this.refresh = refresh;
			return this;
		}

		public Builder channels(ChannelsResult channels) {
			this.channels = channels;
			return this;
		}

		public Reply build() {
			return new Reply(this.error, this.publish, this.broadcast, this.subscribe, this.unsubscribe,
					this.disconnect, this.presence, this.presenceStats, this.history, this.historyRemove, this.info,
					this.refresh, this.channels);
		}

	}
}
