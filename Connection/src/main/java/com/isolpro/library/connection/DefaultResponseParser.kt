package com.isolpro.library.connection

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.isolpro.library.connection.interfaces.ResponseParser

class DefaultResponseParser<T> : ResponseParser<T> {
  override fun parse(data: String): T {
    return Gson().fromJson<T>(data, object : TypeToken<Connection<T>>() {}.type)
  }
}