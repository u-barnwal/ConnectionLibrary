package com.isolpro.library.connection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.isolpro.library.connection.helpers.ConnectionHelper

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    ConnectionHelper<Post>(this)
      .post("posts/")
      .success {
        Log.e("callback", "Success")
      }
      .failure {
        Log.e("callback", "Failure")
      }
      .execute()
  }

  class Post {
    val userId: Number = 0;
    val id: Number = 0;
    val title: String = "";
    val body: String = "";
  }
}