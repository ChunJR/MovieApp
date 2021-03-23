package chun.project.movieapp.screen.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.model.MovieResponseModel
import chun.project.movieapp.repository.MovieRepo
import io.reactivex.Flowable
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


    fun getFavoriteMovies(): Flowable<PagingData<MovieModel>> {
        return movieRepo
            .getMovies()
            .cachedIn(viewModelScope)
            .disposeBy(disposer)
    }

}