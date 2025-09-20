package com.ollama.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a model in the Ollama system.
 * 
 * @since 1.0.0
 */
public class Model {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("modified_at")
    private String modifiedAt;
    
    @JsonProperty("size")
    private Long size;
    
    @JsonProperty("digest")
    private String digest;
    
    @JsonProperty("details")
    private ModelDetails details;
    
    /**
     * Default constructor for JSON deserialization.
     */
    public Model() {
    }
    
    /**
     * Gets the model name.
     * 
     * @return the model name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the model name.
     * 
     * @param name the model name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the model identifier.
     * 
     * @return the model identifier
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Sets the model identifier.
     * 
     * @param model the model identifier
     */
    public void setModel(String model) {
        this.model = model;
    }
    
    /**
     * Gets the last modified timestamp.
     * 
     * @return the modified timestamp
     */
    public String getModifiedAt() {
        return modifiedAt;
    }
    
    /**
     * Sets the last modified timestamp.
     * 
     * @param modifiedAt the timestamp
     */
    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    /**
     * Gets the model size in bytes.
     * 
     * @return the model size
     */
    public Long getSize() {
        return size;
    }
    
    /**
     * Sets the model size.
     * 
     * @param size the size in bytes
     */
    public void setSize(Long size) {
        this.size = size;
    }
    
    /**
     * Gets the model digest/hash.
     * 
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }
    
    /**
     * Sets the model digest.
     * 
     * @param digest the digest
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }
    
    /**
     * Gets the model details.
     * 
     * @return the model details
     */
    public ModelDetails getDetails() {
        return details;
    }
    
    /**
     * Sets the model details.
     * 
     * @param details the model details
     */
    public void setDetails(ModelDetails details) {
        this.details = details;
    }
    
    /**
     * Model details nested class.
     */
    public static class ModelDetails {
        
        @JsonProperty("parent_model")
        private String parentModel;
        
        @JsonProperty("format")
        private String format;
        
        @JsonProperty("family")
        private String family;
        
        @JsonProperty("families")
        private String[] families;
        
        @JsonProperty("parameter_size")
        private String parameterSize;
        
        @JsonProperty("quantization_level")
        private String quantizationLevel;
        
        /**
         * Gets the parent model.
         * 
         * @return the parent model
         */
        public String getParentModel() {
            return parentModel;
        }
        
        /**
         * Sets the parent model.
         * 
         * @param parentModel the parent model
         */
        public void setParentModel(String parentModel) {
            this.parentModel = parentModel;
        }
        
        /**
         * Gets the model format.
         * 
         * @return the format
         */
        public String getFormat() {
            return format;
        }
        
        /**
         * Sets the model format.
         * 
         * @param format the format
         */
        public void setFormat(String format) {
            this.format = format;
        }
        
        /**
         * Gets the model family.
         * 
         * @return the family
         */
        public String getFamily() {
            return family;
        }
        
        /**
         * Sets the model family.
         * 
         * @param family the family
         */
        public void setFamily(String family) {
            this.family = family;
        }
        
        /**
         * Gets the model families.
         * 
         * @return the families array
         */
        public String[] getFamilies() {
            return families;
        }
        
        /**
         * Sets the model families.
         * 
         * @param families the families array
         */
        public void setFamilies(String[] families) {
            this.families = families;
        }
        
        /**
         * Gets the parameter size.
         * 
         * @return the parameter size
         */
        public String getParameterSize() {
            return parameterSize;
        }
        
        /**
         * Sets the parameter size.
         * 
         * @param parameterSize the parameter size
         */
        public void setParameterSize(String parameterSize) {
            this.parameterSize = parameterSize;
        }
        
        /**
         * Gets the quantization level.
         * 
         * @return the quantization level
         */
        public String getQuantizationLevel() {
            return quantizationLevel;
        }
        
        /**
         * Sets the quantization level.
         * 
         * @param quantizationLevel the quantization level
         */
        public void setQuantizationLevel(String quantizationLevel) {
            this.quantizationLevel = quantizationLevel;
        }
    }
}