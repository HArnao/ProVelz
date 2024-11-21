package com.upao.velz.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

fun initializeSecondFirebaseApp(context: Context) {
    val options = FirebaseOptions.Builder()
        .setProjectId("fidelio-core-platform-test")
        .setApplicationId("1:101925174228:android:774bcf87b5343a78207441")
        .setApiKey("AIzaSyC7ZeAN3wlNuj3kiCqP21sa08DLVlkEwT4")
        .setStorageBucket("fidelio-core-platform-test.appspot.com")
        .build()

    FirebaseApp.initializeApp(context, options, "StorageApp")
}
