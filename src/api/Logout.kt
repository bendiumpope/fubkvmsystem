package com.fubk_visitor_system.api

import com.fubk_visitor_system.JwtService
import com.fubk_visitor_system.apiUser
import com.fubk_visitor_system.hash
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val LOGOUT_ENDPOINT = "/logout"

@Location(LOGOUT_ENDPOINT)
class Logout

fun Route.logout(db: Repository, jwtService: JwtService){
    authenticate("jwt") {

        get<Logout> {
            val user = call.apiUser!!
            val activeUser = db.activeUser(user!!.userId)

            try {
                db.updateactiveUser(activeUser!!.id, user.userId, "OffLine")
            } catch (e: Throwable){
                println("an error occured $e")
            }

//      CLEAR TOKEN AT THIS POINT

//            val token = jwtService.
        }
    }
}