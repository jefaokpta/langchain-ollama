package com.example.ollamatest.controller

import com.example.ollamatest.config.IAConfiguration
import com.example.ollamatest.config.StructuredPrompt
import com.example.ollamatest.model.Department
import com.example.ollamatest.model.Question
import dev.langchain4j.model.input.structured.StructuredPromptProcessor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/veia/chat")
class ChatController(private val iaConfiguration: IAConfiguration) {

    @PostMapping("/department")
    fun structuredPrompt(@RequestBody text: String): String{
        val deptos = listOf(
            Department(2, "Financeiro"),
            Department(1, "RH"),
            Department(3, "Comercial"),
            Department(4, "Marketing"),
            Department(5, "Suporte")
        )
        val deptoTemplate = StructuredPrompt.DepartmentTemplate(text, deptos.map(Department::department))
            val prompt = StructuredPromptProcessor.toPrompt(deptoTemplate)
        return iaConfiguration.getIa().generate(prompt.text())
    }

    @PostMapping("/assistant")
    fun ragQuestion(@RequestBody question: Question): String{
        return iaConfiguration.getAssistant().answer(question.question)
    }

    @PostMapping("/support")
    fun supportQuestion(@RequestBody question: Question): String{
        return iaConfiguration.getAssistantSupport().chat(question.question)
    }

}