package com.example.ollamatest.openai

import com.example.ollamatest.model.Question
import dev.langchain4j.model.chat.ChatLanguageModel
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
class OpenAiController(private val chatLanguageModel: ChatLanguageModel) {

    @PostMapping("/chat")
    fun chat(@RequestBody question: Question): String {
        return chatLanguageModel.generate(question.question)
    }
}