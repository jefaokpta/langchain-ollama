package com.example.ollamatest.cron

import com.example.ollamatest.cache.AssistantCache
import com.example.ollamatest.llama.LlamaService
import com.example.ollamatest.model.DepartmentQuestion
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 25/06/2024
 */
@Configuration
@EnableScheduling
class CronTask(
    private val assistantCache: AssistantCache,
    private val llamaService: LlamaService
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "0 0 3 * * *")
    fun cleanCacheEveryNight(){
        assistantCache.clear()
    }

    @Scheduled(fixedRate = 300_000, initialDelay = 120_000)
    fun keepLLMAliveEvery5Minutes(){
        log.info("Perguntando a cada 5 minutos")
        llamaService.classifierDepartment(DepartmentQuestion("Mantendo LLM ativa pra nao crashar", listOf()))
    }
}