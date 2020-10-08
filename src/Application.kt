package com.fubk_visitor_system

import com.fubk_visitor_system.api.*
import com.fubk_visitor_system.api.request.recordsapi
import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.model.User
import com.fubk_visitor_system.repository.DatabaseFactory
import com.fubk_visitor_system.repository.VisitRepository
import com.fubk_visitor_system.webapp.*
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.net.URI
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(DefaultHeaders)

    install(StatusPages){
        exception<Throwable>{e ->
            call.respondText(e.localizedMessage,
                ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation){
        gson()
    }

    install(Locations)

    install(Sessions){
        cookie<EPSession>("SESSION"){
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }

    val hashFunction = { s: String -> hash(s) }
    DatabaseFactory.init()

    val db = VisitRepository()

    val jwtServices = JwtService()

    install(Authentication){
        jwt ("jwt") {
            verifier(jwtServices.verifier)
            realm = "emojiphrases app"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asString()
                val user = db.user(claimString)
                user
            }
        }
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }




    routing {
        static("/static"){
            resources("staticfiles")
        }

        home(db)
        book(db, hashFunction)
        signup(db, hashFunction)
        signin(db, hashFunction)
        addschedule(db, hashFunction)
        updateuser(db, hashFunction)
        updatebookings(db, hashFunction)
        updateschedule(db, hashFunction)
        users(db, hashFunction)
        bookings(db, hashFunction)
        active(db, hashFunction)
        schedules(db, hashFunction)
        records(db, hashFunction)
        signout(db)

        //API
        register(db, jwtServices)
        login(db, jwtServices)
        usersapi(db)
        bookingApi(db)
        scheduleapi(db)
        activeapi(db)
        recordsapi(db)
    }
}

const val API_VERSION = "/api/v1"

suspend fun ApplicationCall.redirect(location: Any){
    respondRedirect(application.locations.href(location))
}

fun ApplicationCall.verifyCode(date: Long, user: User, code:String, hashFunction: (String) -> String) =
    securityCode(date,user,hashFunction) == code &&
            (System.currentTimeMillis()-date).let { it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS) }

fun ApplicationCall.securityCode(date: Long, user:User, hashFunction: (String) -> String) =
    hashFunction("$date:${user.userId}:${request.host()}:${refererHost()}")

fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }

val ApplicationCall.apiUser get() = authentication.principal<User>()

