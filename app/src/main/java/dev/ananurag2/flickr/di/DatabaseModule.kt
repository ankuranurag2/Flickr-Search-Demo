package dev.ananurag2.flickr.di

import android.content.Context
import dev.ananurag2.flickr.data.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * created by ankur on 3/2/20
 */
val databaseModule = module {
    single { getDatabase(androidApplication()) }
    factory { getPhotoDao(get()) }
}

fun getDatabase(context: Context) = AppDatabase.getInstance(context)

fun getPhotoDao(database: AppDatabase) = database.getPhotoDao()

