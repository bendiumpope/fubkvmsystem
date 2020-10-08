package com.fubk_visitor_system.api

import com.fubk_visitor_system.API_VERSION
import com.fubk_visitor_system.api.request.BookingApiRequest
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

const val BOOKING_ENDPOINT = "$API_VERSION/booking"

@Location(BOOKING_ENDPOINT)
class BookingApi

fun Route.bookingApi(db: Repository) {

    authenticate("jwt") {

        get<BookingApi>{
            val user = call.apiUser

            if(user != null){
                if (user.address == "admin" || user.address == "secretary"){
                    call.respond((db.visitbookings()).asReversed())
                }else{
                    call.respond((db.visitbooking(user.userId)).asReversed())
                }
            }else{
                call.respondText("Something went wrong", status = HttpStatusCode.InternalServerError)
            }
        }

        post<BookingApi>{

            val user = call.apiUser

            if(user != null) {

                try {
                    val request = call.receive<BookingApiRequest>()

                    //HOW TO HANDLE DATE AND TIME IN KOTLIN
                    //Try to move this to a separate file
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val formatted = current.format(formatter)

//                    println("${request.whomtovisit}  ${request.visitreason}.")
//                println("${formatted.toString()}")


                    val booking = db.addvisitbooking(
                        user.userId, request.whomtovisit, request.visitreason,
                        "", "", "Pending...", "${formatted.toString()}"
                    )

                    if (booking != null) {
                        call.respond(booking)
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