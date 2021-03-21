package chun.project.movieapp.screen.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.repository.MovieRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.Disposer
import io.sellmair.disposer.disposeBy

class HomeViewModel(private val movieRepo: MovieRepo): ViewModel() {

    private val disposer = Disposer()

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> get() = _state

    private val _trending = MutableLiveData<List<MovieModel>>()
    val trending: LiveData<List<MovieModel>> get() = _trending
    var trendingPage = 1

    override fun onCleared() {
        super.onCleared()
        disposer.dispose()
    }

    fun addEvents() {
        movieRepo.getTrendingList(trendingPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    _state.value = HomeViewState.Success
                    handleMovieData(movieResponse, _trending)
                },
                { error ->
                    error.message?.let {
                        _state.value = HomeViewState.Error(it)
                    }
                }
            ).disposeBy(disposer)
    }

    private fun handleMovieData(movieResponse: MovieResponseModel?,
                                _movies: MutableLiveData<List<MovieModel>>) {
        movieResponse?.let {
            _movies.value = it.results
        }
    }
}