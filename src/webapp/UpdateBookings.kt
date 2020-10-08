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

const val UPDATEBOOKINGS = "/updatebookings"

@Location(UPDATEBOOKINGS)
class UpdateBookings

fun Route.updatebookings(db: Repository, hashFunction: (String) -> String){

    get<UpdateBookings>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }


        if(user == null){
            call.redirect(Signin())
        } else{

            call.respond(FreeMarkerContent("updatebookings.ftl", null))

        }
    }


    post<UpdateBookings>{
        val params = call.receiveParameters()
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        when (action) {
            "update" -> {
                val id = params["id"] ?: throw java.lang.IllegalArgumentException("Missing parameter: id")
                val whomtovisit = params["whomtovisit"] ?: throw IllegalArgumentException("Missing parameter: whom to visit")
                val visitreason = params["reason"] ?: throw IllegalArgumentException("Missing parameter: visit reason")
                val visitingdate = params["visitingdate"] ?: throw IllegalArgumentException("Missing parameter: visiting date")
                val visittime = params["visittime"] ?: throw IllegalArgumentException("Missing parameter: Reason for visit time")
                val bookingstatus = params["bookingstatus"] ?: throw IllegalArgumentException("Missing parameter: Reason for booking status")
                val date = params["date"] ?: throw IllegalArgumentException("Missing parameter: date")
                val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
                db.updatevisitbooking(id.toInt(), whomtovisit, visitreason, visitingdate, visittime, bookingstatus, date)

                call.redirect(Bookings())
            }
        }

    }
}