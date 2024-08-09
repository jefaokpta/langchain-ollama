package com.example.ollamatest.whisper

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 8/9/24
 */
@Service
class TranscriptionService(private val transcriptionClient: TranscriptionClient) {

    fun transcribeAudio(audioName: String): JsonNode {
        return transcriptionClient.transcribe(audioName)
    }
}