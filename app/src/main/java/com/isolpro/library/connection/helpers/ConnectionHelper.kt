package com.isolpro.library.connection.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.isolpro.library.connection.Connection

const val BASE_ENDPOINT = "https://jsonplaceholder.typicode.com/";

class ConnectionHelper<T>(val context: Context) : Connection<T>() {
  override var config: Config = Config(BASE_ENDPOINT)

  override fun showLoader() {
    Toast.makeText(context, "Showing Loader", Toast.LENGTH_LONG).show();
  }

  override fun hideLoader() {
    Toast.makeText(context, "Hiding Loader", Toast.LENGTH_SHORT).show();
  }

  override fun handleOnRequestCreated(endpoint: String, data: T?) {
    Log.e("---------", "handleOnRequestCreated");
    Log.e("---------", data.toString());
  }

  override fun handleOnResponseReceived(data: String?) {
    Log.e("---------", "handleOnResponseReceived");
    data?.let { Log.e("---------", it) };
  }

  override fun handleOnOfflineDataUnsupported() {
    Log.e("---------", "handleOnOfflineDataUnsupported");
  }

  override fun handleOnOfflineDataUnavailable() {
    Log.e("---------", "handleOnOfflineDataUnavailable");
  }

  override fun handleOnError(e: Exception) {
    Log.e("---------", "handleOnError");
  }

  override fun handleOnNoResponseError() {
    Log.e("---------", "handleOnNoResponseError");
  }

  override fun isOfflineMode(): Boolean {
    return !Utils.isOnline(context)
  }
}