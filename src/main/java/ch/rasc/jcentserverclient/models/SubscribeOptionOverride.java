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
 * Override for channel subscription options.
 */
@SuppressWarnings({ "hiding" })
public record SubscribeOptionOverride(@JsonProperty("presence") Boolean presence,
		@JsonProperty("join_leave") Boolean joinLeave, @JsonProperty("force_recovery") Boolean forceRecovery,
		@JsonProperty("force_positioning") Boolean forcePositioning,
		@JsonProperty("force_push_join_leave") Boolean forcePushJoinLeave) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Boolean presence;

		private Boolean joinLeave;

		private Boolean forceRecovery;

		private Boolean forcePositioning;

		private Boolean forcePushJoinLeave;

		public Builder presence(Boolean presence) {
			this.presence = presence;
			return this;
		}

		public Builder joinLeave(Boolean joinLeave) {
			this.joinLeave = joinLeave;
			return this;
		}

		public Builder forceRecovery(Boolean forceRecovery) {
			this.forceRecovery = forceRecovery;
			return this;
		}

		public Builder forcePositioning(Boolean forcePositioning) {
			this.forcePositioning = forcePositioning;
			return this;
		}

		public Builder forcePushJoinLeave(Boolean forcePushJoinLeave) {
			this.forcePushJoinLeave = forcePushJoinLeave;
			return this;
		}

		public SubscribeOptionOverride build() {
			return new SubscribeOptionOverride(this.presence, this.joinLeave, this.forceRecovery, this.forcePositioning,
					this.forcePushJoinLeave);
		}

	}
}
