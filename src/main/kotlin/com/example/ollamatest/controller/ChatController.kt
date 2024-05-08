package com.example.ollamatest.controller

import dev.langchain4j.model.input.structured.StructuredPromptProcessor
import dev.langchain4j.model.ollama.OllamaChatModel.OllamaChatModelBuilder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/chat")
class ChatController {

    private val llm = OllamaChatModelBuilder()
        .baseUrl("http://localhost:11434")
        .modelName("llama3")
        .timeout(Duration.ofMinutes(5))
        .build()

    @PostMapping
    fun simpleQuestion(@RequestBody question: Question): String{
        return llm.generate(question.question)
    }

    @PostMapping("/recipe")
    fun structuredPrompt(@RequestBody ingredients: List<String>): String{
        val foodTemplate = StructuredPrompt.FoodTemplate(ingredients)
        val prompt = StructuredPromptProcessor.toPrompt(foodTemplate)
        return llm.generate(prompt.text())
    }

    @PostMapping("/classified")
    fun structuredPrompt(@RequestBody text: String): String{
        val deptoTemplate = StructuredPrompt.DepartmentTemplate(text, listOf("Financeiro", "RH", "Comercial", "Marketing", "Suporte"))
        val prompt = StructuredPromptProcessor.toPrompt(deptoTemplate)
        return llm.generate(prompt.text())
    }

    //todo: RAG model question live 2 40min

}