package chun.project.movieapp.repository

import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.model.TrendingResponseModel
import chun.project.movieapp.util.Constant
import com.google.gson.Gson
import com.google.gson.JsonParser
import io.reactivex.Single
import okhttp3.ResponseBody

interface MovieRepo {
    fun getConfiguration(): Single<ConfigResponseModel>
    fun getTrendingList(): Single<TrendingResponseModel>
}

class MovieRepoImpl(private val service: MovieService): MovieRepo {
    override fun getConfiguration(): Single<ConfigResponseModel> {
        return service.getConfiguration(Constant.API_KEY)
            .map { responseBody ->
                MovieServiceFun.parseConfig(responseBody)
            }
    }

    override fun getTrendingList(): Single<TrendingResponseModel> {
        return service.getTrendingList("all", "day", Constant.API_KEY)
                .map { responseBody ->
                    MovieServiceFun.parseTrendingData(responseBody)
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

    fun parseTrendingData(responseBody: ResponseBody): TrendingResponseModel? {
        val jsonAsString = responseBody.string()
        val jsonObject = JsonParser().parse(jsonAsString).asJsonObject

        return if (!jsonObject.isJsonNull) {
            Gson().fromJson(jsonObject.toString(), TrendingResponseModel::class.java)
        } else {
            null
        }
    }
}