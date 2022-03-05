package com.isolpro.library.connection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.isolpro.library.connection.services.PostService

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    PostService.getPosts(this)
      .success {
        // TODO("Use your data from $it")

        // use $it.userId to get userId from Post
      }
      .failure {
        // TODO("Let user know that the request has failed")
      }
      .post()
  }
}