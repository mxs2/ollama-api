package com.ollama.api.examples;

import com.ollama.api.OllamaClient;
import com.ollama.api.dto.Model;
import com.ollama.api.exception.OllamaException;
import java.util.List;

/**
 * Example demonstrating model management functionality.
 *
 * <p>This example shows how to:
 *
 * <ul>
 *   <li>List available models
 *   <li>Pull new models
 *   <li>Check model information
 * </ul>
 *
 * @since 1.0.0
 */
public class ModelManagementExample {

  public static void main(String[] args) {
    // Create Ollama client
    OllamaClient client = new OllamaClient();

    // Check if server is running
    if (!client.isServerRunning()) {
      System.err.println("Ollama server is not running!");
      System.err.println("Please start Ollama first: ollama serve");
      System.exit(1);
    }

    try {
      System.out.println("Model Management Example");
      System.out.println("========================");

      // List currently available models
      System.out.println("1. Listing available models:");
      List<Model> models = client.listModels();

      if (models == null || models.isEmpty()) {
        System.out.println("No models found. Consider pulling a model first.");
      } else {
        for (Model model : models) {
          System.out.println("- " + model.getName());
          System.out.println("  Size: " + formatBytes(model.getSize()));
          System.out.println("  Modified: " + model.getModifiedAt());
          System.out.println();
        }
      }

      // Check if llama3.2 model exists
      String modelName = "llama3.2";

      boolean modelExists = models.stream().anyMatch(m -> m.getName().equals(modelName));

      if (!modelExists) {
        // Pull a new model
        System.out.println("2. Pulling model: " + modelName);
        client.pullModel(modelName);
        System.out.println("\nModel pulled successfully.");
      } else {
        System.out.println("Model " + modelName + " already exists locally.");
      }

      // Show model recommendations
      System.out.println("\n4. Recommended models for different use cases:");
      System.out.println("- General chat: llama3.2, llama3.2");
      System.out.println("- Code generation: codellama");
      System.out.println("- Lightweight: llama3.2:mini");
      System.out.println("- Vision tasks: llava");

    } catch (OllamaException e) {
      System.err.println("Error managing models: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Format bytes to human readable format.
   *
   * @param bytes the number of bytes
   * @return formatted string
   */
  private static String formatBytes(Long bytes) {
    if (bytes == null) return "Unknown";

    if (bytes < 1024) return bytes + " B";
    int exp = (int) (Math.log(bytes) / Math.log(1024));
    String pre = "KMGTPE".charAt(exp - 1) + "";
    return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
  }
}