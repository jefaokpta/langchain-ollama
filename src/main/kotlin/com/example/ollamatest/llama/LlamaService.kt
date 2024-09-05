package com.example.ollamatest.llama

import com.example.ollamatest.config.Assistant
import com.example.ollamatest.config.StructuredPrompt
import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.DepartmentQuestion
import com.example.ollamatest.model.OptionRank
import com.example.ollamatest.whisper.TranscriptionClient
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.input.structured.StructuredPromptProcessor
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.ollama.OllamaChatModel.OllamaChatModelBuilder
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */
@Service
class LlamaService(private val transcriptionClient: TranscriptionClient) {

    @Value("\${ollama.url}")
    private lateinit var ollamaUrl: String

    @Value("\${documents.path}")
    private lateinit var documentsPath: String

    private val log = LoggerFactory.getLogger(this::class.java)


    private val assistant: Assistant by lazy { createAssistant() }

    fun assistant() = assistant

    fun classifierDepartment(departmentQuestion: DepartmentQuestion): Answer {
        val deptoTemplate = StructuredPrompt.DepartmentTemplate(departmentQuestion.text!!, departmentQuestion.departments.map { "${it.deptoId}: ${it.name}" })
        val prompt = StructuredPromptProcessor.toPrompt(deptoTemplate)
        return Answer(seekRigthAnswer(
            classifierModel().generate(prompt.text())
                .apply { log.info("🔥 Pergunta: ${departmentQuestion.text} - Resposta: $this") },
            departmentQuestion
        ))
    }

    fun classifierDepartmentAudio(departmentQuestion: DepartmentQuestion): Answer {
        return classifierDepartment(departmentQuestion.copy(text = transcriptionClient.transcribe(
            departmentQuestion.audio!!,
            departmentQuestion.controlNumber
        )["text"].asText()))
    }

    private fun seekRigthAnswer(fullAnswer: String, departmentQuestion: DepartmentQuestion): String {
        val optionsRanked = departmentQuestion.departments.map { OptionRank(it.deptoId, countOptions(fullAnswer, it.deptoId)) }
            .sortedByDescending { it.rank }
        log.info("Options ranked: $optionsRanked")
        if (optionsRanked[0].rank != optionsRanked[1].rank) {
            val winnerOption = optionsRanked[0].option
            if (winnerOption.contains("subura")) return "0" //todo: ignorar subura temporarimente ate ajustar o app em produção
            return winnerOption
        }
        return "0"
    }
    private fun countOptions(text: String, option: String): Int {
        var count = 0
        var index = 0
        while (index < text.length) {
            if (text.startsWith(option, index)) {
                count++
                index += option.length
            } else {
                index++
            }
        }
        return count
    }

    private fun classifierModel(): OllamaChatModel {
        return OllamaChatModelBuilder()
            .baseUrl(ollamaUrl)
//            .modelName("llama3.1")
            .modelName("phi3:medium")
//            .modelName("mistral")
            .temperature(0.1)
            .timeout(Duration.ofMinutes(1))
            .build()
    }

    private fun getConversationalModel(): OllamaChatModel {
        return OllamaChatModelBuilder()
                .baseUrl(ollamaUrl)
//                .modelName("llama3.1")
//                .modelName("phi3:medium")
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