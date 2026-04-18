# Centrifugo Java Client

An unofficial Java client for the [Centrifugo](https://centrifugal.dev/) HTTP Server API.

## Features

- Full coverage for the major Centrifugo HTTP Server API categories
- Strongly typed request and response models
- Fluent builders for request creation
- OpenFeign-based HTTP client integration
- Configurable timeouts, retrying, logging, and transport
- Integration-tested against Centrifugo v6 with Testcontainers

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>ch.rasc</groupId>
    <artifactId>jcentserver-client</artifactId>
    <version>2.0.1-SNAPSHOT</version>
</dependency>
```

## Quick Start

```java
import java.util.Map;

import ch.rasc.jcentserverclient.CentrifugoServerApiClient;
import ch.rasc.jcentserverclient.models.PublishRequest;
import ch.rasc.jcentserverclient.models.PublishResponse;

CentrifugoServerApiClient client = CentrifugoServerApiClient.create(config -> config
    .apiKey("your-centrifugo-api-key")
    .baseUrl("http://localhost:8000/api"));

PublishRequest request = PublishRequest.builder()
    .channel("chat:general")
    .data(Map.of("message", "Hello, World!"))
    .build();

PublishResponse response = client.publication().publish(request);

if (!response.hasError()) {
    System.out.println("Published successfully");
}
```

## API Categories

The client facade exposes the following API groups:

- `client.publication()` for `publish()` and `broadcast()`
- `client.connection()` for `subscribe()`, `unsubscribe()`, `disconnect()`, and `refresh()`
- `client.history()` for `history()` and `historyRemove()`
- `client.presence()` for `presence()` and `presenceStats()`
- `client.rpc()` for `rpc()`
- `client.stats()` for `channels()`, `connections()`, and `info()`
- `client.channels()` as a convenience alias for channel lookups
- `client.userBlock()` for `blockUser()` and `unblockUser()`
- `client.token()` for `revokeToken()` and `invalidateUserTokens()`
- `client.batch()` for batched commands

## Examples

### Publishing

```java
import java.util.Map;

import ch.rasc.jcentserverclient.models.PublishRequest;

PublishRequest request = PublishRequest.builder()
    .channel("notifications")
    .data(Map.of("text", "New notification", "urgency", "high"))
    .build();

client.publication().publish(request);
```

### Publishing with Stream Position Metadata

```java
PublishRequest request = PublishRequest.builder()
    .channel("docs:42")
    .data(Map.of("content", "next revision"))
    .version(8L)
    .versionEpoch("revision-series-1")
    .build();

client.publication().publish(request);
```

### Broadcasting

```java
import java.util.List;
import java.util.Map;

import ch.rasc.jcentserverclient.models.BroadcastRequest;

BroadcastRequest request = BroadcastRequest.builder()
    .channels(List.of("chat:room1", "chat:room2", "chat:room3"))
    .data(Map.of("announcement", "Server maintenance in 10 minutes"))
    .build();

client.publication().broadcast(request);
```

### Connection Management

```java
import ch.rasc.jcentserverclient.models.DisconnectRequest;
import ch.rasc.jcentserverclient.models.SubscribeOptionOverride;
import ch.rasc.jcentserverclient.models.SubscribeRequest;

SubscribeRequest subscribeRequest = SubscribeRequest.builder()
    .user("user123")
    .channel("personal:user123")
    .override(SubscribeOptionOverride.builder()
        .presence(true)
        .joinLeave(true)
        .build())
    .build();

client.connection().subscribe(subscribeRequest);

DisconnectRequest disconnectRequest = DisconnectRequest.builder()
    .user("user123")
    .build();

client.connection().disconnect(disconnectRequest);
```

### Custom RPC

```java
import java.util.Map;

import ch.rasc.jcentserverclient.models.RpcRequest;

RpcRequest request = RpcRequest.builder()
    .method("ping")
    .params(Map.of("value", "hello"))
    .build();

var response = client.rpc().rpc(request);

if (response.error() != null) {
    System.err.println("RPC failed: " + response.error().message());
}
```

### User Blocking and Tokens

```java
import ch.rasc.jcentserverclient.models.InvalidateUserTokensRequest;

client.userBlock().blockUser(builder -> builder
    .user("user123")
    .expireAt(System.currentTimeMillis() / 1000 + 3600));

client.token().revokeToken("token-uid");

client.token().invalidateUserTokens(InvalidateUserTokensRequest.builder()
    .user("user123")
    .channel("private:user123")
    .build());
```

### Server Info

```java
import ch.rasc.jcentserverclient.models.InfoRequest;

var response = client.stats().info(InfoRequest.builder().build());
System.out.println(response.result().nodes().size());
```

## Configuration

### Basic Configuration

```java
CentrifugoServerApiClient client = CentrifugoServerApiClient.create(config -> config
    .apiKey("your-api-key")
    .baseUrl("http://localhost:8000/api"));
```

### Advanced Configuration

```java
import ch.rasc.jcentserverclient.Configuration;

import feign.Logger;
import feign.http2client.Http2Client;

Configuration configuration = Configuration.builder()
    .apiKey("your-api-key")
    .baseUrl("https://your-centrifugo-server.com/api")
    .client(new Http2Client())
    .logLevel(Logger.Level.BASIC)
    .build();

CentrifugoServerApiClient client = CentrifugoServerApiClient.create(configuration);
```

## Error Handling

```java
import ch.rasc.jcentserverclient.ApiException;

try {
    var response = client.publication().publish(request);

    if (response.hasError()) {
        System.err.println(
            "Error: " + response.error().message() + " (Code: " + response.error().code() + ")");
    }
}
catch (ApiException e) {
    System.err.println("API Error: " + e.getMessage());
    System.err.println("Status: " + e.status());
}
```

## Requirements

- Java 17 or higher
- Centrifugo with the HTTP Server API enabled

## Authentication

The client authenticates with the `X-API-Key` header. Configure the same key on both sides.

Example Centrifugo config:

```json
{
  "http_api": {
    "key": "your-secret-api-key"
  }
}
```

## Testing

The integration test suite starts a `centrifugo/centrifugo:v6` container automatically via Testcontainers.

Run the full suite:

```bash
./mvnw test
```

Run a focused test class:

```bash
./mvnw -Dtest=RequestSerializationTest test
```

## Contributing

Contributions are welcome. Open an issue or submit a pull request.

## License

This project is licensed under the Apache License 2.0. See [LICENSE](LICENSE) for details.

## Links

- [Centrifugo Documentation](https://centrifugal.dev/)
- [Centrifugo HTTP API Reference](https://centrifugal.dev/docs/server/server_api)
- [OpenFeign](https://github.com/OpenFeign/feign)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Links

- [Centrifugo Documentation](https://centrifugal.dev/)
- [Centrifugo HTTP API Reference](https://centrifugal.dev/docs/server/server_api)
- [OpenFeign](https://github.com/OpenFeign/feign)" 
