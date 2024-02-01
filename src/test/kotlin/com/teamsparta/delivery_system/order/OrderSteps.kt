//import com.teamsparta.delivery_system.web.request.OrderRequest
//import io.restassured.RestAssured
//import io.restassured.http.ContentType
//import io.restassured.response.Response
//
//object OrderSteps {
//
//    fun placeOrderRequest(cartId: Long, request: OrderRequest): Response {
//        return RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(request)
//            .`when`()
//            .post("/api/orders/{cartId}", cartId)
//            .then() // 여기서 then()을 호출해야 합니다.
//            .extract()
//            .response()
//    }
//
//    fun createOrderRequest(): OrderRequest {
//        val paymentMethod = "카카오페이"
//        val totalPrice = 23000L
//        val requests = "무 빼주세요"
//        return OrderRequest(paymentMethod, totalPrice, requests)
//    }
//}
