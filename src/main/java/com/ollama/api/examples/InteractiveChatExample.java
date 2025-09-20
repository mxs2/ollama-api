package com.ollama.api.examples;

import com.ollama.api.OllamaClient;
import com.ollama.api.dto.ChatRequest;
import com.ollama.api.dto.ChatResponse;
import com.ollama.api.dto.Message;
import com.ollama.api.exception.OllamaException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive chat example demonstrating conversation flow.
 *
 * <p>This example shows how to:
 *
 * <ul>
 *   <li>Create an interactive chat session
 *   <li>Maintain conversation history
 *   <li>Handle user input and responses
 * </ul>
 *
 * @since 1.0.0
 */
public class InteractiveChatExample {

  public static void main(String[] args) {
    // Create Ollama client
    OllamaClient client = new OllamaClient();

    // Check if server is running
    if (!client.isServerRunning()) {
      System.err.println("Ollama server is not running!");
      System.err.println("Please start Ollama first: ollama serve");
      System.exit(1);
    }

    Scanner scanner = new Scanner(System.in);
    List<Message> conversationHistory = new ArrayList<>();

    System.out.println("Interactive Chat with Ollama (llama3.2)");
    System.out.println("Type 'quit', 'exit', or 'bye' to end the conversation");
    System.out.println("================================================");

    while (true) {
      // Get user input
      System.out.print("You: ");
      String userInput = scanner.nextLine().trim();

      // Check for exit commands
      if (userInput.equalsIgnoreCase("quit")
          || userInput.equalsIgnoreCase("exit")
          || userInput.equalsIgnoreCase("bye")) {
        System.out.println("Goodbye!");
        break;
      }

      if (userInput.isEmpty()) {
        continue;
      }

      try {
        // Add user message to conversation history
        conversationHistory.add(Message.user(userInput));

        // Create chat request with conversation history
        ChatRequest request = new ChatRequest("llama3.2", conversationHistory);

        // Send the chat request
        System.out.print("Assistant: ");
        ChatResponse response = client.chat(request);

        String assistantResponse = response.getMessage().getContent();
        System.out.println(assistantResponse);

        // Add assistant response to conversation history
        conversationHistory.add(Message.assistant(assistantResponse));

        System.out.println(); // Add blank line for readability

      } catch (OllamaException e) {
        System.err.println("Error: " + e.getMessage());
        // Remove the last user message if the request failed
        if (!conversationHistory.isEmpty()) {
          conversationHistory.remove(conversationHistory.size() - 1);
        }
      }
    }

    scanner.close();
  }
}