package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import com.fubk_visitor_system.securityCode
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.lang.IllegalArgumentException

const val USERS = "/users"

@Location(USERS)
class Users

fun Route.users(db: Repository, hashFunction: (String) -> String) {


    get<Users> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user == null) {
            call.redirect(Signin())
        } else {

            var userz = db.users()
            val users = userz.asReversed()

//            println{"${users}"}
            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    "users.ftl",
                    mapOf("users" to users, "user" to user, "date" to date, "code" to code), user.userId
                )
            )

        }

    }

    post<Users>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val params = call.receiveParameters()
        val action = params["action"] ?: throw IllegalArgumentException("Missing parameter: action")

        when(action){
            "delete" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                db.removeuser(id)
                call.redirect(Users())
            }
            "edit"->{
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")

                val updateUser = db.user(id)

                if(user == null){
                    call.redirect(Signin())
                } else{
                    val date = System.currentTimeMillis()
                    val code = call.securityCode(date, user, hashFunction)

                    call.respond(
                        FreeMarkerContent("updateuser.ftl",
                            mapOf( "updateUser" to updateUser, "user" to user, "date" to date, "code" to code, "id" to id), user.userId)
                    )
                }
            }
        }
    }
}