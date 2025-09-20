package com.ollama.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response object for chat completion API.
 * 
 * @since 1.0.0
 */
public class ChatResponse {
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("message")
    private Message message;
    
    @JsonProperty("done")
    private Boolean done;
    
    @JsonProperty("done_reason")
    private String doneReason;
    
    @JsonProperty("total_duration")
    private Long totalDuration;
    
    @JsonProperty("load_duration")
    private Long loadDuration;
    
    @JsonProperty("prompt_eval_count")
    private Integer promptEvalCount;
    
    @JsonProperty("prompt_eval_duration")
    private Long promptEvalDuration;
    
    @JsonProperty("eval_count")
    private Integer evalCount;
    
    @JsonProperty("eval_duration")
    private Long evalDuration;
    
    /**
     * Default constructor for JSON deserialization.
     */
    public ChatResponse() {
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
     * Gets the creation timestamp.
     * 
     * @return the created timestamp
     */
    public String getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Sets the creation timestamp.
     * 
     * @param createdAt the timestamp
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Gets the response message.
     * 
     * @return the message
     */
    public Message getMessage() {
        return message;
    }
    
    /**
     * Sets the response message.
     * 
     * @param message the message
     */
    public void setMessage(Message message) {
        this.message = message;
    }
    
    /**
     * Gets whether the response is complete.
     * 
     * @return true if done, false if streaming continues
     */
    public Boolean getDone() {
        return done;
    }
    
    /**
     * Sets the completion status.
     * 
     * @param done true if done
     */
    public void setDone(Boolean done) {
        this.done = done;
    }
    
    /**
     * Gets the reason for completion.
     * 
     * @return the completion reason (e.g., "stop", "length")
     */
    public String getDoneReason() {
        return doneReason;
    }
    
    /**
     * Sets the completion reason.
     * 
     * @param doneReason the completion reason
     */
    public void setDoneReason(String doneReason) {
        this.doneReason = doneReason;
    }
    
    /**
     * Gets the total duration in nanoseconds.
     * 
     * @return the total duration
     */
    public Long getTotalDuration() {
        return totalDuration;
    }
    
    /**
     * Sets the total duration.
     * 
     * @param totalDuration the duration in nanoseconds
     */
    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
    }
    
    /**
     * Gets the model load duration.
     * 
     * @return the load duration
     */
    public Long getLoadDuration() {
        return loadDuration;
    }
    
    /**
     * Sets the model load duration.
     * 
     * @param loadDuration the load duration
     */
    public void setLoadDuration(Long loadDuration) {
        this.loadDuration = loadDuration;
    }
    
    /**
     * Gets the prompt evaluation count.
     * 
     * @return the prompt eval count
     */
    public Integer getPromptEvalCount() {
        return promptEvalCount;
    }
    
    /**
     * Sets the prompt evaluation count.
     * 
     * @param promptEvalCount the count
     */
    public void setPromptEvalCount(Integer promptEvalCount) {
        this.promptEvalCount = promptEvalCount;
    }
    
    /**
     * Gets the prompt evaluation duration.
     * 
     * @return the prompt eval duration
     */
    public Long getPromptEvalDuration() {
        return promptEvalDuration;
    }
    
    /**
     * Sets the prompt evaluation duration.
     * 
     * @param promptEvalDuration the duration
     */
    public void setPromptEvalDuration(Long promptEvalDuration) {
        this.promptEvalDuration = promptEvalDuration;
    }
    
    /**
     * Gets the evaluation token count.
     * 
     * @return the eval count
     */
    public Integer getEvalCount() {
        return evalCount;
    }
    
    /**
     * Sets the evaluation token count.
     * 
     * @param evalCount the count
     */
    public void setEvalCount(Integer evalCount) {
        this.evalCount = evalCount;
    }
    
    /**
     * Gets the evaluation duration.
     * 
     * @return the eval duration
     */
    public Long getEvalDuration() {
        return evalDuration;
    }
    
    /**
     * Sets the evaluation duration.
     * 
     * @param evalDuration the duration
     */
    public void setEvalDuration(Long evalDuration) {
        this.evalDuration = evalDuration;
    }
}