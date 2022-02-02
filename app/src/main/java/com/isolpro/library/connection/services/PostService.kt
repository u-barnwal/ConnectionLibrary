package com.isolpro.library.connection.services

import android.content.Context
import com.isolpro.library.connection.Connection
import com.isolpro.library.connection.helpers.ConnectionHelper
import com.isolpro.library.connection.models.Post

object PostService {

  fun getPosts(ctx: Context): Connection<Post> {
    return ConnectionHelper(ctx, Post::class.java)
      .endpoint("posts/")
//      .payload(Post())
      .loader(false)
  }

}