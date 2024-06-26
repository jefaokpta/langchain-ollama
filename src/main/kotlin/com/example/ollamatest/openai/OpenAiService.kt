package com.example.ollamatest.openai

import com.example.ollamatest.cache.AssistantCache
import com.example.ollamatest.model.Question
import com.example.ollamatest.tools.AssistantSupport
import com.example.ollamatest.tools.BookingTool
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
class OpenAiService(
    private val chatLanguageModel: ChatLanguageModel,
    private val bookingTool: BookingTool,
    private val assistantCache: AssistantCache
) {

    @Value("\${langchain4j.open-ai.chat-model.api-key}")
    private lateinit var apiKey: String

    private val assistantSupport: AssistantSupport by lazy { createAssistantSupport() }

    fun assistant(question: Question) = assistantCache.getAssistant(question.session)
    fun bookingAssistantSupport() = assistantSupport

    fun getDepartmentClassifier() = OpenAiChatModelBuilder()
        .apiKey(apiKey)
        .modelName("gpt-4-turbo")
        .temperature(0.0)
        .timeout(Duration.ofSeconds(30))
        .build()



    private fun createAssistantSupport(): AssistantSupport {
        return AiServices.builder(AssistantSupport::class.java)
            .chatLanguageModel(chatLanguageModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .tools(bookingTool)
            .contentRetriever(createTermsOfUse())
            .build()
    }

    private fun createTermsOfUse(): ContentRetriever {
        val inMemoryEmbeddingStore: InMemoryEmbeddingStore<TextSegment> = InMemoryEmbeddingStore<TextSegment>()
        val document = FileSystemDocumentLoader.loadDocument("src/main/resources/miles-of-smiles-terms-of-use.txt")
        println("Document loaded: ${document.text()}")
        EmbeddingStoreIngestor.ingest(document, inMemoryEmbeddingStore)
        return EmbeddingStoreContentRetriever.from(inMemoryEmbeddingStore)
    }
}