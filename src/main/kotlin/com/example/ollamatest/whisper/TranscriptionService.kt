package com.example.ollamatest.whisper

import com.example.ollamatest.llama.LlamaService
import com.example.ollamatest.model.Department
import com.example.ollamatest.model.DepartmentQuestion
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 8/9/24
 */
@Service
class TranscriptionService(
    private val transcriptionClient: TranscriptionClient,
    private val llamaService: LlamaService,
    private val jacksonObjectMapper: ObjectMapper
) {

    fun transcribeAudio(audioName: String, jsonDepartments: String): String {
        val jsonTranscription =  transcriptionClient.transcribe(audioName)
        return llamaService.classifierDepartment(DepartmentQuestion(jsonTranscription["text"].asText(), jacksonObjectMapper.readValue(jsonDepartments, Array<Department>::class.java).toList()))
    }
}