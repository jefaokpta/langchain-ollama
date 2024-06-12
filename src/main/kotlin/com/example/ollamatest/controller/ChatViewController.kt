package com.example.ollamatest.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 12/06/2024
 */
@Controller
@RequestMapping("/chat")
class ChatViewController {

    @GetMapping
    fun chat(): String {
        return "chat"
    }
}