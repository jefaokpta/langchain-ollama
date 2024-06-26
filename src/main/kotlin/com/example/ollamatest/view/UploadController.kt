package com.example.ollamatest.view

import com.example.ollamatest.cache.AssistantCache
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File


/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 12/06/2024
 */
@Controller
@RequestMapping("/")
class UploadController(private val assistantCache: AssistantCache) {

    private val log = LoggerFactory.getLogger(this::class.java)
    @Value("\${documents.path}")
    private lateinit var documentsPath: String

    @GetMapping("/upload")
    fun upload(): String {
        return "upload"
    }

    @PostMapping("/submit")
    fun submit(@RequestParam("file") file: MultipartFile): String {
        log.info("Submitado: ${file.originalFilename}")
        if (!file.originalFilename!!.endsWith(".txt")) {
            return "redirect:upload?error=invalid-file"
        }
        file.transferTo(File("${System.getProperty("user.dir")}/$documentsPath/${file.originalFilename}"))
        assistantCache.clear()
        return "redirect:upload?success=true"
    }


}