package com.ollama.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response object for listing models.
 * 
 * @since 1.0.0
 */
public class ModelsResponse {
    
    @JsonProperty("models")
    private List<Model> models;
    
    /**
     * Default constructor for JSON deserialization.
     */
    public ModelsResponse() {
    }
    
    /**
     * Gets the list of models.
     * 
     * @return the models list
     */
    public List<Model> getModels() {
        return models;
    }
    
    /**
     * Sets the list of models.
     * 
     * @param models the models list
     */
    public void setModels(List<Model> models) {
        this.models = models;
    }
}