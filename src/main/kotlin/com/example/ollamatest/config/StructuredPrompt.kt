package com.example.ollamatest.config

import dev.langchain4j.model.input.structured.StructuredPrompt

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */

class StructuredPrompt(prompt: String) {

    @StructuredPrompt(
        "Voçê é a atendente de uma empresa de serviços online e deve adivinhar qual o departamento que melhor atenda a necessidade do cliente: {{text}}",
//        "Voçê está atendendo um cliente e deve adivinhar o departamento que o cliente deseja falar: {{text}}",
        "Responda apenas 1 dos departamentos: {{departments}}",
        "Caso não saiba o que responder, responda apenas a palavra: Indefinido"
    )
    class DepartmentTemplate(val text: String, val departments: List<String>)
}