package com.example.ollamatest.llama

import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.DepartmentQuestion
import com.example.ollamatest.model.Question
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 26/06/2024
 */
@RestController
@RequestMapping("/llama")
class LlamaController(private val llamaService: LlamaService) {

    @PostMapping("/department")
    fun structuredPrompt(@RequestBody departmentQuestion: DepartmentQuestion): Answer {
        return llamaService.classifierDepartment(departmentQuestion)
    }

    @PostMapping("/chat")
    fun ragQuestion(@RequestBody question: Question): Answer {
        return Answer(llamaService.assistant().answer(question.question))
    }
}