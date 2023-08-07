package chun.project.movieapp.repository

import chun.project.movieapp.model.ConfigResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): ConfigResponseModel

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingList(@Path("media_type") mediaType: String,
                        @Path("time_window") timeWindow: String,
                        @Query("api_key") apiKey: String,
                        @Query("page") page: Int): Single<ResponseBody>

    @GET("genre/movie/list")
    fun getCategories(@Query("api_key") apiKey: String): Single<ResponseBody>

    @GET("movie/{type}")
    fun getMovies(@Path("type") type: String, // popular, top_rated, upcoming
                         @Query("api_key") apiKey: String,
                         @Query("page") page: Int): Single<ResponseBody>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int,
                         @Query("api_key") apiKey: String): Single<ResponseBody>
}