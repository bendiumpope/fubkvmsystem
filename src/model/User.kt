package com.fubk_visitor_system.model

import io.ktor.auth.*
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class User(
    val userId: String,
    val surName: String="",
    val firstName: String="",
    val emailAddress: String="",
    val phoneNumber: String="",
    val address: String="",
    val role: String="",
    val passwordHash: String
): Serializable, Principal

object Users: Table(){
    val id = varchar("id", 20).primaryKey()
    var surName = varchar("sur_name", 256)
    var firstName = varchar("first_name", 256)
    var emailAddress = varchar("email_address", 256).uniqueIndex()
    var phoneNumber = varchar("phone_num", 256)
    var address = varchar("address", 256)
    var role = varchar("role", 256)
    var passwordHash = varchar("password_hash", 64)

}