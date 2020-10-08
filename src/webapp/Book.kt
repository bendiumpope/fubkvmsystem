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

const val BOOK = "/book"

@Location(BOOK)
class Book

fun Route.book(db: Repository, hashFunction: (String) -> String) {

    get<Book> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if(user == null){
            call.redirect(Signin())
        } else{

            val bookings = db.visitbookings()

            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent("book.ftl",
                    mapOf("booking" to bookings, "user" to user, "date" to date, "code" to code), user.userId)
            )
        }
    }

    post<Book> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val params = call.receiveParameters()
        val date = params["date"] ?.toLongOrNull() ?: return@post call.redirect(it)
        val code = params["code"] ?: return@post call.redirect(it)
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        if(user == null || !call.verifyCode(date, user,code,hashFunction)){
            call.redirect(Signin())

        }else {

            when (action) {
                "add" -> {
                    //HOW TO HANDLE DATE AND TIME IN KOTLIN
                    //Try to move this to a separate file
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val formatted = current.format(formatter)

                    val whomtovisit = params["whomtovisit"] ?: throw IllegalArgumentException("Missing parameter: Staff Name")
                    val reason = params["reason"] ?: throw IllegalArgumentException("Missing parameter: Visit Reason")
                    val visitingdate = ""
                    val visittime = ""
                    val bookingstatus = "Pending...."
                    val datebooked = formatted.toString()

                    db.addvisitbooking(
                        user!!.userId,
                        whomtovisit,
                        reason,
                        visitingdate,
                        visittime,
                        bookingstatus,
                        datebooked
                    )


                }
            }
            call.redirect(Bookings())
        }
    }
}