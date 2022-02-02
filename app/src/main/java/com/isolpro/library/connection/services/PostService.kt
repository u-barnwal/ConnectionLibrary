package com.isolpro.library.connection.services

import android.content.Context
import android.util.Log
import com.isolpro.library.connection.Connection
import com.isolpro.library.connection.helpers.ConnectionHelper
import com.isolpro.library.connection.models.Post

object PostService {

  fun getPosts(ctx: Context): Connection<Post> {
    return ConnectionHelper(ctx, Post::class.java)
      .loader(false)
      .success {
        Log.e("callback", "Success")
        Log.e("Product Id: ", it.id.toString());
      }
      .failure {
        Log.e("callback", "Failure")
      }
  }

}