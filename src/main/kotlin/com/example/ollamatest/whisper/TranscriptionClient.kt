package com.example.ollamatest.whisper

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 8/9/24
 */
@Service
class TranscriptionClient {

    @Value("\${whisper.url}")
    private lateinit var WHISPER_URL: String

    private val HTTP_REQUEST_TIMEOUT = 180L
    private val HTTP_CONNECTION_TIMEOUT = 3L

    private val log = LoggerFactory.getLogger(this::class.java)

    fun transcribe(audioName: String): JsonNode {
        val jsonNode = jacksonObjectMapper().createObjectNode()
        jsonNode.put("audio", audioName)
        val request = HttpRequest.newBuilder(URI(WHISPER_URL))
            .POST(HttpRequest.BodyPublishers.ofString(jsonNode.toString()))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(HTTP_REQUEST_TIMEOUT))
            .build()
        try {
            HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(HTTP_CONNECTION_TIMEOUT))
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString()).let {
                    log.info("${it.body()} - ${it.statusCode()}")
                    return jacksonObjectMapper().readTree(it.body())
                }
        } catch (ex: Exception) {
            log.error("🧨 DEU RUIM AO TRANSCREVER AUDIO $audioName - ${ex.localizedMessage}")
            throw RuntimeException("🧨 DEU RUIM AO TRANSCREVER AUDIO $audioName - ${ex.localizedMessage}")
        }
    }
}