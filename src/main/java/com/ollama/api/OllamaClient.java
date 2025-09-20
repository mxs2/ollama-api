package com.ollama.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ollama.api.dto.ChatRequest;
import com.ollama.api.dto.ChatResponse;
import com.ollama.api.dto.Model;
import com.ollama.api.dto.ModelsResponse;
import com.ollama.api.exception.OllamaException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main client for interacting with the Ollama API.
 *
 * <p>This client provides a simple interface to interact with Ollama's language models, including
 * chat completions and model management.
 *
 * @since 1.0.0
 */
public class OllamaClient {

  private static final Logger logger = LoggerFactory.getLogger(OllamaClient.class);

  private static final String DEFAULT_BASE_URL = "http://localhost:11434";
  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

  private final String baseUrl;
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;
  /** Creates a new Ollama client with default settings. Uses localhost:11434 as the default Ollama server. */
  public OllamaClient() {
    this(DEFAULT_BASE_URL);
  }

  /**
   * Creates a new Ollama client with custom base URL.
   *
   * @param baseUrl the Ollama server base URL
   */
  public OllamaClient(String baseUrl) {
    this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    this.httpClient = HttpClient.newBuilder().connectTimeout(DEFAULT_TIMEOUT).build();
    this.objectMapper = new ObjectMapper();

    logger.info("Initialized Ollama client with URL: {}", this.baseUrl);
  }

  /**
   * Sends a chat completion request.
   *
   * @param request the chat request
   * @return the chat response
   * @throws OllamaException if the request fails
   */
  public ChatResponse chat(ChatRequest request) throws OllamaException {
    logger.debug("Sending chat request for model: {}", request.getModel());

    try {
      String jsonRequest = objectMapper.writeValueAsString(request);

      HttpRequest httpRequest =
          HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/api/chat"))
              .header("Content-Type", "application/json")
              .timeout(DEFAULT_TIMEOUT)
              .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
              .build();

      HttpResponse<String> response =
          httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new OllamaException(
            "Chat request failed: " + response.body(), response.statusCode());
      }

      return objectMapper.readValue(response.body(), ChatResponse.class);

    } catch (IOException | InterruptedException e) {
      throw new OllamaException("Failed to send chat request", e);
    }
  }

  /**
   * Sends a streaming chat completion request.
   *
   * @param request the chat request
   * @param callback function to handle each response chunk
   * @throws OllamaException if the request fails
   */
  public void chatStream(ChatRequest request, Consumer<ChatResponse> callback)
      throws OllamaException {
    logger.debug("Sending streaming chat request for model: {}", request.getModel());

    // Enable streaming for this request
    request.setStream(true);

    try {
      String jsonRequest = objectMapper.writeValueAsString(request);

      HttpRequest httpRequest =
          HttpRequest.newBuilder()
              .uri(URI.create(baseUrl + "/api/chat"))
              .header("Content-Type", "application/json")
              .timeout(Duration.ofMinutes(5))
              .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
            
            HttpResponse<String> response = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers
                    .ofString());
            
            if (response.statusCode() != 200) {
                throw new OllamaException(
                    "Streaming chat request failed: " + 
                    response.body(), response.statusCode());
            }
            
            // Parse each line as a separate JSON response
            String[] lines = response.body().split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    try {
                        ChatResponse chatResponse = objectMapper
                            .readValue(line, ChatResponse.class);
                        callback.accept(chatResponse);
                        
                        if (Boolean.TRUE.equals(chatResponse
                                .getDone())) {
                            break;
                        }
                    } catch (IOException e) {
                        logger.warn("Failed to parse response line: {}", 
                            line, e);
                    }
                }
            }
            
        } catch (IOException | InterruptedException e) {
            throw new OllamaException(
                "Failed to send streaming chat request", e);
        }
    }
    
    /**
     * Lists all available models.
     * 
     * @return list of available models
     * @throws OllamaException if the request fails
     */
    public List<Model> listModels() throws OllamaException {
        logger.debug("Listing available models");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/tags"))
                .header("Content-Type", "application/json")
                .timeout(DEFAULT_TIMEOUT)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers
                    .ofString());
            
            if (response.statusCode() != 200) {
                throw new OllamaException(
                    "Failed to list models: " + response.body(), 
                    response.statusCode());
            }
            
            ModelsResponse modelsResponse = objectMapper
                .readValue(response.body(), ModelsResponse.class);
            
            return Optional.ofNullable(modelsResponse.getModels())
                .orElse(List.of());
            
        } catch (IOException | InterruptedException e) {
            throw new OllamaException("Failed to list models", e);
        }
    }
    
    /**
     * Pulls a model from the Ollama registry.
     * 
     * @param modelName the name of the model to pull
     * @throws OllamaException if the request fails
     */
    public void pullModel(String modelName) throws OllamaException {
        logger.info("Pulling model: {}", modelName);
        
        try {
            String jsonRequest = objectMapper.writeValueAsString(
                new PullRequest(modelName));
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/pull"))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMinutes(30))
                .POST(HttpRequest.BodyPublishers
                    .ofString(jsonRequest))
                .build();
            
            HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers
                    .ofString());
            
            if (response.statusCode() != 200) {
                throw new OllamaException(
                    "Failed to pull model " + modelName + ": " + 
                    response.body(), response.statusCode());
            }
            
            logger.info("Successfully pulled model: {}", modelName);
            
        } catch (IOException | InterruptedException e) {
            throw new OllamaException(
                "Failed to pull model " + modelName, e);
        }
    }
    
    /**
     * Checks if the Ollama server is running.
     * 
     * @return true if the server is accessible
     */
    public boolean isServerRunning() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers
                    .ofString());
            
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            logger.debug("Server check failed", e);
            return false;
        }
    }
    
    /**
     * Internal class for pull requests.
     */
    private static class PullRequest {
        private final String name;
        
        public PullRequest(String name) {
            this.name = name;
        }
        @JsonProperty("name")
        public String getName() {
            return name;
        }
        }
    }