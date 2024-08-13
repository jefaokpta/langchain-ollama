package com.example.ollamatest.controller

import com.example.ollamatest.llama.LlamaService
import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.DepartmentQuestion
import com.example.ollamatest.model.Question
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/veia")
class ChatController(private val llamaService: LlamaService) {

    @PostMapping("/department")
    fun structuredPrompt(@RequestBody departmentQuestion: DepartmentQuestion): String{
        return llamaService.classifierDepartment(departmentQuestion)
    }

    @CrossOrigin(originPatterns = ["https://*.vipsolutions.com.br"])
    @PostMapping("/chat")
    fun ragQuestion(@RequestBody question: Question): Answer {
        return Answer(llamaService.assistant().answer(question.question))
    }


}