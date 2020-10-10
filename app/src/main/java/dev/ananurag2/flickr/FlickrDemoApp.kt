package dev.ananurag2.flickr

import android.app.Application
import dev.ananurag2.flickr.di.databaseModule
import dev.ananurag2.flickr.di.mainActivityModule
import dev.ananurag2.flickr.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * created by ankur on 4/2/20
 */
class FlickrDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FlickrDemoApp)
            modules(listOf(databaseModule, networkModule, mainActivityModule))
        }
    }
}