package daggerok.message

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME

@Table("messages")
data class Message(
    @Id
    val id: Long? = null,

    val body: String = "",

    @CreatedDate
    @LastModifiedDate
    @DateTimeFormat(iso = DATE_TIME)
    val timestamp: Instant? = null,
)
