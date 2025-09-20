package com.ollama.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Request object for chat completion API.
 *
 * @since 1.0.0
 */
public class ChatRequest {

  @JsonProperty("model")
  private String model;

  @JsonProperty("messages")
  private List<Message> messages;

  @JsonProperty("stream")
  private Boolean stream;

  @JsonProperty("temperature")
  private Double temperature;

  @JsonProperty("max_tokens")
  private Integer maxTokens;

  /** Default constructor for JSON deserialization. */
  public ChatRequest() {}

  /**
   * Creates a new chat request.
   *
   * @param model the model to use
   * @param messages the list of messages
   */
  public ChatRequest(String model, List<Message> messages) {
    this.model = model;
    this.messages = messages;
  }

  /**
   * Gets the model name.
   *
   * @return the model name
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets the model name.
   *
   * @param model the model name
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * Gets the messages.
   *
   * @return the messages
   */
  public List<Message> getMessages() {
    return messages;
  }

  /**
   * Sets the messages.
   *
   * @param messages the messages
   */
  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  /**
   * Gets the stream setting.
   *
   * @return true if streaming is enabled
   */
  public Boolean getStream() {
    return stream;
  }

  /**
   * Sets the stream setting.
   *
   * @param stream true to enable streaming
   */
  public void setStream(Boolean stream) {
    this.stream = stream;
  }

  /**
   * Gets the temperature setting.
   *
   * @return the temperature
   */
  public Double getTemperature() {
    return temperature;
  }

  /**
   * Sets the temperature setting.
   *
   * @param temperature the temperature (0.0 to 1.0)
   */
  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  /**
   * Gets the max tokens setting.
   *
   * @return the max tokens
   */
  public Integer getMaxTokens() {
    return maxTokens;
  }

  /**
   * Sets the max tokens setting.
   *
   * @param maxTokens the maximum number of tokens
   */
  public void setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
  }

  /**
   * Creates a builder for this chat request.
   *
   * @param model the model to use
   * @return a new builder
   */
  public static Builder builder(String model) {
    return new Builder(model);
  }

  /** Builder class for ChatRequest. */
  public static class Builder {
    private final ChatRequest request;

    /**
     * Creates a new builder.
     *
     * @param model the model to use
     */
    public Builder(String model) {
      this.request = new ChatRequest();
      this.request.setModel(model);
    }

    /**
     * Sets the messages.
     *
     * @param messages the messages
     * @return this builder
     */
    public Builder messages(List<Message> messages) {
      this.request.setMessages(messages);
      return this;
    }

    /**
     * Sets the stream setting.
     *
     * @param stream true to enable streaming
     * @return this builder
     */
    public Builder stream(Boolean stream) {
      this.request.setStream(stream);
      return this;
    }

    /**
     * Sets the temperature.
     *
     * @param temperature the temperature (0.0 to 1.0)
     * @return this builder
     */
    public Builder temperature(Double temperature) {
      this.request.setTemperature(temperature);
      return this;
    }

    /**
     * Sets the max tokens.
     *
     * @param maxTokens the maximum number of tokens
     * @return this builder
     */
    public Builder maxTokens(Integer maxTokens) {
      this.request.setMaxTokens(maxTokens);
      return this;
    }

    /**
     * Builds the chat request.
     *
     * @return the chat request
     */
    public ChatRequest build() {
      return request;
    }
  }
}