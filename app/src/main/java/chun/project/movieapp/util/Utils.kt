package chun.project.movieapp.util

import android.content.Context

object Utils {

    fun getPosterPath(context: Context, backdropPath: String?, getOriginal: Boolean = false): String {
        val baseUrl = context.myAppPreferences.getString(
            Constant.SHARED_PREFERENCE_IMAGE_SECURE_URL,
            Constant.BASE_IMAGE_URL
        )

        return if (getOriginal) {
            baseUrl + Constant.DEFAULT_IMAGE_SIZE + backdropPath
        } else {
            val imageSize = context.myAppPreferences.getString(
                Constant.SHARED_PREFERENCE_IMAGE_POSTER_SIZE,
                Constant.DEFAULT_IMAGE_SIZE
            )
            baseUrl + imageSize + backdropPath
        }
    }

    fun getBackdropPath(context: Context, backdropPath: String?, getOriginal: Boolean = false): String {
        val baseUrl = context.myAppPreferences.getString(
            Constant.SHARED_PREFERENCE_IMAGE_SECURE_URL,
            Constant.BASE_IMAGE_URL
        )

        return if (getOriginal) {
            baseUrl + Constant.DEFAULT_IMAGE_SIZE + backdropPath
        } else {
            val imageSize = context.myAppPreferences.getString(
                Constant.SHARED_PREFERENCE_IMAGE_BACKDROP_SIZE,
                Constant.DEFAULT_IMAGE_SIZE
            )
            baseUrl + imageSize + backdropPath
        }
    }
}