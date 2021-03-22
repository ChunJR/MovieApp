package chun.project.movieapp.screen.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.repository.GetMoviesRxRepository
import chun.project.movieapp.repository.MovieRepo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.Disposer
import io.sellmair.disposer.disposeBy

class HomeViewModelV2(private val repository: GetMoviesRxRepository): ViewModel() {

    private val disposer = Disposer()

    override fun onCleared() {
        super.onCleared()
        disposer.dispose()
    }

    fun getFavoriteMovies(): Flowable<PagingData<MovieModel>> {
        return repository
            .getMovies()
            .map { pagingData -> pagingData.filter { it.backdrop_path.isNotEmpty() } }
            .cachedIn(viewModelScope)
            .disposeBy(disposer)
    }

//    fun addEvents() {
//        repository.getTrendingList(trendingPage)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { movieResponse ->
//                    _state.value = HomeViewState.Success
//                    handleMovieData(movieResponse, _trending)
//                },
//                { error ->
//                    error.message?.let {
//                        _state.value = HomeViewState.Error(it)
//                    }
//                }
//            ).disposeBy(disposer)
//    }

//    private fun handleMovieData(movieResponse: MovieResponseModel?,
//                                _movies: MutableLiveData<List<MovieModel>>) {
//        movieResponse?.let {
//            _movies.value = it.results
//        }
//    }
}