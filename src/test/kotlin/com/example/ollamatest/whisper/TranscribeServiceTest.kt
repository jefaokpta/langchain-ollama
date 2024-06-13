package com.example.ollamatest.whisper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 13/06/2024
 */
@Disabled
@SpringBootTest
class TranscribeServiceTest(@Autowired private val transcriptor: Transcriptor){

    @Test
    fun test(){
        val processBuilder = ProcessBuilder()
        val command = "~/Workspace/Python/pythonProject/.venv/bin/whisper ~/Workspace/Python/pythonProject/audios/momoa.ogg --fp16=False --language=pt --beam_size=5 --patience=2 --output_dir=/tmp --output_format=json"
        processBuilder.command("bash", "-c", command)
        val process = processBuilder.start()
        //abaixo le a saida do processo
//            val reader = BufferedReader(InputStreamReader(process.inputStream))
//            val output = reader.readText()

        println(process.onExit().get())
    }
    @Test
    fun testService(){
        transcriptor.transcribe("momoa.ogg")
    }

}