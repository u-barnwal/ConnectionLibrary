package com.isolpro.library.connection.helpers

import android.content.Context
import android.widget.Toast
import com.isolpro.library.connection.Connection
import org.json.JSONObject

class ConnectionHelper(val context: Context) : Connection() {
  override fun getConfig(): Config {
    return Config("", true)
  }

  override fun showLoader() {
    Toast.makeText(context, "Showing Loader", Toast.LENGTH_LONG).show();
  }

  override fun hideLoader() {
    Toast.makeText(context, "Hiding Loader", Toast.LENGTH_SHORT).show();
  }

  override fun handleOnRequestCreated(endpoint: String, data: JSONObject) {
    TODO("Not yet implemented")
  }

  override fun handleOnResponseReceived(data: JSONObject) {
    TODO("Not yet implemented")
  }
}