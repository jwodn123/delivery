//import com.teamsparta.delivery_system.DatabaseCleanup
//import io.restassured.RestAssured
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
//import org.junit.jupiter.api.BeforeEach
//import org.springframework.boot.test.web.server.LocalServerPort
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//class ApiTest {
//
//    @Autowired
//    private lateinit var databaseCleanup: DatabaseCleanup
//
//    @LocalServerPort
//    private var port: Int = 0
//
//    @BeforeEach
//    fun setUp() {
//        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
//            RestAssured.port = port
//            databaseCleanup.afterPropertiesSet()
//        }
//        databaseCleanup.execute()
//    }
//}
