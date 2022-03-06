package com.isolpro.library.connection.services

import android.content.Context
import com.isolpro.library.connection.Connection
import com.isolpro.library.connection.helpers.ConnectionHelper
import com.isolpro.library.connection.interfaces.ResponseParser
import com.isolpro.library.connection.models.Post

object PostService {

  fun getPosts(ctx: Context): Connection<List<Post>> {
    return ConnectionHelper<List<Post>>(ctx)
      .endpoint("/posts")
      .loader(false)
  }

  fun createPost(ctx: Context, post: Post): Connection<Post> {
    return ConnectionHelper<Post>(ctx)
      .parser(object : ResponseParser<Post> {
        override fun parse(data: String): Post {
          return Post()
        }
      })
      .payload(post)
      .endpoint("/posts")
      .loader(false)
  }

}