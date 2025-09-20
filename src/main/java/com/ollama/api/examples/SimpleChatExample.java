package com.ollama.api.examples;

import com.ollama.api.OllamaClient;
import com.ollama.api.dto.ChatResponse;
import com.ollama.api.exception.OllamaException;
import com.ollama.api.util.ChatBuilder;

/**
 * Simple example demonstrating basic chat functionality.
 *
 * <p>This example shows how to:
 *
 * <ul>
 *   <li>Create an Ollama client
 *   <li>Send a simple chat message
 *   <li>Handle the response
 * </ul>
 *
 * @since 1.0.0
 */
public class SimpleChatExample {

  public static void main(String[] args) {
    // Create Ollama client (uses localhost:11434 by default)
    OllamaClient client = new OllamaClient();

    // Check if server is running
    if (!client.isServerRunning()) {
      System.err.println("Ollama server is not running!");
      System.err.println("Please start Ollama first: ollama serve");
      System.exit(1);
    }

    try {
      // Create a simple chat request using ChatBuilder
      var request =
          ChatBuilder.create("llama3.2")
              .addUserMessage("Hello! Can you explain what you are in one sentence?")
              .stream(false)  // Explicitly disable streaming for complete response
              .build();

      System.out.println("Sending chat request...");
      System.out.println("User: " + request.getMessages().get(0).getContent());

      // Send the chat request
      ChatResponse response = client.chat(request);

      // Print the response
      System.out.println("Assistant: " + response.getMessage().getContent());

      // Print some metadata
      System.out.println("\nMetadata:");
      System.out.println("Model: " + response.getModel());
      System.out.println("Done: " + response.getDone());
      if (response.getTotalDuration() != null) {
        System.out.println("Total Duration: " + response.getTotalDuration() + "ns");
      }

    } catch (OllamaException e) {
      System.err.println("Error communicating with Ollama: " + e.getMessage());
      e.printStackTrace();
    }
  }
}