package net.passioncloud.auth.accounts
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val authBaseUrl = "http://auth-app-dev-env.eba-25icge9s.ap-south-1.elasticbeanstalk.com"


private const val loginPath = "/auth/login"
private const val signupPath = "/auth/signup"
private const val confirmPath = "/auth/confirm"

data class LoginBody(val email: String, val password: String)
data class SignupBody(val email: String, val password: String)
data class ConfirmBody(val email: String, val confirmationCode: String)


interface AuthWebService {
    @POST(loginPath)
    suspend fun login(@Body loginBody: LoginBody) : String

    @POST(signupPath)
    suspend fun signup(@Body signupBody: SignupBody)

    @Multipart
    @POST(confirmPath)
    suspend fun confirm(@Body confirmBody: ConfirmBody) : String
}


///**
// * Okhttp3 interceptor
// */
//class AuthInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
//        val request = chain.request()
//        println("METHOD=${request.method}, URL=${request.url}")
//        fun getBodyContent(request: Request) : String {
//            val buffer = okio.Buffer()
//            val copy = request.newBuilder().build()
//            copy.body?.writeTo(buffer)
//            return buffer.readUtf8()
//        }
//        //println("BODY=${getBodyContent(request)}")
////        request.headers.forEach { println("Header ${it.first}, ${it.second}")}
////        println("Headers: ContentType: ${request.headers.get("Content-Type")}")
//        return chain.proceed(request)
//    }
//}

/**
 * Main entry point for network access
 */
val authWebService: AuthWebService by lazy {
    val client = OkHttpClient()
        .newBuilder()
//        .addInterceptor(AuthInterceptor())
        .build()

    // convert dates properly
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(authBaseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    retrofit.create(AuthWebService::class.java)
}


