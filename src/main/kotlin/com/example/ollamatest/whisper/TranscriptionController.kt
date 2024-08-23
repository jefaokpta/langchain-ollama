package com.example.ollamatest.whisper

import com.example.ollamatest.model.Answer
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Paths

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 13/06/2024
 */
@RestController
@RequestMapping("/veia/transcription")
class TranscriptionController(
    private val transcriptionService: TranscriptionService,
) {

    @Value("\${transcriptions.path}")
    private lateinit var audioPath: String

    @PostMapping
    fun uploadAudio(@RequestParam("audio") audio: MultipartFile, @RequestParam jsonDepartments: String): Answer {
        audio.transferTo(Paths.get(audioPath, audio.originalFilename))
        return transcriptionService.transcribeAudio(audio.originalFilename!!, jsonDepartments)
    }
}