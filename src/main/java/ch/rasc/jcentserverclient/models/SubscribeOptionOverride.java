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
public record SubscribeOptionOverride(@JsonProperty("presence") BoolValue presence,
		@JsonProperty("join_leave") BoolValue joinLeave, @JsonProperty("force_recovery") BoolValue forceRecovery,
		@JsonProperty("force_positioning") BoolValue forcePositioning,
		@JsonProperty("force_push_join_leave") BoolValue forcePushJoinLeave) {

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private BoolValue presence;

		private BoolValue joinLeave;

		private BoolValue forceRecovery;

		private BoolValue forcePositioning;

		private BoolValue forcePushJoinLeave;

		public Builder presence(BoolValue presence) {
			this.presence = presence;
			return this;
		}

		public Builder joinLeave(BoolValue joinLeave) {
			this.joinLeave = joinLeave;
			return this;
		}

		public Builder forceRecovery(BoolValue forceRecovery) {
			this.forceRecovery = forceRecovery;
			return this;
		}

		public Builder forcePositioning(BoolValue forcePositioning) {
			this.forcePositioning = forcePositioning;
			return this;
		}

		public Builder forcePushJoinLeave(BoolValue forcePushJoinLeave) {
			this.forcePushJoinLeave = forcePushJoinLeave;
			return this;
		}

		public SubscribeOptionOverride build() {
			return new SubscribeOptionOverride(this.presence, this.joinLeave, this.forceRecovery, this.forcePositioning,
					this.forcePushJoinLeave);
		}

	}
}
