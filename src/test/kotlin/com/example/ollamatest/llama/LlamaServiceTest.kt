package com.example.ollamatest.llama

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 8/23/24
 */
@Disabled
class LlamaServiceTest {

    @Test
    fun conta() {
        val text = "Olá! Para ajudar você a conhecer nossos serviços melhor, eu recomendo entrar em contato com nosso departamento de atendimento ao cliente. Eles podem fornecer informações detalhadas sobre todos os serviços que oferecemos e orientá-lo na escolha do mais adequado para suas necessidades.\n" +
                "\n" +
                "Resposta: 1"
        val count1 = countSubstring(text, "1")
        println("Count of 1: $count1")
        val count2 = countSubstring(text, "2")
        println("Count of 2: $count2")
    }

    private fun countSubstring(text: String, substr: String): Int {
        var count = 0
        var index = 0
        while (index < text.length) {
            if (text.startsWith(substr, index)) {
                count++
                index += substr.length
            } else {
                index++
            }
        }
        return count
    }


}