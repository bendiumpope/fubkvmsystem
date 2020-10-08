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

const val ACTIVE = "/active"

@Location(ACTIVE)
class Active

fun Route.active(db: Repository, hashFunction: (String) -> String) {


    get<Active> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val actives = db.activeUsers()

        if (user == null) {
            call.redirect(Signin())
        } else {


            //val profile = db.profilepix(user.userId)

//            if(profile.isNotEmpty()){
                //profileUrls = profile.get(profile.lastIndex).imageUrl
//
//            }
            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    "active.ftl",
                    mapOf("actives" to actives, "user" to user), user.userId
                )
            )

        }

    }
}