import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.EventFollow
import com.teamxticket.xticket.data.model.SaleDateResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserEventFollowsResponse
import com.teamxticket.xticket.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApiClient {

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<UserResponse>
    
    @POST("auth/signup")
    suspend fun postUser(@Body user: User): Response<ApiResponse>

    @PUT("auth/update")
    suspend fun putUser(@Body user: User): Response<CodeResponse>

    @GET("user/eventFollows/{userId}")
    suspend fun getUserEventFollows(@Path("userId") userId: Int) : Response<UserEventFollowsResponse>

    @POST("user//eventFollow/{userId}")
    suspend fun followEvent(@Path("userId") userId: Int, @Body eventId: EventFollow) : Response<ApiResponse>
}
