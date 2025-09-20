package com.ollama.api.examples;

import com.ollama.api.OllamaClient;
import com.ollama.api.exception.OllamaException;
import com.ollama.api.util.ChatBuilder;

/**
 * Example demonstrating streaming chat functionality.
 *
 * <p>This example shows how to:
 *
 * <ul>
 *   <li>Create an Ollama client
 *   <li>Send a streaming chat message
 *   <li>Handle streaming responses in real-time
 * </ul>
 *
 * @since 1.0.0
 */
public class StreamingChatExample {

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
      // Create a streaming chat request
      var request =
          ChatBuilder.create("llama3.2")
              .addUserMessage("Write a short poem about artificial intelligence.")
              .stream(true)
              .build();

      System.out.println("Sending streaming chat request...");
      System.out.println("User: " + request.getMessages().get(0).getContent());
      System.out.println("Assistant: ");

      // Send the streaming chat request
      client.chatStream(
          request,
          response -> {
            // Handle each streaming chunk
            if (response.getMessage() != null && response.getMessage().getContent() != null) {
              System.out.print(response.getMessage().getContent());
              System.out.flush();
            }

            // Check if this is the final chunk
            if (Boolean.TRUE.equals(response.getDone())) {
              System.out.println("\n\nStream completed!");
              System.out.println("Model: " + response.getModel());
              if (response.getTotalDuration() != null) {
                System.out.println("Total Duration: " + response.getTotalDuration() + "ns");
              }
            }
          });

      // Wait a bit for the stream to complete
      Thread.sleep(1000);

    } catch (OllamaException e) {
      System.err.println("Error communicating with Ollama: " + e.getMessage());
      e.printStackTrace();
    } catch (InterruptedException e) {
      System.err.println("Thread interrupted: " + e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}