package com.isolpro.library.connection.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.isolpro.library.connection.Connection

const val BASE_ENDPOINT = "https://jsonplaceholder.typicode.com/";

class ConnectionHelper<T>(private val ctx: Context, private val typeClass: Class<T>) :
  Connection<T>() {
  override var config: Config = Config(BASE_ENDPOINT)

  override fun getContext(): Context {
    return ctx;
  }

  override fun showLoader() {
    Toast.makeText(getContext(), "Showing Loader", Toast.LENGTH_LONG).show();
  }

  override fun hideLoader() {
    Toast.makeText(getContext(), "Hiding Loader", Toast.LENGTH_SHORT).show();
  }

  override fun handleOnRequestCreated(endpoint: String, data: Any?) {
    Log.e("Request:", "");
    Log.e(endpoint, data.toString());
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
    Log.e("Error:", "");
    e.message?.let { Log.e("", it) };
  }

  override fun handleOnNoResponseError() {
    Log.e("---------", "handleOnNoResponseError");
  }

  override fun getClassType(): Class<T> {
    return typeClass;
  }
}