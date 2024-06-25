package com.example.ollamatest.openai

import com.example.ollamatest.config.StructuredPrompt
import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.Department
import com.example.ollamatest.model.Question
import dev.langchain4j.model.input.structured.StructuredPromptProcessor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 24/06/2024
 */
@RestController
@RequestMapping("/openai")
class OpenAiController(private val openAiService: OpenAiService) {

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
        return openAiService.getDepartmentClassifier().generate(prompt.text())
    }

    @PostMapping("/chat/assistant")
    fun chatAssistant(@RequestBody question: Question): Answer {
        return Answer(openAiService.assistant(question).answer(question.question))
    }

    @PostMapping("/support")
    fun supportQuestion(@RequestBody question: Question): String{
        return openAiService.bookingAssistantSupport().chat(question.question)
    }


}