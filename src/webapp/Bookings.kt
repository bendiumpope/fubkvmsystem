package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.model.VisitBooking
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

const val BOOKINGS = "/bookings"

@Location(BOOKINGS)
class Bookings

fun Route.bookings(db: Repository, hashFunction: (String) -> String) {


    get<Bookings> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user == null) {
            call.redirect(Signin())
        } else {

            val role = user.address.toLowerCase()
            var booking : List<VisitBooking>
            println("benpopes role is ${user}")

            if(role == "admin" || role == "secretary"){
               booking = db.visitbookings()

            }else{
                booking = db.visitbooking(user.userId)

            }
            var bookings : List<VisitBooking> = booking.asReversed()

            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    "bookings.ftl",
                    mapOf("booking" to bookings, "user" to user, "date" to date, "code" to code), user.userId
                )
            )
        }

    }

    post<Bookings>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val params = call.receiveParameters()
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        when(action){
            "delete" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
//                println("the id is ${id}")
                db.removevisitbooking(id)
                call.redirect(Bookings())
            }

            "completed" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")

//                println("the id ${id}")
                val booking = db.visitbooking(id.toInt())
//                println("booking $booking")
                db.addvisitrecord(booking!!.userId, booking.date, booking.visittime, booking.whomtovisit)
//                db.removevisitbooking(id)
                call.redirect(Records())
            }

            "edit"->{
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
//                println("the id ${id}")
                val booking = db.visitbooking(id.toInt())
//                println("booking $booking")

                if(user == null){
                    call.redirect(Signin())
                } else{
                    val date = System.currentTimeMillis()
                    val code = call.securityCode(date, user, hashFunction)

                    call.respond(
                        FreeMarkerContent("updatebookings.ftl",
                            mapOf( "booking" to booking, "user" to user, "code" to code, "id" to id), user.userId)
                    )
                }
            }
        }
    }
}