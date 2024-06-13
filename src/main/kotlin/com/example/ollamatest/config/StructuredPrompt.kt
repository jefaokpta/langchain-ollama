package com.example.ollamatest.config

import dev.langchain4j.model.input.structured.StructuredPrompt

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */

class StructuredPrompt {

    @StructuredPrompt(
        "Voçê é a recepcionista de uma empresa de telefonia e precisa direcionar o cliente para um departamento com base no texto: {{text}}",
        "Responda apenas uma das opções: {{departments}}",
        "Caso não saiba oque responder, responda apenas a palavra: Indefinido"
    )
    class DepartmentTemplate(val text: String, val departments: List<String>)
}