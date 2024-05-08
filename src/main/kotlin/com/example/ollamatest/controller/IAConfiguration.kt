package com.example.ollamatest.controller

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel.OllamaChatModelBuilder
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */
@Service
class IAConfiguration {

    private val ollamaChatModel = OllamaChatModelBuilder()
        .baseUrl("http://localhost:11434")
        .modelName("llama3")
        .timeout(Duration.ofMinutes(5))
        .build()

    fun getIa() = ollamaChatModel

    private var assistant: Assistant? = null

    fun getAssistant(): Assistant {
        if (assistant == null) {
            assistant = createAssistant()
        }
        return assistant!!
    }

    fun createAssistant(): Assistant {
        return AiServices.builder(Assistant::class.java)
            .chatLanguageModel(ollamaChatModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .contentRetriever(createContentRetriever())
            .build()
    }

    fun createContentRetriever(): ContentRetriever {
        val inMemoryEmbeddingStore: InMemoryEmbeddingStore<TextSegment> = InMemoryEmbeddingStore<TextSegment>()
        val document = FileSystemDocumentLoader.loadDocument("src/main/resources/kathia.txt")
        println("Document loaded: ${document.text()}")
        EmbeddingStoreIngestor.ingest(document, inMemoryEmbeddingStore)
        return EmbeddingStoreContentRetriever.from(inMemoryEmbeddingStore)
    }
}