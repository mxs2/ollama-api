# Contributing to Ollama API Java Client

Thank you for your interest in contributing to the Ollama API Java Client! We welcome contributions from the community.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/your-username/ollama-api.git
   cd ollama-api
   ```
3. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## Development Setup

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Ollama server for testing

### Building the Project

```bash
mvn clean compile
```

### Running Examples

```bash
# Make sure Ollama is running first
ollama serve

# Pull a model for testing
ollama pull llama3.2

# Run an example
mvn exec:java -Dexec.mainClass="com.ollama.api.examples.SimpleChatExample"
```

## Code Style

This project follows **Google Java Style** guidelines:

- Use 2 spaces for indentation
- Line length limit: 100 characters
- Import organization: java.* first, then others alphabetically
- Javadoc for all public methods and classes

### Formatting

The project includes Google Java Format tooling. Before committing, ensure your code is properly formatted.

## Making Changes

### 1. Code Changes

- Write clear, concise code
- Add Javadoc comments for public APIs
- Follow existing patterns and conventions
- Ensure backward compatibility when possible

### 2. Testing

- Test your changes with a running Ollama server
- Verify examples still work
- Test with different models if possible

### 3. Documentation

- Update README.md if adding new features
- Add examples for new functionality
- Update Javadoc comments

## Submitting Changes

### 1. Commit Guidelines

Use clear, descriptive commit messages:

```
feat: add streaming chat support
fix: handle connection timeout properly
docs: update README examples
refactor: simplify error handling
```

### 2. Pull Request Process

1. **Push your changes** to your fork
2. **Create a Pull Request** against the main branch
3. **Describe your changes** clearly in the PR description
4. **Reference any related issues**

### 3. Pull Request Requirements

- [ ] Code compiles without errors
- [ ] Examples run successfully
- [ ] Documentation is updated
- [ ] Code follows style guidelines
- [ ] No breaking changes (or clearly documented)

## Types of Contributions

### Bug Fixes
- Clear description of the bug
- Steps to reproduce
- Proposed solution

### New Features
- Description of the feature
- Use case and rationale
- Implementation plan
- Backward compatibility considerations

### Documentation
- Improvements to README
- Code examples
- API documentation
- Comment improvements

### Performance Improvements
- Benchmarking data
- Description of the optimization
- Impact analysis

## API Design Guidelines

### DTOs (Data Transfer Objects)
- Use Jackson annotations for JSON mapping
- Provide both constructors and builders
- Include proper Javadoc
- Follow immutable patterns where possible

### Client Methods
- Clear method names
- Comprehensive error handling
- Consistent parameter patterns
- Proper exception documentation

### Builder Patterns
- Fluent interface design
- Method chaining support
- Sensible defaults
- Validation where appropriate

## Issue Reporting

When reporting issues:

1. **Check existing issues** first
2. **Use issue templates** when available
3. **Provide minimal reproduction** code
4. **Include environment details**:
   - Java version
   - Ollama version
   - Operating system
   - Model being used

## Questions and Support

- 💬 **Discussions**: Use GitHub Discussions for questions
- 🐛 **Bugs**: Create GitHub Issues
- 📧 **Security**: Email security issues privately

## Code of Conduct

Please be respectful and inclusive in all interactions. We want this to be a welcoming community for everyone.

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to Ollama API Java Client! 🎉