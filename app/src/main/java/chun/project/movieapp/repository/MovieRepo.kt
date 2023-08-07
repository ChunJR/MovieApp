package chun.project.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.repository.paging.MoviePagingSource
import chun.project.movieapp.repository.paging.TrendingPagingSource
import chun.project.movieapp.util.Constant
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

interface MovieRepo {
    suspend fun getConfiguration(): ConfigResponseModel?
    fun getCategories(): Single<MovieModel>
    fun getMovieDetails(movieId: Int): Single<MovieModel>
    fun getTrendingMovies(): Flowable<PagingData<MovieModel>>
    fun getMovies(type: String): Flowable<PagingData<MovieModel>>
}

class MovieRepoImpl(private val service: MovieService) : MovieRepo {
    override suspend fun getConfiguration(): ConfigResponseModel? {

        // Move the execution of the coroutine to the I/O dispatcher
        return withContext(Dispatchers.IO) {
            // Blocking network request code
            MovieServiceFun.parseConfig(service.getConfiguration(Constant.API_KEY))
        }
    }

    override fun getCategories(): Single<MovieModel> {
        return service.getCategories(Constant.API_KEY)
            .map { responseBody ->
                MovieServiceFun.parseMovieDetailsData(responseBody)
            }
    }

    override fun getMovieDetails(movieId: Int): Single<MovieModel> {
        return service.getMovieDetails(movieId, Constant.API_KEY)
            .map { responseBody ->
                MovieServiceFun.parseMovieDetailsData(responseBody)
            }
    }

    override fun getTrendingMovies(): Flowable<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { TrendingPagingSource(service) }
        ).flowable
    }

    override fun getMovies(type: String): Flowable<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { MoviePagingSource(type, service) }
        ).flowable
    }
}

object MovieServiceFun {

    fun parseConfig(responseBody: ResponseBody): ConfigResponseModel? {
        val jsonAsString = responseBody.string()
        val jsonObject = JsonParser().parse(jsonAsString).asJsonObject

        return if (!jsonObject.isJsonNull && jsonObject.has("images")) {
            val jsonImages = jsonObject.get("images")
            Gson().fromJson(jsonImages.toString(), ConfigResponseModel::class.java)
        } else {
            null
        }
    }

    fun parseMoviesData(responseBody: ResponseBody): MovieResponseModel? {
        val jsonAsString = responseBody.string()
        val jsonObject = JsonParser().parse(jsonAsString).asJsonObject

        return if (!jsonObject.isJsonNull) {
            Gson().fromJson(jsonObject.toString(), MovieResponseModel::class.java)
        } else {
            null
        }
    }

    fun parseMovieDetailsData(responseBody: ResponseBody): MovieModel? {
        val jsonAsString = responseBody.string()
        val jsonObject = JsonParser().parse(jsonAsString).asJsonObject

        return if (!jsonObject.isJsonNull) {
            Gson().fromJson(jsonObject.toString(), MovieModel::class.java)
        } else {
            null
        }
    }
}