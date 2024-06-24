package com.example.ollamatest.openai

import com.example.ollamatest.config.Assistant
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel.OpenAiChatModelBuilder
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 24/06/2024
 */
@Service
class OpenAiService(private val chatLanguageModel: ChatLanguageModel) {

    @Value("\${documents.path}")
    private lateinit var documentsPath: String

    @Value("\${langchain4j.open-ai.chat-model.api-key}")
    private lateinit var apiKey: String

    private val assistant: Assistant by lazy { createAssistant() }

    fun assistant() = assistant

    fun getDepartmentClassifier() = OpenAiChatModelBuilder()
        .apiKey(apiKey)
        .modelName("gpt-4-turbo")
        .temperature(0.0)
        .timeout(Duration.ofSeconds(30))
        .build()

    private fun createAssistant(): Assistant {
        return AiServices.builder(Assistant::class.java)
            .chatLanguageModel(chatLanguageModel)
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