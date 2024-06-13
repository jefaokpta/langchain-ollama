package com.example.ollamatest.whisper

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 13/06/2024
 */
@RestController
@RequestMapping("/veia/transcription")
class TranscriptionController(private val transcribeService: TranscribeService) {

    @PostMapping
    fun transcribe(@RequestBody audio: Transcription): Transcription{
        return transcribeService.transcribe(audio.text)
    }

}