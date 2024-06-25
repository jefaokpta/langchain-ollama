package com.example.ollamatest.cache

import com.example.ollamatest.config.Assistant
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 25/06/2024
 */
@Component
class AssistantCache(private val chatLanguageModel: ChatLanguageModel) {

    private val assistants = mutableMapOf<String, Assistant>()

    @Value("\${documents.path}")
    private lateinit var documentsPath: String

    fun getAssistant(session: String): Assistant {
        return assistants.getOrPut(session) { createAssistant() }
    }

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

    fun clear() = assistants.clear()

}