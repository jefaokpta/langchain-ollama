package com.example.ollamatest.whisper

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 13/06/2024
 */
@Service
class Transcriptor {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${transcriptions.path}")
    private lateinit var transcriptionsPath: String

    fun transcribe(audio: String) {
        val processBuilder = ProcessBuilder()
        val command =
            "~/Workspace/Python/pythonProject/.venv/bin/whisper $transcriptionsPath/$audio " +
                    "--fp16=False " +
                    "--language=pt " +
                    "--beam_size=5 " +
                    "--patience=2 " +
                    "--output_dir=$transcriptionsPath " +
                    "--output_format=json"
        processBuilder.command("bash", "-c", command)
        val process = processBuilder.start()
        log.info("Transcribing audio $audio... ${process.onExit().get()}")
    }
}