package com.isolpro.library.connection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.isolpro.library.connection.services.PostService

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    PostService.getPosts(this)
      .success {
        Log.e("callback", "Success")
        Log.e("Product Id: ", it.id.toString());
      }
      .failure {
        Log.e("callback", "Failure")
      }
      .get()
  }
}