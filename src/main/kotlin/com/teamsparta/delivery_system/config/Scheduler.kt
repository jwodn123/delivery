//package com.teamsparta.delivery_system.config
//
//import org.springframework.batch.core.*
//import org.springframework.batch.core.launch.JobLauncher
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.scheduling.annotation.EnableScheduling
//import org.springframework.scheduling.annotation.Scheduled
//import org.springframework.stereotype.Component
//import java.time.OffsetDateTime
//
//@EnableScheduling
//@Component
//class Scheduler {
//    @Autowired
//    lateinit var jobLauncher: JobLauncher
//    @Autowired lateinit var job: Job
//
//    @Scheduled(fixedDelay = 30000)
//    fun startJob() {
//        val jobParameterMap = mapOf("requestDate" to JobParameter(OffsetDateTime.now().toString(), String::class.java))
//        val jobParameters = JobParameters(jobParameterMap)
//
//        val execution: JobExecution = jobLauncher.run(job, jobParameters)
//
//        // 작업이 완료될 때까지 대기
//        while (execution.status == BatchStatus.STARTING || execution.status == BatchStatus.STARTED) {
//            try {
//                Thread.sleep(1000) // 1초마다 체크
//            } catch (e: InterruptedException) {
//                // 스레드 인터럽트가 발생하면 종료
//                Thread.currentThread().interrupt()
//                break
//            }
//        }
//
//        println("Job execution completed with status: ${execution.status}")
//    }
//}
