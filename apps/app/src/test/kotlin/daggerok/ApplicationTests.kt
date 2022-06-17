package daggerok

import daggerok.message.Message
import daggerok.message.Messages
import daggerok.testing.infrastructure.AbstractMySqlTestContainerTest
import org.apache.logging.log4j.kotlin.logger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import reactor.test.StepVerifier

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = [Application::class])
class ApplicationTests(@Autowired val messages: Messages) : AbstractMySqlTestContainerTest() {

    @Test
    fun `should test context`() {
        // given
        messages.deleteAll().subscribe(log::info)

        // when
        StepVerifier.create(messages.save(Message(body = "Hello!")))
            .consumeNextWith { log.info { "saved: $it" } }
            .verifyComplete()

        // then
        StepVerifier.create(messages.findAll())
            .consumeNextWith { log.info { "found: $it" } }
            .verifyComplete()
    }

    companion object {
        val log = logger()
    }
}
