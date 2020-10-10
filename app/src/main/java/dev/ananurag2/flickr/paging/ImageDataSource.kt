package dev.ananurag2.flickr.paging

import androidx.paging.PageKeyedDataSource
import dev.ananurag2.flickr.data.network.ApiService
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.utils.toPhotoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * created by ankur on 2/2/20
 */

public class ImageDataSource(private val query: String,private val apiService: ApiService,private val scope: CoroutineScope) : PageKeyedDataSource<Int, PhotoData>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoData>) {
        fetchData(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, callback: (List<PhotoData>) -> Unit) {
        scope.launch {
            val response = apiService.getImageList(query, page)
            if (response.isSuccessful) {
                val body = response.body()
                if ("ok".equals(body?.stat, true)) {
                    val flickrList = body?.photos?.photo
                    val photoList = ArrayList<PhotoData>()
                    flickrList?.forEach {
                        photoList.add(it.toPhotoData(query,page))
                    }
                    callback(photoList)
                }
            }
        }
    }
}