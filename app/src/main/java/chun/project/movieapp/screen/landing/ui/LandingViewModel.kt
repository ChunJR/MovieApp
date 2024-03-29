package chun.project.movieapp.screen.landing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.repository.MovieRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LandingViewModel(private val movieRepo: MovieRepo): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<LandingViewState>()
    val state: LiveData<LandingViewState> get() = _state

    private val _config = MutableLiveData<ConfigResponseModel>()
    val config: LiveData<ConfigResponseModel> get() = _config

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getConfiguration() {
        compositeDisposable.add(movieRepo.getConfiguration()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.value = LandingViewState.Loading }
            .subscribe(
                { configResponse ->
                    _state.value = LandingViewState.Success
                    handleData(configResponse)
                },
                { error ->
                    error.message?.let {
                        _state.value = LandingViewState.Error(it)
                    }
                }))
    }

    private fun handleData(configResponse: ConfigResponseModel?) {
        _config.value = configResponse!!
    }
}