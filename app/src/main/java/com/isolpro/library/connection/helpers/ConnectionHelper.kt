package com.isolpro.library.connection.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.isolpro.library.connection.Connection

const val BASE_ENDPOINT = "https://jsonplaceholder.typicode.com/";

class ConnectionHelper<T>(val context: Context) : Connection<T>() {
  override fun getConfig(): Config {
    return Config(BASE_ENDPOINT, true)
  }

  override fun showLoader() {
    Toast.makeText(context, "Showing Loader", Toast.LENGTH_LONG).show();
  }

  override fun hideLoader() {
    Toast.makeText(context, "Hiding Loader", Toast.LENGTH_SHORT).show();
  }

  override fun handleOnRequestCreated(endpoint: String, data: T?) {
    Log.e("---------", "handleOnRequestCreated");
  }

  override fun handleOnResponseReceived(data: T?) {
    Log.e("---------", "handleOnResponseReceived");
  }

  override fun handleOnConnectionError() {
    Log.e("---------", "handleOnConnectionError");
  }

  override fun handleOnOfflineDataUnsupported() {
    Log.e("---------", "handleOnOfflineDataUnsupported");
  }

  override fun handleOnOfflineDataUnavailable() {
    Log.e("---------", "handleOnOfflineDataUnavailable");
  }
}