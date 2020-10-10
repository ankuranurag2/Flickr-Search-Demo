package dev.ananurag2.flickr.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * created by ankur on 1/2/20
 */

/**
 * This class is used as a lighter version of {@link dev.ananurag2.flickr.model.FlickerPhoto} as well as  database entity
 * Some fields in {@link dev.ananurag2.flickr.model.FlickerPhoto} are of no use once the Image Url is constructed using them.
 */

@Parcelize
@Entity(tableName = "images")
data class PhotoData(
    @PrimaryKey
    val id: String,
    val title: String?,
    val url: String?,
    val queryString: String,
    val page: Int?
) : Parcelable