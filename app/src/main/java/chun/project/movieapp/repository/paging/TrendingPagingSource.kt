package chun.project.movieapp.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.repository.MovieService
import chun.project.movieapp.repository.MovieServiceFun
import chun.project.movieapp.util.Constant
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TrendingPagingSource(
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

    private fun toLoadResult(data: MovieResponseModel, page: Int): LoadResult<Int, MovieModel> {
        data.results?.let {
            return LoadResult.Page(
                data = it,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == data.total_pages) null else page + 1
            )
        } ?: run {
            return LoadResult.Error(Throwable("Invalid data"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition
    }
}