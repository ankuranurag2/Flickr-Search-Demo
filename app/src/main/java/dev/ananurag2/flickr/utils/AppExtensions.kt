package dev.ananurag2.flickr.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.model.FlickerPhoto

/**
 * created by ankur on 31/1/20
 */


fun Context.showToast(msg: String, longDuration: Boolean = false) {
    Toast.makeText(this, msg, if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Context.checkForInternet(): Boolean {
    var result = false
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return result
}

fun FlickerPhoto.toPhotoData(query: String, page: Int) = PhotoData(id?:"", title, getImageUrl(), query, page)