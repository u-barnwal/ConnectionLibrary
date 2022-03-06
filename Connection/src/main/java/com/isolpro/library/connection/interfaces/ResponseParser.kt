package com.isolpro.library.connection.interfaces

interface ResponseParser<T> {
  fun parse(data: String): T
}