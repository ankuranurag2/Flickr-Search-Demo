package dev.ananurag2.flickr.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.ananurag2.flickr.data.db.PhotoDao
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.data.network.ApiService
import dev.ananurag2.flickr.model.ApiResult
import dev.ananurag2.flickr.paging.ImageDataSourceFactory
import dev.ananurag2.flickr.utils.toPhotoData
import kotlinx.coroutines.CoroutineScope

/**
 * created by ankur on 31/1/20
 */
class ImageRepository (private val photoDao: PhotoDao, private val apiService: ApiService) {

    suspend fun getImageList(query: String, page: Int, isConnected: Boolean): ApiResult<List<PhotoData>> {
        var imageList: List<PhotoData>

        if (isConnected) {
            val response = apiService.getImageList(query, page)
            if (response.isSuccessful) {
                val body = response.body()
                if ("ok".equals(body?.stat, true)) {
                    val flickrList = body?.photos?.photo
                    val photoList = ArrayList<PhotoData>()
                    flickrList?.forEach {
                        photoList.add(it.toPhotoData(query, page))
                    }
                    imageList = photoList
                } else
                    return ApiResult.error(body?.msg ?: "Error ${body?.code}", null)
            } else
                return ApiResult.error("Something went wrong", null)
        } else
            imageList = photoDao.getPhotosForQuery(query, page)

        return if (imageList.isNullOrEmpty()) {
            //we have reached to the last page
            if (page > 1)
                ApiResult.done(null)
            else
                ApiResult.error("Oops! No result found.")
        } else {
            if (isConnected)                //Insert only data fetched from Network call
                photoDao.insertPhotos(imageList)
            ApiResult.success(imageList)
        }
    }

    private fun getDataFromRemote(query: String, scope: CoroutineScope): LiveData<PagedList<PhotoData>> {
        val dataSourceFactory = ImageDataSourceFactory(query, apiService, scope)

        return LivePagedListBuilder(dataSourceFactory, ImageDataSourceFactory.pagedListConfig()).build()
    }

    companion object {

        @Volatile
        private var instance: ImageRepository? = null

        fun getInstance(photoDao: PhotoDao, apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: ImageRepository(photoDao, apiService).also { instance = it }
            }
    }
}