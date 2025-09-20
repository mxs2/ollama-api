---
applyTo: 'java'
---

# Java Backend Development Guidelines

## Project Context
This is a Java backend project for the Ollama API client library. Follow these guidelines when generating code, answering questions, or reviewing changes.

## Code Style & Standards

### General Java Conventions
- Use Java 21 features where appropriate
- Follow Oracle's Java Code Conventions
- Use meaningful variable and method names
- Prefer composition over inheritance
- Write self-documenting code with clear intent

### Naming Conventions
- Classes: PascalCase (e.g., `OllamaClient`)
- Methods/Variables: camelCase (e.g., `sendRequest`)
- Constants: UPPER_SNAKE_CASE (e.g., `DEFAULT_TIMEOUT`)
- Packages: lowercase with dots (e.g., `com.ollama.api`)

### Code Structure
- Maximum line length: 60 characters
- Use 4 spaces for indentation (no tabs)
- Group imports: java.*, javax.*, third-party, project
- Order class members: static fields, instance fields, constructors, methods
- Use blank lines to separate logical sections

## Architecture Patterns

### Dependency Injection
- Use constructor injection over field injection
- Prefer immutable objects where possible
- Use builder pattern for complex object creation

### Error Handling
- Use specific exception types
- Don't catch generic Exception unless necessary
- Log errors with appropriate level and context
- Use Optional for nullable returns instead of null

### API Design
- Use RESTful principles for HTTP APIs
- Return appropriate HTTP status codes
- Use DTOs for data transfer between layers
- Validate input parameters

## Testing Guidelines

### Unit Tests
- Use JUnit 5 as the testing framework
- Use Mockito for mocking dependencies
- Follow AAA pattern: Arrange, Act, Assert
- Test method naming: `should_ReturnExpectedResult_When_ConditionMet`

### Test Coverage
- Aim for 80%+ code coverage
- Focus on testing business logic
- Test edge cases and error conditions
- Use parameterized tests for multiple scenarios

## Documentation

### JavaDoc
- Document all public APIs
- Include parameter descriptions and return values
- Add @since tags for version tracking
- Use @throws for documented exceptions

### README
- Include usage examples
- Document configuration options
- Provide troubleshooting guide
- Keep dependencies up to date

## Performance Considerations

### Best Practices
- Use StringBuilder for string concatenation in loops
- Prefer ArrayList over Vector
- Use enhanced for loops where possible
- Close resources properly (try-with-resources)

### Concurrency
- Use thread-safe collections when needed
- Prefer immutable objects in concurrent contexts
- Use CompletableFuture for asynchronous operations
- Avoid synchronized blocks where possible

## Security Guidelines

### Input Validation
- Validate all user inputs
- Sanitize data before processing
- Use prepared statements for database queries
- Implement proper authentication/authorization

### Dependencies
- Keep dependencies up to date
- Use dependency vulnerability scanning
- Minimize dependency footprint
- Prefer well-maintained libraries

## Build & Deployment

### Maven/Gradle
- Use semantic versioning
- Keep build files clean and organized
- Use profiles for different environments
- Include integration tests in build pipeline

### CI/CD
- Run tests on every commit
- Use static code analysis tools
- Automate dependency updates
- Deploy to staging before production