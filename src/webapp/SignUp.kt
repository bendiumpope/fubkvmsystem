package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.MIN_PASSWORD_LENGTH
import com.fubk_visitor_system.MIN_USER_ID_LENGTH
import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.model.User
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import com.fubk_visitor_system.userNameValid
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val SIGNUP = "/signup"

@Location(SIGNUP)
data class Signup(
    val userId: String="",
    val firstName: String="",
    val lastName: String="",
    val emailAddress: String="",
    val phone: String="",
    val address: String="",
    val error: String = ""
)

fun Route.signup(db: Repository, hashFunction: (String) -> String){
    post<Signup>{
        val user = call.sessions.get<EPSession>()?.let { user -> db.user(user.userId) }


        val signUpParameters = call.receive<Parameters>()

        val userId = signUpParameters["userName"] ?: return@post call.redirect(it)
        val surName = signUpParameters["surName"] ?: return@post call.redirect(it)
        val firstName = signUpParameters["firstName"] ?: return@post call.redirect(it)
        val emailAddress = signUpParameters["emailAddress"] ?: return@post call.redirect(it)
        val phoneNumber = signUpParameters["phoneNumber"] ?: return@post call.redirect(it)
        val address = signUpParameters["address"] ?: return@post call.redirect(it)
        val role = "visitor"
        val password = signUpParameters["password"] ?: return@post call.redirect(it)
        val confirmPassword = signUpParameters["confirmPassword"] ?: return@post call.redirect(it)

        val signupError = Signup(userId, surName, emailAddress)

        when{
            password.length < MIN_PASSWORD_LENGTH ->
                call.redirect(signupError.copy(error = "Password should be at least $MIN_PASSWORD_LENGTH characters long"))
            password != confirmPassword ->
                call.redirect(signupError.copy(error = "Password/confirm password dont match"))
            userId.length < MIN_USER_ID_LENGTH ->
                call.redirect(signupError.copy(error = "Username should be at least $MIN_USER_ID_LENGTH characters long"))
            !userNameValid(userId) ->
                call.redirect(signupError.copy(error = "Username should consist of digits, letters, dots or underscores"))
            db.user(userId) != null ->
                call.redirect(signupError.copy(error = "User with the following username is already registered"))


            else -> {
                val hash = hashFunction(password)
                val newUser = User(userId, surName, firstName, emailAddress, phoneNumber, address, role, hash)

                try {
                    db.createUser(newUser)
                    db.addactiveUser(userId, "OnLine")
                } catch (e: Throwable){
                    when{
                        db.user(userId) != null ->
                            call.redirect(signupError.copy(error = "User with the following username $ is already registered"))
                        db.userByEmail(emailAddress) != null ->
                            call.redirect(signupError.copy(error = "User with the following email $emailAddress is already registered"))

                        else ->{
                            application.log.error("Failed to register user", e)
                            call.redirect(signupError.copy(error = "Failed to register"))
                            println("an error occured $e")
                        }
                    }
                }
                call.sessions.set(EPSession(userId))
                call.redirect(Home())
            }
        }
    }

    get<Signup>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user != null){
            call.redirect(Home())
        } else{
            call.respond(FreeMarkerContent("signin.ftl", mapOf("user" to it.userId, "error" to it.error)))
        }
    }
}