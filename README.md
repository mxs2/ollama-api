# Ollama API Java

<div align="center">
  <img src="https://img.shields.io/badge/Java-21+-orange?style=for-the-badge&logo=java&logoColor=white" alt="Java 21+">
  <img src="https://img.shields.io/badge/Maven-3.6+-blue?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="MIT License">
  <img src="https://img.shields.io/badge/Ollama-Compatible-purple?style=for-the-badge" alt="Ollama Compatible">
</div>

## Instalação

### Pré-requisitos

-  **Java 21** ou superior
-  **Maven 3.6+**
-  **Ollama** instalado e rodando

### Clonando o Projeto

```bash
git clone https://github.com/usuario/ollama-api.git
cd ollama-api
```

### Compilando

```bash
# Com Maven
mvn clean compile
```

## Início Rápido

### 1. Iniciando o Servidor Ollama

```bash
# Usar nosso script de gerenciamento
./scripts/ollama-native.sh start

# Ou manualmente
ollama serve
```

### 2. Baixando um Modelo

```bash
# Modelo leve para testes
./scripts/ollama-native.sh pull llama3.2

# Ou manualmente
ollama pull llama3.2
```

### 3. Primeiro Chat

```java
import com.ollama.api.OllamaClient;
import com.ollama.api.dto.ChatResponse;
import com.ollama.api.util.ChatBuilder;

public class MeuPrimeiroChat {
    public static void main(String[] args) {
        try {
            // Criar cliente
            OllamaClient client = new OllamaClient();
            
            // Criar request
            var request = ChatBuilder.create("llama3.2")
                .addUserMessage("Olá! Como você está?")
                .build();
            
            // Enviar e receber resposta
            ChatResponse response = client.chat(request);
            System.out.println(response.getMessage().getContent());
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
```

## Guia de Uso

### Chat Básico

```java
OllamaClient client = new OllamaClient();

var request = ChatBuilder.create("llama3.2")
    .addUserMessage("Escreva um haiku sobre programação")
    .temperature(0.7)
    .maxTokens(100)
    .build();

ChatResponse response = client.chat(request);
System.out.println(response.getMessage().getContent());
```

### Chat com Streaming

```java
var request = ChatBuilder.create("llama3.2")
    .addUserMessage("Conte-me uma história")
    .stream(true)
    .build();

client.chatStream(request, response -> {
    if (response.getMessage() != null) {
        System.out.print(response.getMessage().getContent());
    }
    
    if (Boolean.TRUE.equals(response.getDone())) {
        System.out.println("\nConcluído!");
    }
});
```

### Conversação com Histórico

```java
List<Message> conversa = new ArrayList<>();
conversa.add(Message.system("Você é um assistente prestativo"));
conversa.add(Message.user("Olá!"));
conversa.add(Message.assistant("Oi! Como posso ajudar?"));
conversa.add(Message.user("Qual é a capital do Brasil?"));

ChatRequest request = new ChatRequest("llama3.2", conversa);
ChatResponse response = client.chat(request);
```

### Gerenciamento de Modelos

```java
OllamaClient client = new OllamaClient();

// Listar modelos disponíveis
List<Model> models = client.listModels();
models.forEach(model -> 
    System.out.println(model.getName() + " - " + 
                      formatSize(model.getSize()))
);

// Baixar novo modelo
client.pullModel("llama3.2");

// Verificar se modelo está disponível
ModelManager manager = new ModelManager(client);
boolean disponivel = manager.isModelAvailable("llama3.2");
```

## Exemplos Práticos

O projeto inclui vários exemplos prontos para uso:

### Executando os Exemplos
Usando Maven:

```bash
# Compilar o projeto
mvn compile

# Executar exemplos
mvn exec:java -Dexec.mainClass="com.ollama.api.examples.SimpleChatExample"
mvn exec:java -Dexec.mainClass="com.ollama.api.examples.StreamingChatExample"
```

## Requisitos

- Java 21 ou superior
- Ollama instalado e executando

## Colaboradores 
- [Débora Buriti](https://github.com/Debburiti)
- [Mateus Xavier](https://github.com/mxs2)
- [Mirella Santana](https://github.com/mihebs)
- [Myllena Navarro](https://github.com/Myllena-navarro)

## Contribuição

Contribuições são bem-vindas! Por favor, abra uma issue ou envie um pull request.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).