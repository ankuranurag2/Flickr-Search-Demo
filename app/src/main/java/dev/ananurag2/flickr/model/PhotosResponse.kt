package dev.ananurag2.flickr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created by ankur on 31/1/20
 */
data class PhotosResponse(
    @SerializedName("photos")
    val photos: FlickerPhotos?,
    @SerializedName("stat")
    val stat: String?,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("code")
    val code: Int?
)

data class FlickerPhotos(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("perpage")
    val perpage: Int?,
    @SerializedName("photo")
    val photo: List<FlickerPhoto>?,
    @SerializedName("total")
    val total: String?
)

@Parcelize
data class FlickerPhoto(
    @SerializedName("farm")
    val farm: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("secret")
    val secret: String?,
    @SerializedName("server")
    val server: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url_o")
    val urlO: String?,
    @SerializedName("url_q")
    val urlQ: String?
) : Parcelable {

    fun getImageUrl() = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
}
