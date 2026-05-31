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

    val env = if (!isRailway) {
        dotenv {
            directory = "../"
            ignoreIfMissing = false
        }
    } else null

    val getEnv: (String) -> String = { key ->
        if (isRailway) {
            System.getenv(key) ?: throw IllegalStateException("Missing Railway env var: $key")
        } else {
            env?.get(key) ?: throw IllegalStateException("Missing .env var: $key")
        }
    }

    // ── Mode selection ───────────────────────────────────────────
    val localMode = !isRailway && getEnv("LOCAL_MODE") == "true"

    val dbHost     = if (localMode) getEnv("LOCAL_DB_HOST")      else getEnv("PRODUCTION_DB_HOST")
    val dbPort     = if (localMode) getEnv("LOCAL_DB_PORT")      else getEnv("PRODUCTION_DB_PORT")
    val dbName     = if (localMode) getEnv("LOCAL_DB_NAME")      else getEnv("PRODUCTION_DB_NAME")
    val dbUser     = if (localMode) getEnv("LOCAL_DB_USER")      else getEnv("PRODUCTION_DB_USER")
    val dbPassword = if (localMode) getEnv("LOCAL_DB_PASSWORD")  else getEnv("PRODUCTION_DB_PASSWORD")

    val modeLabel = when {
        isRailway  -> "railway"
        localMode  -> "local"
        else       -> "remote"
    }

    log.info("---------------------------------------------------")
    log.info("  Mode     : $modeLabel")
    log.info("  DB Host  : $dbHost")
    log.info("  DB Port  : $dbPort")
    log.info("  DB Name  : $dbName")
    log.info("  DB User  : $dbUser")
    log.info("---------------------------------------------------")

    // ── Plugins ──────────────────────────────────────────────────
    install(ContentNegotiation) {
        json()
    }

    // ── Database ─────────────────────────────────────────────────
    val dataSource = HikariDataSource(HikariConfig().apply {
        jdbcUrl         = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
        username        = dbUser
        password        = dbPassword
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 5
    })

    log.info("Database pool initialized → ${dataSource.jdbcUrl}")

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
                    "database" to if (dbOk) "connected" else "unreachable",
                    "mode"     to modeLabel
                )
            )
        }

        authRoutes(userService)
    }
}