package com.example.ollamatest.controller

import dev.langchain4j.model.ollama.OllamaChatModel.OllamaChatModelBuilder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/chat")
class ChatController {


    @PostMapping
    fun simpleQuestion(@RequestBody question: Question): String{
        val llm = OllamaChatModelBuilder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3")
            .timeout(Duration.ofMinutes(5))
            .build()
        return llm.generate(question.question)
    }

    //todo: structured prompt question live1 1h 7min
    //todo: RAG model question live 2 40min

}