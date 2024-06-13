package com.example.ollamatest.whisper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 13/06/2024
 */
@Service
class TranscribeService(private val transcriber: Transcriber) {

    @Value("\${transcriptions.path}")
    private lateinit var transcriptionsPath: String

    fun transcribe(audio: String): Transcription {
        transcriber.transcribe(audio)
        val text = File("$transcriptionsPath/$audio").readText()
        return Transcription(jacksonObjectMapper().readTree(text)["text"].asText())
    }
}