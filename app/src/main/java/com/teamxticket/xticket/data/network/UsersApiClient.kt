import com.teamxticket.xticket.data.model.SaleDateResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface UsersApiClient {

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<UserResponse>
    
    @POST("auth/signup")
    suspend fun postUser(@Body user: User): Response<ApiResponse>
    
}
