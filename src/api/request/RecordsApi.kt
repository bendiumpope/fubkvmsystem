package com.fubk_visitor_system.api.request

import com.fubk_visitor_system.API_VERSION
import com.fubk_visitor_system.apiUser
import com.fubk_visitor_system.model.VisitRecord
import com.fubk_visitor_system.repository.Repository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get

const val RECORDS_ENDPOINT = "$API_VERSION/recordsapi"

@Location(RECORDS_ENDPOINT)
class RecordsApi

fun Route.recordsapi(db: Repository){
    authenticate("jwt") {

        get<RecordsApi> {
            val user = call.apiUser!!
            var visitrecord : List<VisitRecord>

            if(user != null) {

                if ((user.address == "admin") || (user.address == "secretary")) {
                    visitrecord = db.visitrecords()
                } else {
                    visitrecord = db.visitrecord(user.userId)
                }
                call.respond((visitrecord).asReversed())
            }else{
                call.respondText("Something went wrong", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}