package chun.project.movieapp.screen.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import chun.project.movieapp.model.Genres
import chun.project.movieapp.model.MovieModel
import chun.project.movieapp.repository.MovieRepo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.Disposer
import io.sellmair.disposer.disposeBy

class HomeViewModel(private val movieRepo: MovieRepo) : ViewModel() {

    private val disposer = Disposer()

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> get() = _state

    private val _categories = MutableLiveData<List<Genres>>()
    val categories: LiveData<List<Genres>> get() = _categories

    private val _movieDetails = MutableLiveData<MovieModel>()
    val movieModel: LiveData<MovieModel> get() = _movieDetails

    override fun onCleared() {
        super.onCleared()
        disposer.dispose()
    }

    fun getTrendingMovies(): Flowable<PagingData<MovieModel>> {
        return movieRepo
            .getTrendingMovies()
            .cachedIn(viewModelScope)
            .disposeBy(disposer)
    }

    fun getMovies(type: String): Flowable<PagingData<MovieModel>> {
        return movieRepo
            .getMovies(type)
            .cachedIn(viewModelScope)
            .disposeBy(disposer)
    }

    fun getCategories() {
        movieRepo.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    handleGenresData(movieResponse, _categories)
                },
                { error ->
                    error.message?.let {
                        _state.value = HomeViewState.Error(it)
                    }
                }
            ).disposeBy(disposer)
    }

    fun getMovieDetails(movieId: Int) {
        movieRepo.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.value = HomeViewState.Loading }
            .subscribe(
                { movieResponse ->
                    _state.value = HomeViewState.Success
                    handleMovieData(movieResponse, _movieDetails)
                },
                { error ->
                    error.message?.let {
                        _state.value = HomeViewState.Error(it)
                    }
                }
            ).disposeBy(disposer)
    }

    private fun handleGenresData(
        movieResponse: MovieModel?,
        _categories: MutableLiveData<List<Genres>>
    ) {
        movieResponse?.let {
            _categories.value = it.genresList
        }
    }

    private fun handleMovieData(
        movieResponse: MovieModel?,
        _movieDetails: MutableLiveData<MovieModel>
    ) {
        movieResponse?.let {
            _movieDetails.value = it
        }
    }

}