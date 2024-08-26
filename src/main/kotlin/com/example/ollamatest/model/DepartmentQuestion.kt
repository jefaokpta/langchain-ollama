package com.example.ollamatest.model

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 8/8/24
 */
data class DepartmentQuestion(
    val text: String? = null,
    val departments: List<Department>,
    val audio: String? = null
) {
}