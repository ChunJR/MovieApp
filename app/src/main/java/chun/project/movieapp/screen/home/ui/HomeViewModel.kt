package chun.project.movieapp.screen.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chun.project.movieapp.model.TrendingModel
import chun.project.movieapp.model.TrendingResponseModel
import chun.project.movieapp.repository.MovieRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.Disposer
import io.sellmair.disposer.disposeBy

class HomeViewModel(private val movieRepo: MovieRepo): ViewModel() {

    private val disposer = Disposer()

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> get() = _state

    private val _trending = MutableLiveData<List<TrendingModel>>()
    val trending: LiveData<List<TrendingModel>> get() = _trending

    var trendingPage = 1

    override fun onCleared() {
        super.onCleared()
        disposer.dispose()
    }

    fun addEvents() {
        movieRepo.getTrendingList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.value = HomeViewState.Loading }
            .subscribe(
                { trending ->
                    _state.value = HomeViewState.Success
                    handleData(trending)
                },
                { error ->
                    error.message?.let {
                        _state.value = HomeViewState.Error(it)
                    }
                }
            ).disposeBy(disposer)
    }

    private fun handleData(trending: TrendingResponseModel) {
        _trending.value = trending.results
        trendingPage = trending.page
    }
}