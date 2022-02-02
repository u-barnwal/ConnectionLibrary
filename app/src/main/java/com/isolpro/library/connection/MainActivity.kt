package com.isolpro.library.connection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.isolpro.library.connection.helpers.ConnectionHelper

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    ConnectionHelper<Post>(this, Post::class.java)
      .loader(false)
      .success {
        Log.e("callback", "Success")
        Log.e("Product Id: ", it.id.toString());
      }
      .failure {
        Log.e("callback", "Failure")
      }
      .post("posts/")
  }

  class Post {
    val userId: Number = 0;
    val id: Number = 0;
    val title: String = "";
    val body: String = "";
  }
}