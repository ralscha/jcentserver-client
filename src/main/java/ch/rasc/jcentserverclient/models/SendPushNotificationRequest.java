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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public record SendPushNotificationRequest(@JsonProperty("recipient") PushRecipient recipient,
		@JsonProperty("notification") PushNotification notification, @JsonProperty("uid") String uid,
		@JsonProperty("send_at") Long sendAt, @JsonProperty("optimize_for_reliability") Boolean optimizeForReliability,
		@JsonProperty("limit_strategy") PushLimitStrategy limitStrategy,
		@JsonProperty("analytics_uid") String analyticsUid,
		@JsonProperty("localizations") Map<String, PushLocalization> localizations,
		@JsonProperty("use_templating") Boolean useTemplating, @JsonProperty("use_meta") Boolean useMeta) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private PushRecipient recipient;

		private PushNotification notification;

		private String uid;

		private Long sendAt;

		private Boolean optimizeForReliability;

		private PushLimitStrategy limitStrategy;

		private String analyticsUid;

		private Map<String, PushLocalization> localizations;

		private Boolean useTemplating;

		private Boolean useMeta;

		public Builder recipient(PushRecipient recipient) {
			this.recipient = recipient;
			return this;
		}

		public Builder notification(PushNotification notification) {
			this.notification = notification;
			return this;
		}

		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}

		public Builder sendAt(Long sendAt) {
			this.sendAt = sendAt;
			return this;
		}

		public Builder optimizeForReliability(Boolean optimizeForReliability) {
			this.optimizeForReliability = optimizeForReliability;
			return this;
		}

		public Builder limitStrategy(PushLimitStrategy limitStrategy) {
			this.limitStrategy = limitStrategy;
			return this;
		}

		public Builder analyticsUid(String analyticsUid) {
			this.analyticsUid = analyticsUid;
			return this;
		}

		public Builder localizations(Map<String, PushLocalization> localizations) {
			this.localizations = localizations;
			return this;
		}

		public Builder useTemplating(Boolean useTemplating) {
			this.useTemplating = useTemplating;
			return this;
		}

		public Builder useMeta(Boolean useMeta) {
			this.useMeta = useMeta;
			return this;
		}

		public SendPushNotificationRequest build() {
			return new SendPushNotificationRequest(this.recipient, this.notification, this.uid, this.sendAt,
					this.optimizeForReliability, this.limitStrategy, this.analyticsUid, this.localizations,
					this.useTemplating, this.useMeta);
		}

	}
}
