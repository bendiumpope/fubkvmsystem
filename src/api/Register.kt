package com.fubk_visitor_system.api

import com.fubk_visitor_system.*
import com.fubk_visitor_system.model.User
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val REGISTER_ENDPOINT = "/register"

@Location(REGISTER_ENDPOINT)
class Register

fun Route.register(db: Repository, jwtService: JwtService){
    post<Register>{
        val params = call.receive<Parameters>()

        val userId = params["userId"] ?: return@post call.redirect(it)
        val surName = params["surName"] ?: return@post call.redirect(it)
        val firstName = params["firstName"] ?: return@post call.redirect(it)
        val emailAddress = params["emailAddress"] ?: return@post call.redirect(it)
        val phoneNumber = params["phoneNumber"] ?: return@post call.redirect(it)
        val address = params["address"] ?: return@post call.redirect(it)
        val role = "visitor"
        val password = params["password"] ?: return@post call.redirect(it)
        val confirmPassword = params["confirmPassword"] ?: return@post call.redirect(it)

        when{
            password.length < MIN_PASSWORD_LENGTH ->
                call.respondText("Password should be at least $MIN_PASSWORD_LENGTH characters long", status = HttpStatusCode.BadRequest)
            password != confirmPassword ->
                call.respondText("Password/confirm password dont match", status = HttpStatusCode.BadRequest)
            userId.length < MIN_USER_ID_LENGTH ->
                call.respondText("Username should be at least $MIN_USER_ID_LENGTH characters long", status = HttpStatusCode.BadRequest)
            !userNameValid(userId) ->
                call.respondText("Username should consist of digits, letters, dots or underscores", status = HttpStatusCode.BadRequest)
            db.user(userId) != null ->
                call.respondText("User with the following username is already registered", status = HttpStatusCode.Conflict)

            else -> {
                val passwordHash = hash(password)
                val newUser = User(userId, surName, firstName, emailAddress, phoneNumber, address, role, passwordHash)

                try {
                    db.createUser(newUser)
                    db.addactiveUser(userId, "OnLine")
                } catch (e: Throwable){
                    when{
                        db.user(userId) != null ->
                            call.respondText("User with the following username is already registered", status = HttpStatusCode.NotAcceptable)
                        db.userByEmail(emailAddress) != null ->
                            call.respondText("User with the following email $emailAddress is already registered", status = HttpStatusCode.NotAcceptable)

                        else ->{
                            application.log.error("Failed to register user", e)
                            call.respondText("Failed to register", status = HttpStatusCode.NotAcceptable)
                            println("an error occured $e")
                        }
                    }
                }

                val user = db.user(userId, hash(password))

                if(user != null) {
                    val token = jwtService.generateToken(user)
                    call.respondText(token)
                }

            }
        }

    }
}