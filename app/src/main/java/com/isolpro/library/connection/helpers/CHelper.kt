package com.isolpro.library.connection.helpers

import android.content.Context
import com.isolpro.library.connection.Connection

class CHelper<T>(private val ctx: Context, private val classType: Class<T>) : Connection<T>() {
  override var config: Config = Config("API_BASE_ENDPOINT")

  override fun getContext(): Context {
    return ctx;
  }

  override fun showLoader() {
    TODO("Write your function for showing loader")
  }

  override fun hideLoader() {
    TODO("Write your function for hiding loader")
  }

  override fun handleOnRequestCreated(endpoint: String, data: Any?) {
    TODO("Access the request endpoint and data")
  }

  override fun handleOnResponseReceived(data: String?) {
    TODO("This is triggered everytime your receive a response, implement your logger")
  }

  override fun handleOnNoResponseError() {
    TODO("Handle when nothing is received as response")
  }

  override fun handleOnOfflineDataUnsupported() {
    TODO("Handle when the request made, doesn't store offline data")
  }

  override fun handleOnOfflineDataUnavailable() {
    TODO("Handle when the request made, store offline data but doesn't have anything cache yet")
  }

  override fun handleOnError(e: Exception) {
    TODO("Handle all other errors")
  }

  override fun getClassType(): Class<T> {
    return classType;
  }
}