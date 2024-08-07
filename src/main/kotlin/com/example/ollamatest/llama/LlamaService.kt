package com.example.ollamatest.llama

import com.example.ollamatest.config.Assistant
import com.example.ollamatest.tools.BookingTool
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.ollama.OllamaChatModel.OllamaChatModelBuilder
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */
@Service
class LlamaService(private val bookingTool: BookingTool) {

    @Value("\${ollama.url}")
    private lateinit var ollamaUrl: String

    @Value("\${documents.path}")
    private lateinit var documentsPath: String


    private val assistant: Assistant by lazy { createAssistant() }

    fun assistant() = assistant

    fun getClassifierModel(): OllamaChatModel {
        return OllamaChatModelBuilder()
            .baseUrl(ollamaUrl)
//            .modelName("llama3.1")
            .modelName("mistral")
            .temperature(0.0)
            .timeout(Duration.ofMinutes(1))
            .build()
    }

    private fun getConversationalModel(): OllamaChatModel {
        return OllamaChatModelBuilder()
                .baseUrl(ollamaUrl)
//                .modelName("llama3.1")
                .modelName("mistral")
                .temperature(1.0)
                .timeout(Duration.ofMinutes(1))
                .build()
    }

    private fun createAssistant(): Assistant {
        return AiServices.builder(Assistant::class.java)
            .chatLanguageModel(getConversationalModel())
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .contentRetriever(createContentRetriever())
            .build()
    }


    private fun createContentRetriever(): ContentRetriever {
        val inMemoryEmbeddingStore: InMemoryEmbeddingStore<TextSegment> = InMemoryEmbeddingStore<TextSegment>()
        val documents = FileSystemDocumentLoader.loadDocuments(documentsPath)
        println("Documents loaded: ${documents.size}")
        EmbeddingStoreIngestor.ingest(documents, inMemoryEmbeddingStore)
        return EmbeddingStoreContentRetriever.from(inMemoryEmbeddingStore)
    }

}