package com.isolpro.library.connection.helpers

import android.content.Context
import android.widget.Toast
import com.isolpro.library.connection.Connection

class ConnectionHelper<T>(val context: Context) : Connection<T>() {
  override fun getConfig(): Config {
    return Config("", true)
  }

  override fun showLoader() {
    Toast.makeText(context, "Showing Loader", Toast.LENGTH_LONG).show();
  }

  override fun hideLoader() {
    Toast.makeText(context, "Hiding Loader", Toast.LENGTH_SHORT).show();
  }

  override fun handleOnRequestCreated(endpoint: String, data: T) {
    TODO("Not yet implemented")
  }

  override fun handleOnResponseReceived(data: T) {
    TODO("Not yet implemented")
  }
}