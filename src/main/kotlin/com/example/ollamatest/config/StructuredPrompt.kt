package com.example.ollamatest.config

import dev.langchain4j.model.input.structured.StructuredPrompt

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */

class StructuredPrompt(prompt: String) {

    @StructuredPrompt(
        "Voçê é a atendente de uma empresa de serviços online e deve adivinhar qual o departamento que melhor atenda a necessidade do cliente: {{text}}",
        "Responda apenas o número correspondente do departamento: {{departments}}",
        "Caso não saiba o que responder, responda apenas o múmero: 0"
    )
    class DepartmentTemplate(val text: String, val departments: List<String>)
}