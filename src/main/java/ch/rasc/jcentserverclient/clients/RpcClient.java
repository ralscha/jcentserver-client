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
package ch.rasc.jcentserverclient.clients;

import java.util.function.Function;

import ch.rasc.jcentserverclient.models.RpcRequest;
import ch.rasc.jcentserverclient.models.RpcResponse;
import feign.Headers;
import feign.RequestLine;

/**
 * Centrifugo RPC API client for custom server-side RPC methods.
 */
@Headers("Content-Type: application/json")
public interface RpcClient {

	@RequestLine("POST /rpc")
	RpcResponse rpc(RpcRequest request);

	default RpcResponse rpc(Function<RpcRequest.Builder, RpcRequest.Builder> fn) {
		return this.rpc(fn.apply(RpcRequest.builder()).build());
	}

}
