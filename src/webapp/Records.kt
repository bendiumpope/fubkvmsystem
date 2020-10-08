package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.model.VisitRecord
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

const val RECORDS = "/records"

@Location(RECORDS)
class Records

fun Route.records(db: Repository, hashFunction: (String) -> String) {


    get<Records>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if(user == null){
            call.redirect(Signin())
        } else{
            var visitrecord : List<VisitRecord>

            if((user.address == "admin") || (user.address == "secretary")){
                visitrecord = db.visitrecords()
            }else{

                visitrecord = db.visitrecord(user.userId)
            }

            val visitrecords = visitrecord.asReversed()

            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent("records.ftl",
                    mapOf("visitrecords" to visitrecords, "user" to user, "date" to date, "code" to code), user.userId)
            )

        }

    }

    post<Records>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val params = call.receiveParameters()
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        when(action){
            "delete" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                db.removevisitrecord(id)
                call.redirect(Records())
            }
        }
    }
}