package com.teamsparta.delivery_system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import io.sentry.Sentry

@EnableJpaAuditing
@SpringBootApplication
//@EnableBatchProcessing  // 배치를 활성화, 5 버전 이후 부터 사용 x
//@EnableScheduling       // 선택사항, @EnableScheduling은 선택 사항입니다. 만약 CI/CD 도구 없이 해당 애플리케이션을 계속해서 실행상태를 유지하며 특정 시간대에 배치작업을 수행 하길 원한다면 추가해줍니다.
class DeliverySystemApplication

fun main(args: Array<String>) {

    Sentry.init { options ->
        // Sentry 프로젝트에 연결하기 위한 DSN(Data Source Name)입니다. DSN은 Sentry에서 프로젝트를 구별하고 해당 프로젝트에 오류 및 로깅 데이터를 보낼 때 사용
        options.dsn = "https://1f32fca5e83cf6acf173259d79fe606c@o4506755611754496.ingest.sentry.io/4506755620732928"
        // 트랜잭션 추적을 샘플링하는 비율을 나타냅니다.
        // 값은 0.0에서 1.0 사이의 실수로, 1.0은 100% 샘플링을 의미합니다.
        // 이 값을 조절하여 전체 트랜잭션 중 얼마나 많은 비율을 샘플링할지 결정할 수 있습니다.
        // 예를 들어, options.tracesSampleRate = 0.5로 설정하면 50%의 트랜잭션이 샘플링됩니다.
        options.tracesSampleRate = 1.0
        // 디버그 모드를 활성화하는 옵션으로, 디버그 모드가 활성화되면 더 자세한 로깅 및 디버깅 정보가 캡처되어 Sentry에 전송됩니다.
        // 애플리케이션을 개발 또는 테스트할 때 유용하며, 프로덕션 환경에서는 비활성화하는 것이 일반적입니다.
        options.isDebug = true
    }

    runApplication<DeliverySystemApplication>(*args)
}
