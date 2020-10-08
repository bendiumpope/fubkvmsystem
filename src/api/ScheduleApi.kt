package com.fubk_visitor_system.api

import com.fubk_visitor_system.API_VERSION
import com.fubk_visitor_system.api.request.ScheduleApiRequest
import com.fubk_visitor_system.apiUser
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val SCHEDULE_ENDPOINT = "$API_VERSION/scheduleapi"

@Location(SCHEDULE_ENDPOINT)
class ScheduleApi

fun Route.scheduleapi(db: Repository){
    authenticate("jwt") {

        get<ScheduleApi>{
            val user = call.apiUser!!

            var schedule = db.visitingschedule()

            call.respond(schedule.asReversed())
        }

        post<ScheduleApi>{

            val user = call.apiUser

            if(user != null) {

                try {
                    val request = call.receive<ScheduleApiRequest>()

                    //HOW TO HANDLE DATE AND TIME IN KOTLIN
                    //Try to move this to a separate file
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val formatted = current.format(formatter)


                    val schedule = db.addvisitingschedule(user.userId, request.office,
                        request.date, request.timeduration, "${formatted.toString()}")

                    if (schedule != null) {
                        call.respond(schedule)
                    } else {
                        call.respondText("Invalid data received", status = HttpStatusCode.InternalServerError)
                    }
                } catch (e: Throwable) {
                    call.respondText("Invalid data received its a catch", status = HttpStatusCode.BadRequest)
                }

            }else{

                call.respondText("Invalid data received", status = HttpStatusCode.InternalServerError)
            }
        }
    }

}