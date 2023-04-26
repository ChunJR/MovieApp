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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val movieRepo: MovieRepo) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> get() = _state

    private val _categories = MutableLiveData<List<Genres>>()
    val categories: LiveData<List<Genres>> get() = _categories

    private val _movieDetails = MutableLiveData<MovieModel>()
    val movieModel: LiveData<MovieModel> get() = _movieDetails

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getTrendingMovies(): Flowable<PagingData<MovieModel>> {
        return movieRepo
            .getTrendingMovies()
            .cachedIn(viewModelScope)
//            .disposeBy(disposer)
    }

    fun getMovies(type: String): Flowable<PagingData<MovieModel>> {
        return movieRepo
            .getMovies(type)
            .cachedIn(viewModelScope)
//            .disposeBy(disposer)
    }

    fun getCategories() {
        compositeDisposable.add(movieRepo.getCategories()
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
            ))
    }

    fun getMovieDetails(movieId: Int) {
        compositeDisposable.add(movieRepo.getMovieDetails(movieId)
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
            ))
    }

    private fun handleGenresData(
        movieResponse: MovieModel?,
        _categories: MutableLiveData<List<Genres>>
    ) {
        movieResponse?.let {
            _categories.value = it.genresList!!
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