import com.teamxticket.xticket.data.model.ApiResponse
import com.teamxticket.xticket.data.model.CodeResponse
import com.teamxticket.xticket.data.model.SaleDateResponse
import com.teamxticket.xticket.data.model.SearchEventPlannerResponse
import com.teamxticket.xticket.data.model.User
import com.teamxticket.xticket.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface UsersApiClient {

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<UserResponse>
    
    @POST("auth/signup")
    suspend fun postUser(@Body user: User): Response<ApiResponse>

    @PUT("auth/update")
    suspend fun putUser(@Body user: User): Response<CodeResponse>
    @GET("user/searchEventPlanner/{query}")
    fun searchEventPlanners(@Path("query") query: String, @QueryMap params: Map<String, String>): Response<SearchEventPlannerResponse>

}
