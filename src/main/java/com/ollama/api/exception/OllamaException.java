package com.ollama.api.exception;

/**
 * Base exception for all Ollama API related errors.
 * 
 * @since 1.0.0
 */
public class OllamaException extends Exception {
    
    private final int statusCode;
    
    /**
     * Creates a new OllamaException with a message.
     * 
     * @param message the error message
     */
    public OllamaException(String message) {
        super(message);
        this.statusCode = -1;
    }
    
    /**
     * Creates a new OllamaException with a message and cause.
     * 
     * @param message the error message
     * @param cause the underlying cause
     */
    public OllamaException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
    }
    
    /**
     * Creates a new OllamaException with message and HTTP status code.
     * 
     * @param message the error message
     * @param statusCode the HTTP status code
     */
    public OllamaException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    /**
     * Gets the HTTP status code if available.
     * 
     * @return the status code, or -1 if not available
     */
    public int getStatusCode() {
        return statusCode;
    }
}