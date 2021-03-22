package chun.project.movieapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.util.Constant
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val service: MovieService
) : RxPagingSource<Int, MovieModel>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieModel>> {
        val page = params.key ?: 1

        return service.getTrendingList("all", "day", Constant.API_KEY, page)
            .subscribeOn(Schedulers.io())
            .map { responseBody ->
                val data = MovieServiceFun.parseMoviesData(responseBody)
                data?.let {
                    toLoadResult(it, page)
                } ?: run {
                    LoadResult.Error(Throwable("parse data failed"))
                }
            }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: MovieResponseModel?, page: Int): LoadResult<Int, MovieModel> {
        return LoadResult.Page(
            data = data.results,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (page == data.total_pages) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        TODO("Not yet implemented")
    }
}