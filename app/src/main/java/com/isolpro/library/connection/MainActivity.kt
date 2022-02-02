package com.isolpro.library.connection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.isolpro.library.connection.helpers.ConnectionHelper
import com.isolpro.library.connection.models.Post
import com.isolpro.library.connection.services.PostService

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    PostService.getPosts(this).get()

//    ConnectionHelper(this, Post::class.java)
//      .loader(false)
//      .success {
//        Log.e("callback", "Success")
//        Log.e("Product Id: ", it.id.toString());
//      }
//      .failure {
//        Log.e("callback", "Failure")
//      }
//      .endpoint("posts/")
//      .post()
  }
}