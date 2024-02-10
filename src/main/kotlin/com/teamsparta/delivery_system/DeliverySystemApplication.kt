package com.teamsparta.delivery_system

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableBatchProcessing  // 배치를 활성화
//@EnableScheduling       // @EnableScheduling은 선택 사항입니다. 만약 CI/CD 도구 없이 해당 애플리케이션을 계속해서 실행상태를 유지하며 특정 시간대에 배치작업을 수행 하길 원한다면 추가해줍니다.
class DeliverySystemApplication

fun main(args: Array<String>) {
    runApplication<DeliverySystemApplication>(*args)
}
