package chun.project.movieapp.repository

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String): Single<ResponseBody>

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingList(@Path("media_type") mediaType: String,
                        @Path("time_window") timeWindow: String,
                        @Query("api_key") apiKey: String,
                        @Query("page") page: Int): Single<ResponseBody>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                          @Query("page") page: Int): Single<ResponseBody>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                          @Query("page") page: Int): Single<ResponseBody>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String,
                          @Query("page") page: Int): Single<ResponseBody>
}