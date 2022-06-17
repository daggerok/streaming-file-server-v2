package daggerok.message

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface Messages : R2dbcRepository<Message, Long>
