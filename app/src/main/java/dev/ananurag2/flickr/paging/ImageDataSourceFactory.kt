package dev.ananurag2.flickr.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import dev.ananurag2.flickr.data.network.ApiService
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.utils.PER_PAGE_RESULT_COUNT
import kotlinx.coroutines.CoroutineScope


/**
 * created by ankur on 2/2/20
 */
class ImageDataSourceFactory(private val query: String, private val apiService: ApiService, private val scope: CoroutineScope) : DataSource.Factory<Int, PhotoData>() {

    private val liveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, PhotoData> {
        val source = ImageDataSource(query, apiService, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        fun pagedListConfig() = PagedList.Config.Builder()
            .setPageSize(PER_PAGE_RESULT_COUNT)
            .build()
    }
}