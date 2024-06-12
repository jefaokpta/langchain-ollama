package com.example.ollamatest.config

import com.example.ollamatest.tools.AssistantSupport
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
class IAConfiguration(private val bookingTool: BookingTool) {

    @Value("\${ollama.url}")
    private lateinit var ollamaUrl: String

    @Value("\${documents.path}")
    private lateinit var documentsPath: String

    private var ollamaChatModel: OllamaChatModel? = null

    private var assistant: Assistant? = null
    private var assistantSupport: AssistantSupport? = null

    fun getIa(): OllamaChatModel {
        if(ollamaChatModel == null){
            ollamaChatModel = OllamaChatModelBuilder()
                .baseUrl(ollamaUrl)
                .modelName("llama3")
                .temperature(0.0)
                .timeout(Duration.ofMinutes(1))
                .build()
        }
        return ollamaChatModel!!
    }

    fun getAssistant(): Assistant {
        if (assistant == null) {
            assistant = createAssistant()
        }
        return assistant!!
    }

    fun getAssistantSupport(): AssistantSupport {
        if (assistantSupport == null) {
            assistantSupport = createAssistantSupport()
        }
        return assistantSupport!!
    }

    fun createAssistant(): Assistant {
        return AiServices.builder(Assistant::class.java)
            .chatLanguageModel(getIa())
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .contentRetriever(createContentRetriever())
            .build()
    }

    fun createAssistantSupport(): AssistantSupport {
        return AiServices.builder(AssistantSupport::class.java)
            .chatLanguageModel(getIa())
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .tools(bookingTool)
            .contentRetriever(createTermsOfUse())
            .build()
    }

    fun createContentRetriever(): ContentRetriever {
        val inMemoryEmbeddingStore: InMemoryEmbeddingStore<TextSegment> = InMemoryEmbeddingStore<TextSegment>()
        val documents = FileSystemDocumentLoader.loadDocuments(documentsPath)
        println("Documents loaded: ${documents.size}")
        EmbeddingStoreIngestor.ingest(documents, inMemoryEmbeddingStore)
        return EmbeddingStoreContentRetriever.from(inMemoryEmbeddingStore)
    }

    fun createTermsOfUse(): ContentRetriever {
        val inMemoryEmbeddingStore: InMemoryEmbeddingStore<TextSegment> = InMemoryEmbeddingStore<TextSegment>()
        val document = FileSystemDocumentLoader.loadDocument("src/main/resources/miles-of-smiles-terms-of-use.txt")
        println("Document loaded: ${document.text()}")
        EmbeddingStoreIngestor.ingest(document, inMemoryEmbeddingStore)
        return EmbeddingStoreContentRetriever.from(inMemoryEmbeddingStore)
    }
}