package chun.project.movieapp.repository

import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.util.Constant
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.Single
import okhttp3.ResponseBody

interface MovieRepo {
    fun getConfiguration(): Single<ConfigResponseModel>
    fun getTrendingList(page: Int): Single<MovieResponseModel>
    fun getPopularList(page: Int): Single<MovieResponseModel>
    fun getTopRatedList(page: Int): Single<MovieResponseModel>
    fun getUpcomingList(page: Int): Single<MovieResponseModel>
}

class MovieRepoImpl(private val service: MovieService): MovieRepo {
    override fun getConfiguration(): Single<ConfigResponseModel> {
        return service.getConfiguration(Constant.API_KEY)
            .map { responseBody ->
                MovieServiceFun.parseConfig(responseBody)
            }
    }

    override fun getTrendingList(page: Int): Single<MovieResponseModel> {
        return service.getTrendingList("all", "day", Constant.API_KEY, page)
                .map { responseBody ->
                    MovieServiceFun.parseMoviesData(responseBody)
                }
    }

    override fun getPopularList(page: Int): Single<MovieResponseModel> {
        return service.getPopularMovies(Constant.API_KEY, page)
                .map { responseBody ->
                    MovieServiceFun.parseMoviesData(responseBody)
                }
    }

    override fun getTopRatedList(page: Int): Single<MovieResponseModel> {
        return service.getTopRatedMovies(Constant.API_KEY, page)
                .map { responseBody ->
                    MovieServiceFun.parseMoviesData(responseBody)
                }
    }

    override fun getUpcomingList(page: Int): Single<MovieResponseModel> {
        return service.getUpcomingMovies(Constant.API_KEY, page)
                .map { responseBody ->
                    MovieServiceFun.parseMoviesData(responseBody)
                }
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
}