package chun.project.movieapp.repository

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingList(@Path("media_type") mediaType: String,
                        @Path("time_window") timeWindow: String,
                        @Query("api_key") apiKey: String): Single<ResponseBody>
}