package com.ollama.api.util;

import com.ollama.api.dto.ChatRequest;
import com.ollama.api.dto.Message;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for building chat requests easily.
 * 
 * @since 1.0.0
 */
public class ChatBuilder {
    
    private final ChatRequest request;
    
    /**
     * Creates a new chat builder with the specified model.
     * 
     * @param model the model name to use
     */
    public ChatBuilder(String model) {
        this.request = new ChatRequest();
        this.request.setModel(model);
    }
    
    /**
     * Creates a new chat builder with the specified model.
     * 
     * @param model the model name to use
     * @return a new chat builder
     */
    public static ChatBuilder create(String model) {
        return new ChatBuilder(model);
    }
    
    /**
     * Adds messages to the chat request.
     * 
     * @param messages the messages to add
     * @return this builder for method chaining
     */
    public ChatBuilder messages(Message... messages) {
        this.request.setMessages(Arrays.asList(messages));
        return this;
    }
    
    /**
     * Adds a list of messages to the chat request.
     * 
     * @param messages the messages list
     * @return this builder for method chaining
     */
    public ChatBuilder messages(List<Message> messages) {
        this.request.setMessages(messages);
        return this;
    }
    
    /**
     * Adds a user message to the chat request.
     * 
     * @param content the user message content
     * @return this builder for method chaining
     */
    public ChatBuilder addUserMessage(String content) {
        var messages = this.request.getMessages();
        if (messages == null) {
            messages = new java.util.ArrayList<>();
            this.request.setMessages(messages);
        }
        messages.add(Message.user(content));
        return this;
    }
    
    /**
     * Adds a system message to the chat request.
     * 
     * @param content the system message content
     * @return this builder for method chaining
     */
    public ChatBuilder addSystemMessage(String content) {
        var messages = this.request.getMessages();
        if (messages == null) {
            messages = new java.util.ArrayList<>();
            this.request.setMessages(messages);
        }
        messages.add(Message.system(content));
        return this;
    }
    
    /**
     * Adds an assistant message to the chat request.
     * 
     * @param content the assistant message content
     * @return this builder for method chaining
     */
    public ChatBuilder addAssistantMessage(String content) {
        var messages = this.request.getMessages();
        if (messages == null) {
            messages = new java.util.ArrayList<>();
            this.request.setMessages(messages);
        }
        messages.add(Message.assistant(content));
        return this;
    }
    
    /**
     * Sets the temperature for response generation.
     * 
     * @param temperature the temperature value (0.0 to 2.0)
     * @return this builder for method chaining
     */
    public ChatBuilder temperature(double temperature) {
        this.request.setTemperature(temperature);
        return this;
    }
    
    /**
     * Sets the maximum tokens for the response.
     * 
     * @param maxTokens the maximum number of tokens
     * @return this builder for method chaining
     */
    public ChatBuilder maxTokens(int maxTokens) {
        this.request.setMaxTokens(maxTokens);
        return this;
    }
    
    /**
     * Enables or disables streaming for the response.
     * 
     * @param stream true to enable streaming
     * @return this builder for method chaining
     */
    public ChatBuilder stream(boolean stream) {
        this.request.setStream(stream);
        return this;
    }
    
    /**
     * Builds the chat request.
     * 
     * @return the configured chat request
     */
    public ChatRequest build() {
        return request;
    }
    
    /**
     * Creates a simple chat builder for a single user message.
     * 
     * @param model the model name
     * @param userMessage the user message content
     * @return a new chat builder
     */
    public static ChatBuilder simple(String model, 
            String userMessage) {
        return new ChatBuilder(model)
            .messages(Message.user(userMessage));
    }
    
    /**
     * Creates a chat builder with system and user messages.
     * 
     * @param model the model name
     * @param systemMessage the system message content
     * @param userMessage the user message content
     * @return a new chat builder
     */
    public static ChatBuilder withSystem(String model, 
            String systemMessage, String userMessage) {
        return new ChatBuilder(model)
            .messages(
                Message.system(systemMessage),
                Message.user(userMessage)
            );
    }
}