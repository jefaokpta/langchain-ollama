package com.example.ollamatest.controller

import com.example.ollamatest.config.StructuredPrompt
import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.Department
import com.example.ollamatest.model.Question
import com.example.ollamatest.openai.OpenAiService
import dev.langchain4j.model.input.structured.StructuredPromptProcessor
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/veia")
class ChatController(private val openAiService: OpenAiService) {

    @PostMapping("/department")
    fun structuredPrompt(@RequestBody text: String): String{
        val deptos = listOf(
            Department(2, "Financeiro"),
            Department(1, "RH"),
            Department(3, "Comercial"),
            Department(4, "Marketing"),
            Department(5, "Suporte")
//            Department(1, "Já sou cliente"),
//            Department(1, "Não sou cliente"),
        )
        val deptoTemplate = StructuredPrompt.DepartmentTemplate(text, deptos.map(Department::department))
            val prompt = StructuredPromptProcessor.toPrompt(deptoTemplate)
        return openAiService.getDepartmentClassifier().generate(prompt.text())
    }

    @CrossOrigin(originPatterns = ["https://*.vipsolutions.com.br"])
    @PostMapping("/chat")
    fun ragQuestion(@RequestBody question: Question): Answer {
        return Answer(openAiService.assistant(question).answer(question.question))
    }


}