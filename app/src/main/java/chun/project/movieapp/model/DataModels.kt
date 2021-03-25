package chun.project.movieapp.model

import android.os.Parcelable
import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class HomeDataV2(val viewType: Int,
                      var listItem: Pair<PagingData<MovieModel>, List<Genres>>,
                      var isRefresh: Boolean? = false)

@Parcelize
data class ConfigResponseModel(
    @SerializedName("base_url") var base_url: String? = null,
    @SerializedName("secure_base_url") var secure_base_url: String? = null,
    @SerializedName("backdrop_sizes") var backdrop_sizes: List<String>? = null,
    @SerializedName("poster_sizes") var poster_sizes: List<String>? = null,
) : Parcelable

@Parcelize
data class MovieResponseModel(
    @SerializedName("page") var page: Int = -1,
    @SerializedName("total_pages") var total_pages: Int = -1,
    @SerializedName("total_results") var total_results: Int = -1,
    @SerializedName("results") var results: List<MovieModel>? = null,
) : Parcelable

@Parcelize
data class MovieModel(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("title") var title: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var poster_path: String? = null,
    @SerializedName("backdrop_path") var backdrop_path: String? = null,
    @SerializedName("release_date") var release_date: String? = null,
    @SerializedName("vote_average") var rating: Float = -1f,
    @SerializedName("genres") var genresList: List<Genres>? = null,
) : Parcelable

@Parcelize
data class Genres(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("name") var name: String? = null
) : Parcelable


