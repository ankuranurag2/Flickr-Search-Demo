package dev.ananurag2.flickr.data.network

import dev.ananurag2.flickr.model.PhotosResponse
import dev.ananurag2.flickr.utils.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * created by ankur on 31/1/20
 */
public interface ApiService {
    @GET("services/rest/")
    suspend fun getImageList(
        @Query("text") query: String,
        @Query("page") pageNum: Int,
        @Query("api_key") apiKey: String = "YOUR_ENCRYPTED_API_KEY".decrypt(AppUtils.decodeBase64("YW5rdXJhbnVyYWcy")),
        @Query("method") method: String = FLICKR_METHOD,
        @Query("format") format: String = FLICKR_API_RESPONSE_FORMAT,
        @Query("per_page") perPage: Int = PER_PAGE_RESULT_COUNT,
        @Query("nojsoncallback") jsonCallback: Int = JSON_CALLBACK
    ): Response<PhotosResponse>
}