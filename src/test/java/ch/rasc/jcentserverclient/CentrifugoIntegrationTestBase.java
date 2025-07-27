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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

/**
 * Base class for integration tests that require a running Centrifugo instance. Uses
 * TestContainers to start and manage a Centrifugo container.
 */
@Testcontainers
public abstract class CentrifugoIntegrationTestBase {

	protected static final String TEST_API_KEY = "test-api-key-for-integration-tests";

	protected static final int CENTRIFUGO_PORT = 8000;

	@Container
	protected static final GenericContainer<?> centrifugo = new GenericContainer<>("centrifugo/centrifugo:v6")
		.withExposedPorts(CENTRIFUGO_PORT)
		.withCopyFileToContainer(MountableFile.forClasspathResource("centrifugo-test-config.json"),
				"/centrifugo/config.json")
		.withCommand("centrifugo", "-c", "config.json");

	protected CentrifugoServerApiClient client;

	protected String baseUrl;

	@BeforeAll
	static void waitForCentrifugo() {
		// Wait for Centrifugo to be ready
		centrifugo.start();
	}

	@BeforeEach
	void setUp() {
		String host = centrifugo.getHost();
		Integer port = centrifugo.getMappedPort(CENTRIFUGO_PORT);
		this.baseUrl = String.format("http://%s:%d/api", host, port);

		this.client = CentrifugoServerApiClient.create(config -> config.apiKey(TEST_API_KEY).baseUrl(this.baseUrl));
	}

}
