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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val SCHEDULES = "/schedules"

@Location(SCHEDULES)
class Schedules

fun Route.schedules(db: Repository, hashFunction: (String) -> String) {


    get<Schedules> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user == null) {
            call.redirect(Signin())
        } else {

            var schedule = db.visitingschedule()

            val schedules = schedule.asReversed()


            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    "schedules.ftl",
                    mapOf("schedules" to schedules, "user" to user, "date" to date, "code" to code), user.userId
                )
            )

        }

    }

    post<Schedules>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        val params = call.receiveParameters()

        val action = params["action"] ?: throw IllegalArgumentException("Missing parameter: action")

        when(action){
            "delete" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                db.removevisitschedule(id.toInt())
                call.redirect(Schedules())
            }

            "edit"->{
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                val schedule = db.visitingschedule(id.toInt())

                if(user == null){
                    call.redirect(Signin())
                } else{
                    val date = System.currentTimeMillis()
                    val code = call.securityCode(date, user, hashFunction)

                    call.respond(
                        FreeMarkerContent("updateschedule.ftl",
                            mapOf( "schedule" to schedule, "user" to user, "code" to code, "id" to id), user.userId)
                    )
                }
            }
        }
    }
}