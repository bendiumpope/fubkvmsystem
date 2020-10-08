package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import com.fubk_visitor_system.securityCode
import com.fubk_visitor_system.verifyCode
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val ADDSCHEDULE = "/addschedule"

@Location(ADDSCHEDULE)
class AddSchedule

fun Route.addschedule(db: Repository, hashFunction: (String) -> String) {

    get<AddSchedule> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if(user == null){
            call.redirect(Signin())
        } else{

            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent("addschedule.ftl",
                    mapOf("user" to user, "code" to code), user.userId)
            )

        }
    }

    post<AddSchedule> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        val params = call.receiveParameters()

        val code = params["code"] ?: return@post call.redirect(it)
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")
        val date = System.currentTimeMillis()

        if(user == null || !call.verifyCode(date, user,code,hashFunction)){
            call.redirect(Signin())
        }

        when (action) {
            "add" -> {

                //HOW TO HANDLE DATE AND TIME IN KOTLIN
                //Try to move this to a separate file
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formatted = current.format(formatter)

                val office = params["office"] ?: throw IllegalArgumentException("Missing parameter: Office")
                val availabledate = params["date"] ?: throw IllegalArgumentException("Missing parameter: Date available")
                val timeduration = params["timeduration"] ?: throw IllegalArgumentException("Missing parameter: Time duration")
                val datebooked = formatted.toString()

                db.addvisitingschedule(user!!.userId, office, availabledate, timeduration, datebooked)

            }
        }
        call.redirect(Schedules())
    }
}