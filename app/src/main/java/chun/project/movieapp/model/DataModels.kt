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
    @SerializedName("base_url") var base_url: String?,
    @SerializedName("secure_base_url") var secure_base_url: String?,
    @SerializedName("backdrop_sizes") var backdrop_sizes: List<String>?,
    @SerializedName("poster_sizes") var poster_sizes: List<String>?,
) : Parcelable

@Parcelize
data class MovieResponseModel(
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("total_results") var total_results: Int,
    @SerializedName("results") var results: List<MovieModel>,
) : Parcelable

@Parcelize
data class MovieModel(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("poster_path") var poster_path: String,
    @SerializedName("backdrop_path") var backdrop_path: String,
    @SerializedName("genres") var genresList: List<Genres>,
) : Parcelable

@Parcelize
data class Genres(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
) : Parcelable


