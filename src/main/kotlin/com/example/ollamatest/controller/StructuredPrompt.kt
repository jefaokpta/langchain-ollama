package com.example.ollamatest.controller

import dev.langchain4j.model.input.structured.StructuredPrompt

/**
 * @author Jefferson Alves Reis (jefaokpta) <jefaokpta@hotmail.com>
 * Date: 08/05/2024
 */

class StructuredPrompt {
    @StructuredPrompt(
        "Crie uma receita com os seguintes ingredientes {{ingredients}}: ",
        "Nome da receita: ...",
        "Modo de preparo: ...",
        "Tempo de preparo: ...",
    )
    class FoodTemplate(val ingredients: List<String>)

    @StructuredPrompt(
        "Voçê é a recepcionista de uma empresa e precisa direcionar o cliente para um departamento com base no seguinte texto: {{text}}",
        "Responda apenas umas destas opções: {{departments}}",
    )
    class DepartmentTemplate(val text: String, val departments: List<String>)
}