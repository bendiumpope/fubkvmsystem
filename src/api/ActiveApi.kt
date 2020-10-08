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

const val ACTIVE_ENDPOINT = "$API_VERSION/activeapi"

@Location(ACTIVE_ENDPOINT)
class ActiveApi

fun Route.activeapi(db: Repository){
    authenticate("jwt") {

        get<ActiveApi> {
            val user = call.apiUser!!

            if ((user != null) && (user.address == "admin")){
                var userz = db.users()


                call.respond((db.activeUsers()).asReversed())
            }else{
                call.respondText("Only an admin User can Access this route", status = HttpStatusCode.Unauthorized)
            }
        }
    }
}