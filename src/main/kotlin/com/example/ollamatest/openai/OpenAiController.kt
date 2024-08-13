package com.example.ollamatest.openai

import com.example.ollamatest.config.StructuredPrompt
import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.DepartmentQuestion
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
    fun structuredPrompt(@RequestBody departmentQuestion: DepartmentQuestion): String{
        val deptoTemplate = StructuredPrompt.DepartmentTemplate(departmentQuestion.text, departmentQuestion.departments.map { "${it.id}: ${it.name}" })
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