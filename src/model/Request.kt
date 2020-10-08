package com.fubk_visitor_system.model

data class Request(val whomtovisit:String, val visitreason:String,
                   val visitingdate:String, val visitingtime:String,
                   val bookingstatus:String, val date: String) {
}