package com.example.vkapifriends.helpers

import android.app.Application
import com.vk.sdk.VKSdk
import android.support.v4.view.KeyEventCompat.startTracking
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker



class VkOnCreate: Application() {
    private var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
        //VKSdk.initialize(applicationContext)
    }
}