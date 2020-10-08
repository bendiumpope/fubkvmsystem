package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.MIN_PASSWORD_LENGTH
import com.fubk_visitor_system.MIN_USER_ID_LENGTH
import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val SIGNIN = "/signin"

@Location(SIGNIN)
data class Signin(val userId: String="", val error: String="")

fun Route.signin(db: Repository, hashFunction: (String) -> String){
    post<Signin>{

        val signinParameters = call.receive<Parameters>()
        val userId = signinParameters["userName"] ?: return@post call.redirect(it)
        val password = signinParameters["password"] ?: return@post call.redirect(it)

        val signInError = Signin(userId)

        val signin = when{
            userId.length < MIN_USER_ID_LENGTH -> null
            password.length < MIN_PASSWORD_LENGTH -> null
            else -> db.user(userId, hashFunction(password))
        }

        if (signin == null){
            call.redirect(signInError.copy(error = "Invalid username or password"))
        } else {

            val activeUser = db.activeUser(signin.userId)

            try {
                db.updateactiveUser(activeUser!!.id, signin.userId, "OnLine")

            } catch (e: Throwable){
                println("an error occured $e")
            }
            call.sessions.set(EPSession(signin.userId))
            call.redirect(Home())
        }
    }

    get<Signin>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user != null){
            call.redirect(Home())
        } else {
            call.respond(FreeMarkerContent("signin.ftl", mapOf("user" to it.userId, "error" to it.error), ""))
        }
    }
}