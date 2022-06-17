package daggerok.testing.infrastructure

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
internal class AbstractMySqlTestContainerTestApp

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [AbstractMySqlTestContainerTestApp::class])
abstract class AbstractMySqlTestContainerTest {

    companion object {
        data class TestMySQLContainer(val image: String = "mysql:8.0.24") : MySQLContainer<TestMySQLContainer>(image)

        @Container
        val mysql: TestMySQLContainer = TestMySQLContainer()
            .withDatabaseName("database")
            .withEnv("TZ", "UTC")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url") { mysql.jdbcUrl.replaceFirst("jdbc:", "r2dbc:") }
            registry.add("spring.r2dbc.username", mysql::getUsername)
            registry.add("spring.r2dbc.password", mysql::getPassword)
        }
    }
}
