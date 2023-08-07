package chun.project.movieapp.screen.landing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import chun.project.movieapp.model.ConfigResponseModel
import chun.project.movieapp.repository.MovieRepo
import chun.project.movieapp.util.Resource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

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

    fun getConfiguration() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = movieRepo.getConfiguration()
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun handleData(configResponse: ConfigResponseModel?) {
        _config.value = configResponse!!
    }
}