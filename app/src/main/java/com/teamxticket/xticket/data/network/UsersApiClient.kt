import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.OneTimeUseCode
import com.teamxticket.xticket.data.model.OneTimeUseCodeResponse
import com.teamxticket.xticket.data.model.SaleDateResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsersApiClient {

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<UserResponse>
    
    @POST("auth/signup")
    suspend fun postUser(@Body user: User): Response<ApiResponse>

    @PUT("auth/update")
    suspend fun putUser(@Body user: User): Response<CodeResponse>
    @POST("user/requestOTUCode")
    suspend fun requestOTUCode(email: String): Response<OneTimeUseCodeResponse>
    @POST("auth/codeLogin")
    suspend fun codeLogin(@Body oneTUCode: OneTimeUseCode): Response<UserResponse>
}
