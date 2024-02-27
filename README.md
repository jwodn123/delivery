1. 프로젝트 소개
[배달 시스템]
<aside>
💡 배달의 민족과 같은 시스템 시나리오를 통해 개발한 개인 프로젝트 입니다.

- 기본적인 기능들은 배달의 민족과 같다고 생각하시면 됩니다.
- 다른점이 있다면 저의 프로젝트에서는 ‘라이더’ 라는 존재를 빼고, 가게 사장님이 버튼 클릭으로 주만 상태에 대한 변경( 주문 확정, 배달 시작, 배달 완료 )을 했을 때 사용자에 휴대폰 문자 메시지가 전달되도록 시나리오를 변경 하였습니다.

### ✅ 프로젝트 핵심 기능

- RabbitMQ 를 활용한 실시간 주문 상태 변경 알림 서비스.
- 주문 상태 변경에 대한 메시지가 Consume 되지 않을 때, Sentry + Slack 을 활용한 모니터링 시스템 구축.

### ✅ 프로젝트 목표

- 주문 상태 변경에 대한 알림이 네트워크 상의 문제 등으로 인해 사용자에게 제대로 전달 되지 않을 경우, RabbitMQ 를 활용하여 전달하려는 메시지에 대한 **데이터 유실을 막고**, 전달되지 않은 메시지를 다시 한번 **사용자에게 재전송** 하는 구조 확립.
- RabbitMQ의 Queue에서 메시지가 제대로 consume 되지 않는 문제가 많이 발생할 경우 Slack 으로 Error에 대한 알림을 보내주는 모니터링 시스템 구축.
</aside>
