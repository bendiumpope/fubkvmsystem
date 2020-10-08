package com.fubk_visitor_system.webapp

import com.fubk_visitor_system.model.EPSession
import com.fubk_visitor_system.redirect
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val SIGNOUT = "/signout"

@Location(SIGNOUT)
class Signout

fun Route.signout(db: Repository){

    get<Signout>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val activeUser = db.activeUser(user!!.userId)

        try {
            db.updateactiveUser(activeUser!!.id, user.userId, "OffLine")
        } catch (e: Throwable){
            println("an error occured $e")
        }

        call.sessions.clear<EPSession>()
        call.redirect(Signin())
    }
}