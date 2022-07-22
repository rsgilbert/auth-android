package net.passioncloud.auth

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

private const val userPath = "/user/user"

data class UserResponse(val email: String)

interface UserWebService {
    @GET(userPath)
    fun user(@Header("Authorization") authorization: String) :  UserResponse

}