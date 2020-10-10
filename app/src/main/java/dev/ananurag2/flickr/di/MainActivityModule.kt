package dev.ananurag2.flickr.di

import dev.ananurag2.flickr.data.db.PhotoDao
import dev.ananurag2.flickr.data.network.ApiService
import dev.ananurag2.flickr.repository.ImageRepository
import dev.ananurag2.flickr.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * created by ankur on 4/2/20
 */
val mainActivityModule = module {
    single { getImageRepository(get(), get()) }

    viewModel { MainViewModel(get()) }
}

fun getImageRepository(photoDao: PhotoDao, apiService: ApiService): ImageRepository = ImageRepository.getInstance(photoDao, apiService)