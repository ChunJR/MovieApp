package chun.project.movieapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrendingResponseModel(
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("total_results") var total_results: Int,
    @SerializedName("results") var results: List<TrendingModel>,
) : Parcelable

@Parcelize
data class TrendingModel(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("poster_path") var poster_path: String,
    @SerializedName("backdrop_path") var backdrop_path: String,
) : Parcelable

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>.checkItemsAre() =
    if (all { it is T })
        this as List<T>
    else null


