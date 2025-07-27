# Integration Test Suite for JCentServer Client

This directory contains comprehensive integration tests for the JCentServer Client library. These tests start a real Centrifugo instance using TestContainers and test all major client operations.

## Test Structure

### Base Test Class
- `CentrifugoIntegrationTestBase.java` - Base class that manages the Centrifugo container lifecycle

### Client Operation Tests
- `PublicationClientIntegrationTest.java` - Tests publishing and broadcasting messages
- `ChannelsClientIntegrationTest.java` - Tests channel listing and management
- `HistoryClientIntegrationTest.java` - Tests message history retrieval
- `PresenceClientIntegrationTest.java` - Tests presence information
- `StatsClientIntegrationTest.java` - Tests server statistics
- `ConnectionClientIntegrationTest.java` - Tests connection management operations
- `BatchClientIntegrationTest.java` - Tests batch operations
- `UserStatusClientIntegrationTest.java` - Tests user status management

### Comprehensive Tests
- `CentrifugoComprehensiveIntegrationTest.java` - End-to-end workflow tests
- `ErrorHandlingIntegrationTest.java` - Error conditions and edge cases

## Prerequisites

### Required Software
- Docker (for TestContainers)
- Java 17+
- Maven 3.6+

### Test Dependencies
The tests use the following key dependencies:
- JUnit 5 - Testing framework
- TestContainers - Container management for integration tests
- AssertJ - Fluent assertions
- Logback - Logging (test scope)

## Running the Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=PublicationClientIntegrationTest
```

### Run Tests with Debug Logging
```bash
mvn test -Dlogback.configurationFile=src/test/resources/logback-test.xml
```

## Test Configuration

### Centrifugo Configuration
The tests use a custom Centrifugo configuration (`src/test/resources/centrifugo-test-config.json`) with:
- Test API key: `test-api-key-for-integration-tests`
- History enabled with 10 message limit
- Presence tracking enabled
- Debug mode enabled

### Container Configuration
- Uses `centrifugo/centrifugo:v6` Docker image
- Exposes port 8000 (mapped to random host port)
- Starts automatically before tests run
- Stops automatically after tests complete

## Test Coverage

The integration tests cover:

### Core Functionality
- ✅ Message publishing to single channels
- ✅ Broadcasting to multiple channels
- ✅ Channel listing and filtering
- ✅ Message history retrieval
- ✅ Presence information and statistics
- ✅ Server statistics
- ✅ Batch operations
- ✅ User status management
- ✅ Connection management (subscribe, unsubscribe, disconnect, refresh)

### Advanced Features
- ✅ Message tagging and metadata
- ✅ Idempotency keys
- ✅ History pagination and filtering
- ✅ Parallel batch processing
- ✅ Complex user state management

### Error Handling
- ✅ Invalid API keys
- ✅ Malformed requests
- ✅ Edge cases (empty data, long names, special characters)
- ✅ Unicode character handling
- ✅ Client recovery after errors

### Performance & Reliability
- ✅ High-frequency publishing
- ✅ Concurrent operations
- ✅ Large message payloads
- ✅ Multiple simultaneous clients

## Test Data

Tests use dynamically generated test data with timestamps to avoid conflicts:
- Channel names include timestamps: `test-channel-{timestamp}`
- User IDs include timestamps: `test-user-{timestamp}`
- Messages include identification data for verification

## Troubleshooting

### Docker Issues
If TestContainers fails to start:
1. Ensure Docker is running
2. Check Docker permissions
3. Verify Docker image can be pulled: `docker pull centrifugo/centrifugo:v6`

### Port Conflicts
TestContainers automatically handles port mapping, but if you see port conflicts:
1. Stop other applications using port 8000
2. Restart Docker
3. Re-run tests

### Test Failures
If tests fail intermittently:
1. Check Docker resources (memory/CPU)
2. Increase test timeouts if needed
3. Check Centrifugo logs in test output

### Logging
Increase logging verbosity by modifying `src/test/resources/logback-test.xml`:
```xml
<logger name="ch.rasc.jcentserverclient" level="TRACE"/>
<logger name="org.testcontainers" level="DEBUG"/>
```

## Contributing

When adding new tests:
1. Extend `CentrifugoIntegrationTestBase` for container management
2. Use descriptive test names with `@DisplayName`
3. Follow the Given-When-Then pattern
4. Include both happy path and error cases
5. Use unique test data to avoid conflicts
6. Add appropriate assertions for all response fields

## Performance Considerations

- Tests run sequentially by default to avoid resource conflicts
- Container startup happens once per test class
- Use small delays between rapid operations to avoid overwhelming Centrifugo
- Clean up test data when necessary (though isolation usually handles this)

## Security Notes

- Tests use hardcoded test credentials that should never be used in production
- The test configuration disables admin interface for security
- All test data is ephemeral and cleaned up automatically
