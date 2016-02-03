package com.aldoborrero.muzei.ch.muzei

import android.net.Uri
import android.util.Log
import com.aldoborrero.muzei.ch.api.CalvinHobbesApiService
import com.aldoborrero.muzei.ch.api.model.Photo
import com.aldoborrero.muzei.ch.di.component.CalvinHobbesComponent
import com.aldoborrero.muzei.ch.di.component.DaggerCalvinHobbesComponent
import com.aldoborrero.muzei.ch.di.module.CalvinHobbesModule
import com.aldoborrero.muzei.ch.helper.Constants
import com.google.android.apps.muzei.api.Artwork
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.util.*
import javax.inject.Inject

class CalvinHobbesArtSource : RemoteMuzeiArtSource(CalvinHobbesArtSource.TAG) {

    @Inject lateinit var apiService: CalvinHobbesApiService

    @Throws(RemoteMuzeiArtSource.RetryException::class)
    override fun onTryUpdate(reason: Int) {
        initInjector()

        Log.d(TAG, "Scheduling a new background update")

        val photoResponse = apiService.randomPhoto()

        photoResponse.enqueue(object : Callback<Photo> {

            private var first: Boolean = true

            override fun onResponse(response: Response<Photo>, retrofit: Retrofit) {
                if (!response.isSuccess) {
                    Log.e(TAG, "Response has not been successful! Not performing a background update!")
                    return
                }

                val photo = response.body()
                Log.d(TAG, "New url received: " + photo.url)

                publishArtwork(Artwork.Builder().imageUri(Uri.parse(photo.url)).build())
            }

            override fun onFailure(t: Throwable) {
                Log.e(TAG, "Response has not been successful! Not performing a background update: " + t)
            }
        })

        scheduleUpdate(
                System.currentTimeMillis() + Constants.ROTATE_TIME_MILLIS + random.nextInt(Constants.MAX_JITTER_MILLIS))
    }

    //region Private Methods Region
    private fun initInjector() {
        component().inject(this)
    }

    private fun component(): CalvinHobbesComponent {
        return DaggerCalvinHobbesComponent.builder().calvinHobbesModule(CalvinHobbesModule(applicationContext)).build()
    }

    companion object {
        val TAG = CalvinHobbesApiService::class.java.simpleName

        private val random = Random()
    }
    //endregion
}
