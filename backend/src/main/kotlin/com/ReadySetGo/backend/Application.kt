package com.ReadySetGo.backend

import com.ReadySetGo.backend.controller.authRoutes
import com.ReadySetGo.backend.repository.UserRepository
import com.ReadySetGo.backend.service.UserService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.dotenv
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    // ── Load environment ─────────────────────────────────────────
    val isRailway = System.getenv("RAILWAY_ENVIRONMENT") != null

    val getEnv: (String) -> String = { key ->
        if (isRailway) {
            System.getenv(key) ?: throw IllegalStateException("Missing env var: $key")
        } else {
            val env = dotenv {
                directory = "../"
                ignoreIfMissing = false
            }
            env[key]
        }
    }

    // ── Plugins ──────────────────────────────────────────────────
    install(ContentNegotiation) {
        json()
    }

    // ── Database ─────────────────────────────────────────────────
    val dataSource = HikariDataSource(HikariConfig().apply {
        jdbcUrl         = "jdbc:postgresql://${getEnv("DB_HOST")}:${getEnv("DB_PORT")}/${getEnv("DB_NAME")}"
        username        = getEnv("DB_USER")
        password        = getEnv("DB_PASSWORD")
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 5
    })

    // ── Services ─────────────────────────────────────────────────
    val userRepository = UserRepository(dataSource)
    val userService = UserService(
        userRepository  = userRepository,
        jwtSecret       = getEnv("JWT_SECRET"),
        jwtIssuer       = getEnv("JWT_ISSUER"),
        jwtAudience     = getEnv("JWT_AUDIENCE"),
        jwtExpirationMs = getEnv("JWT_EXPIRATION_MS").toLong()
    )

    // ── Routes ───────────────────────────────────────────────────
    routing {
        get("/health") {
            val dbOk = try {
                dataSource.connection.use { it.isValid(2) }
            } catch (e: Exception) {
                false
            }
            call.respond(
                mapOf(
                    "status"   to "ok",
                    "database" to if (dbOk) "connected" else "unreachable"
                )
            )
        }

        authRoutes(userService)
    }
}