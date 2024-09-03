package com.example.ollamatest.config

import dev.langchain4j.model.input.structured.StructuredPrompt

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */

class StructuredPrompt(prompt: String) {

    @StructuredPrompt(
//        "Voçê é atendente de uma empresa de serviços online e deve adivinhar qual o departamento que melhor atenda a necessidade do cliente: {{text}}",
        "Voçê é atendente de uma empresa de serviços de telefonia",
        "Descobra se a conversa é de uma que já é cliente ou ainda não é cliente: {{text}}",
        "Responda apenas o número correspondente da opção: {{options}}",
        "Caso não saiba o que responder, responda apenas o múmero: 0"
    )
    class DepartmentTemplate(val text: String, val options: List<String>)
}