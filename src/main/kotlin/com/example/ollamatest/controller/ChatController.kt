package com.example.ollamatest.controller

import com.example.ollamatest.llama.LlamaService
import com.example.ollamatest.model.Answer
import com.example.ollamatest.model.DepartmentQuestion
import com.example.ollamatest.model.Question
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/veia")
class ChatController(private val llamaService: LlamaService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/department")
    fun structuredPrompt(@RequestBody departmentQuestion: DepartmentQuestion): Answer {
        if (departmentQuestion.departments.isEmpty()) {
            log.warn("\uD83D\uDEAB Nenhuma opção de departamento para classificar")
            return Answer("0")
        }
        if (departmentQuestion.departments.size == 1) {
            log.warn("\uD83D\uDEAB Existe apenas uma opção de departamento para classificar")
            return Answer(departmentQuestion.departments.first().deptoId)
        }
        if (departmentQuestion.audio != null) {
            return llamaService.classifierDepartmentAudio(departmentQuestion)
        }
        return llamaService.classifierDepartment(departmentQuestion)
    }

    @CrossOrigin(originPatterns = ["https://*.vipsolutions.com.br"])
    @PostMapping("/chat")
    fun ragQuestion(@RequestBody question: Question): Answer {
        return Answer(llamaService.assistant().answer(question.question))
    }


}