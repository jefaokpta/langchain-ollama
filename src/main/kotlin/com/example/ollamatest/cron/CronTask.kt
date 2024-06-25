package com.example.ollamatest.cron

import com.example.ollamatest.cache.AssistantCache
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 25/06/2024
 */
@Configuration
@EnableScheduling
class CronTask(private val assistantCache: AssistantCache) {

    @Scheduled(cron = "0 0 3 * * *")
    fun cleanCacheEveryNight(){
        assistantCache.clear()
    }
}