package dev.ananurag2.flickr.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * created by ankur on 3/2/20
 */
@Dao
interface PhotoDao {

    //won't use live data here
    @Query("SELECT * FROM images WHERE queryString=:queryString AND page=:page")
    suspend fun getPhotosForQuery(queryString: String, page: Int): List<PhotoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photoList: List<PhotoData>)
}