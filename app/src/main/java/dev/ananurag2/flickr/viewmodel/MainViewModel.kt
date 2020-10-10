package dev.ananurag2.flickr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ananurag2.flickr.data.db.PhotoData
import dev.ananurag2.flickr.model.Status
import dev.ananurag2.flickr.repository.ImageRepository
import kotlinx.coroutines.launch

/**
 * created by ankur on 31/1/20
 */
class MainViewModel (private val repository: ImageRepository) : ViewModel() {

    var spanCount = 2
    var pageCount = 1

    /**
        {@link shouldLoad}
        If  null    ->  don't load anymore data [i.e Reached last page, no more data to load]
            false   ->  wait until loading is complete and value is true again
            true    ->  can load more data
     */
    var shouldLoad: Boolean? = true
    var errorMsg = MutableLiveData<String>()

    private var previousQuery = ""

    //append newly fetched data in this List
    private val imageStore: ArrayList<PhotoData> = ArrayList()

    //we will observe this value
    val imageList = MutableLiveData<ArrayList<PhotoData>>()

    fun fetchImage(query: String = previousQuery, isConnected: Boolean) = viewModelScope.launch {
        shouldLoad = false
        if (!query.equals(previousQuery)) {
            previousQuery = query
            imageStore.clear()
            pageCount = 1
        }

        val apiResult = repository.getImageList(query, pageCount, isConnected)
        when (apiResult.status) {
            Status.SUCCESS -> {
                shouldLoad = true
                errorMsg.postValue(null)
                pageCount++

                imageStore.addAll(apiResult.data!!)
                imageList.postValue(imageStore)
            }

            Status.DONE -> shouldLoad = null

            Status.ERROR -> {
                shouldLoad = true
                //If error occurs in subsequent calls, atleast show the previously loaded data
                if (imageStore.isEmpty()) {
                    pageCount = 1
                    errorMsg.postValue(apiResult.msg)
                }
            }
        }
    }
}