# Centrifugo Java Client

An unofficial Java client for the [Centrifugo](https://centrifugal.dev/) server HTTP API.

## Features

- **Full Centrifugo HTTP API Support**: Covers all major API endpoints including publication, connection management, presence, history, and more
- **Builder Pattern**: Fluent and intuitive API for creating requests
- **OpenFeign Integration**: Leverages OpenFeign for robust HTTP client functionality
- **Error Handling**: Comprehensive error handling with custom exceptions
- **Type Safety**: Strongly-typed request/response models
- **Configurable**: Flexible configuration options for different environments

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>ch.rasc</groupId>
    <artifactId>jcentserver-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start

```java
import ch.rasc.jcentserverclient.CentrifugoServerApiClient;
import ch.rasc.jcentserverclient.model.PublishRequest;
import ch.rasc.jcentserverclient.model.PublishResponse;

// Create client with builder pattern
CentrifugoServerApiClient client = CentrifugoServerApiClient.create(config -> 
    config.apiKey("your-centrifugo-api-key")
          .baseUrl("http://localhost:8000/api")
);

// Publish a message to a channel
Map<String, Object> data = Map.of("message", "Hello, World!");

PublishRequest request = PublishRequest.builder()
    .channel("chat:general")
    .data(data)
    .build();

PublishResponse response = client.publication.publish(request);

if (!response.hasError()) {
    System.out.println("Message published successfully!");
}
```

## API Categories

The client is organized into different categories based on Centrifugo's API structure:

### Publication API (`client.publication()`)
- `publish()` - Publish data into a channel
- `broadcast()` - Broadcast data to multiple channels

### Connection Management API (`client.connection()`)
- `subscribe()` - Subscribe user to a channel
- `unsubscribe()` - Unsubscribe user from a channel
- `disconnect()` - Disconnect user
- `refresh()` - Refresh user connection

### History API (`client.history()`)
- `history()` - Retrieve channel message history
- `historyRemove()` - Remove channel history

### Presence API (`client.presence()`)
- `presence()` - Get channel presence information (all connected clients)
- `presenceStats()` - Get channel presence statistics (counts only)

### Stats API (`client.stats()`)
- `channels()` - Get active channels information
- `connections()` - Get connections information  
- `info()` - Get server node information

### Channels API (`client.channels()`)
- `channels()` - Get active channels information (alias for stats.channels)

### User Block API (`client.userBlock()`)
- `blockUser()` - Block a user
- `unblockUser()` - Unblock a user

### Token API (`client.token()`)
- `revokeToken()` - Revoke individual tokens
- `invalidateUserTokens()` - Invalidate all user tokens

### Batch API (`client.batch()`)
- `batch()` - Send multiple commands in a single request


## Examples

### Publishing Messages

```java
// Simple publish
PublishRequest request = PublishRequest.builder()
    .channel("notifications")
    .data(Map.of("text", "New notification", "urgency", "high"))
    .build();

PublishResponse response = client.publication.publish(request);
```

### Broadcasting to Multiple Channels

```java
BroadcastRequest request = BroadcastRequest.builder()
    .channels(List.of("chat:room1", "chat:room2", "chat:room3"))
    .data(Map.of("announcement", "Server maintenance in 10 minutes"))
    .build();

BroadcastResponse response = client.publication.broadcast(request);
```

### Connection Management

```java
// Subscribe user to channel
SubscribeRequest subscribeRequest = SubscribeRequest.builder()
    .user("user123")
    .channel("personal:user123")
    .build();

client.connection.subscribe(subscribeRequest);

// Disconnect user
DisconnectRequest disconnectRequest = DisconnectRequest.builder()
    .user("user123")
    .build();

client.connection.disconnect(disconnectRequest);
```

## Configuration

### Basic Configuration

```java
CentrifugoServerApiClient client = CentrifugoServerApiClient.create(config -> 
    config.apiKey("your-api-key")
          .baseUrl("http://localhost:8000/api")
);
```

### Advanced Configuration

```java
Configuration configuration = Configuration.builder()
    .apiKey("your-api-key")
    .baseUrl("https://your-centrifugo-server.com/api")
    .client(new Http2Client()) // Custom HTTP client
    .logLevel(Logger.Level.BASIC) // Enable logging
    .build();

CentrifugoServerApiClient client = CentrifugoServerApiClient.create(configuration);
```

## Error Handling

The client provides comprehensive error handling:

```java
try {
    PublishResponse response = client.publication.publish(request);
    
    if (response.hasError()) {
        // Handle Centrifugo-level errors
        CentrifugoError error = response.getError();
        System.err.println("Error: " + error.getMessage() + " (Code: " + error.getCode() + ")");
    }
} catch (CentrifugoApiException e) {
    // Handle API exceptions
    System.err.println("API Error: " + e.getMessage());
    System.err.println("Error Code: " + e.getErrorCode());
}
```


## Requirements

- Java 17 or higher
- Centrifugo server with HTTP Server API enabled

## Authentication

The client uses the `X-API-Key` header for authentication. Make sure to:

1. Set `http_api.key` in your Centrifugo configuration
2. Use the same key when creating the client

Example Centrifugo config:
```json
{
  "http_api": {
    "key": "your-secret-api-key"
  }
}
```

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Links

- [Centrifugo Documentation](https://centrifugal.dev/)
- [Centrifugo HTTP API Reference](https://centrifugal.dev/docs/server/server_api)
- [OpenFeign](https://github.com/OpenFeign/feign)" 
