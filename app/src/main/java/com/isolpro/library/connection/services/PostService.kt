package com.isolpro.library.connection.services

import android.content.Context
import com.isolpro.library.connection.Connection
import com.isolpro.library.connection.helpers.ConnectionHelper
import com.isolpro.library.connection.models.Post

object PostService {

  fun getPosts(ctx: Context): Connection<List<Post>> {
    return ConnectionHelper<List<Post>>(ctx)
      .endpoint("/posts")
      .loader(false)
  }

  fun createPost(ctx: Context, post: Post): Connection<Post> {
    return ConnectionHelper<Post>(ctx)
      .payload(post)
      .endpoint("/posts")
      .loader(false)
  }

}