package com.fubk_visitor_system.api

import com.fubk_visitor_system.API_VERSION
import com.fubk_visitor_system.apiUser
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

const val USERS_ENDPOINT = "$API_VERSION/usersapi"

@Location(USERS_ENDPOINT)
class UsersApi

fun Route.usersapi(db: Repository){
    authenticate("jwt") {

        get<UsersApi> {
            val user = call.apiUser!!

            if ((user != null) && (user.address == "admin")){
                var userz = db.users()

                call.respond((db.users()).asReversed())
        }else{
                call.respondText("Only an admin User can Access this route", status = HttpStatusCode.Unauthorized)
            }
        }
    }
}