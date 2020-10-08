package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val UPDATESCHEDULE= "/updateschedule"

@Location(UPDATESCHEDULE)
class UpdateSchedule

fun Route.updateschedule(db: Repository, hashFunction: (String) -> String){

    get<UpdateSchedule>{
//        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
//
//        if(user == null){
//            call.redirect(Signin())
//        } else{

            call.respond(FreeMarkerContent("updateschedule.ftl", null))

//        }
    }

    post<UpdateSchedule>{
        val params = call.receiveParameters()
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        when (action) {
            "update" -> {
                val id = params["id"] ?: throw java.lang.IllegalArgumentException("Missing parameter: id")
                val office = params["office"] ?: throw IllegalArgumentException("Missing parameter: whom to visit")
                val date = params["date"] ?: throw IllegalArgumentException("Missing parameter: visit reason")
                val timeduration = params["timeduration"] ?: throw IllegalArgumentException("Missing parameter: visiting date")

                db.updatevisitingschedule(id.toInt(), office, date, timeduration)

                call.redirect(Schedules())
            }
        }

    }
}