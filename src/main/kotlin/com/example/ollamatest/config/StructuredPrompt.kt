package com.example.ollamatest.config

import dev.langchain4j.model.input.structured.StructuredPrompt

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */

class StructuredPrompt {

    @StructuredPrompt(
        "Voçê é a recepcionista de uma empresa e precisa direcionar o cliente de acordo com o texto: {{text}}",
        "Responda apenas uma das opções: {{options}}",
        "Caso não saiba o que responder, responda apenas a palavra: Indefinido"
    )
    class DepartmentTemplate(val text: String, val options: List<String>)
}