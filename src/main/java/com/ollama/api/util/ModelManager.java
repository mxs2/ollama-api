package com.ollama.api.util;

import com.ollama.api.OllamaClient;
import com.ollama.api.dto.Model;
import com.ollama.api.exception.OllamaException;
import java.util.List;

/**
 * Utility class for managing Ollama models.
 *
 * @since 1.0.0
 */
public class ModelManager {

  private final OllamaClient client;

  /**
   * Creates a new model manager.
   *
   * @param client the Ollama client
   */
  public ModelManager(OllamaClient client) {
    this.client = client;
  }

  /**
   * Checks if a model is available locally.
   *
   * @param modelName the model name to check
   * @return true if the model is available
   * @throws OllamaException if there's an error checking models
   */
  public boolean isModelAvailable(String modelName) throws OllamaException {
    List<Model> models = client.listModels();
    return models.stream().anyMatch(model -> model.getName().equals(modelName));
  }

  /**
   * Gets the size of a model if available.
   *
   * @param modelName the model name
   * @return the model size in bytes, or null if not found
   * @throws OllamaException if there's an error checking models
   */
  public Long getModelSize(String modelName) throws OllamaException {
    List<Model> models = client.listModels();
    return models.stream()
        .filter(model -> model.getName().equals(modelName))
        .map(Model::getSize)
        .findFirst()
        .orElse(null);
  }

  /**
   * Gets model information if available.
   *
   * @param modelName the model name
   * @return the model information, or null if not found
   * @throws OllamaException if there's an error checking models
   */
  public Model getModelInfo(String modelName) throws OllamaException {
    List<Model> models = client.listModels();
    return models.stream()
        .filter(model -> model.getName().equals(modelName))
        .findFirst()
        .orElse(null);
  }

  /**
   * Pulls a model if it's not already available.
   *
   * @param modelName the model name to pull
   * @return true if the model was pulled or already available
   * @throws OllamaException if there's an error pulling the model
   */
  public boolean ensureModelAvailable(String modelName) throws OllamaException {
    if (isModelAvailable(modelName)) {
      return true;
    }

    client.pullModel(modelName);
    return isModelAvailable(modelName);
  }

  /**
   * Gets a list of recommended models for different use cases.
   *
   * @return a string with model recommendations
   */
  public static String getModelRecommendations() {
    return """
        Model Recommendations:
        - General chat: llama3.2, llama3.2
        - Code generation: codellama, starcoder
        - Lightweight: llama3.2:mini, tinyllama
        - Vision tasks: llava, llama3.2
        - Math/reasoning: dolphin-mixtral
        """;
  }
}