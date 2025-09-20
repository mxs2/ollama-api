package com.ollama.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a message in a chat conversation.
 * 
 * @since 1.0.0
 */
public class Message {
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("content")
    private String content;
    
    /**
     * Default constructor for JSON deserialization.
     */
    public Message() {
    }
    
    /**
     * Creates a new message with role and content.
     * 
     * @param role the message role (user, assistant, system)
     * @param content the message content
     */
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
    
    /**
     * Gets the message role.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }
    
    /**
     * Sets the message role.
     * 
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * Gets the message content.
     * 
     * @return the content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Sets the message content.
     * 
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Creates a user message.
     * 
     * @param content the message content
     * @return a new user message
     */
    public static Message user(String content) {
        return new Message("user", content);
    }
    
    /**
     * Creates an assistant message.
     * 
     * @param content the message content
     * @return a new assistant message
     */
    public static Message assistant(String content) {
        return new Message("assistant", content);
    }
    
    /**
     * Creates a system message.
     * 
     * @param content the message content
     * @return a new system message
     */
    public static Message system(String content) {
        return new Message("system", content);
    }
    
    @Override
    public String toString() {
        return "Message{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}