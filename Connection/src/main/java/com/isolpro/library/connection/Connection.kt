package com.isolpro.library.connection

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.isolpro.custom.Callback
import com.isolpro.library.connection.helpers.Utils
import org.json.JSONException
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executor
import java.util.concurrent.Executors


abstract class Connection<T>() {
  private val REQUST_MODE_POST = "POST";
  private val REQUST_MODE_GET = "GET";

  private val mExecutor: Executor = Executors.newSingleThreadExecutor()
  private val handler = Handler(Looper.getMainLooper())

  protected abstract var config: Config;

  private var payload: T? = null
  private var success: Callback<T>? = null
  private var failure: Callback<T>? = null
  private var loader = true

  private var endpoint: String = ""
  private var requestMode: String = REQUST_MODE_POST

  private var offlineEndpoint: String? = null

  // Public Methods

  fun payload(payload: T?): Connection<T> {
    this.payload = payload;
    return this;
  }

  fun config(config: Config): Connection<T> {
    this.config = config;
    return this;
  }

  fun loader(loader: Boolean): Connection<T> {
    this.loader = loader;
    return this;
  }

  fun post(endpoint: String) {
    this.requestMode = REQUST_MODE_POST;
    this.endpoint = endpoint;

    execute()
  }

  fun get(endpoint: String) {
    this.requestMode = REQUST_MODE_GET;
    this.endpoint = endpoint;

    execute()
  }

  // Event Methods

  private fun onRequestCreated(endpoint: String, data: T?) {
    handleOnRequestCreated(endpoint, data);
  }

  protected open fun onSuccess(res: T?) {
    success?.exec(res)
    hideLoader()
  }

  protected open fun onFailure(res: T?) {
    failure?.exec(res)
    hideLoader()
  }

  private fun onResponseReceived(data: String?) {
    handler.post {
      handleOnResponseReceived(data);
    }
  }

  private fun onNoResponseError() {
    handler.post {
      handleOnNoResponseError();
    }
  }

  private fun onOfflineDataUnsupported() {
    handler.post {
      handleOnOfflineDataUnsupported()
      hideLoader()
    }
  }

  private fun onOfflineDataUnavailable() {
    handler.post {
      handleOnOfflineDataUnavailable()
      hideLoader()
    }
  }

  private fun onError(e: Exception) {
    handler.post {
      handleOnError(e);
      hideLoader()
    }
  }

  private fun execute() {
    if (loader) showLoader()

    if (!isOfflineMode()) mExecutor.execute { this.doInBackground() }
    else mExecutor.execute { this.doInBackgroundOffline() }
  }

  fun setOfflineEndpoint(offlineEndpoint: String, uniqueRowId: String? = ""): Connection<T> {
    val suffix = uniqueRowId ?: ""
    this.offlineEndpoint = offlineEndpoint + suffix
    return this
  }

  fun success(success: Callback<T>): Connection<T> {
    this.success = success;
    return this;
  }

  fun failure(failure: Callback<T>): Connection<T> {
    this.failure = failure;
    return this;
  }

  private fun hasOfflineEndpoint(): Boolean {
    return offlineEndpoint != null
  }

  private fun doInBackground() {
    try {
      val apiEndpoint = config.baseEndpoint + endpoint;
      val apiURL = URL(apiEndpoint)

      onRequestCreated(apiEndpoint, payload);

      val httpURLConnection = apiURL.openConnection() as HttpURLConnection
      httpURLConnection.requestMethod = requestMode
      httpURLConnection.doOutput = true
      httpURLConnection.doInput = true

      val outputStream = httpURLConnection.outputStream

      val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
      bufferedWriter.write(Gson().toJson(payload))
      bufferedWriter.flush()
      bufferedWriter.close()

      outputStream.close()

      val inputStream = httpURLConnection.inputStream

      val bufferedReader = BufferedReader(InputStreamReader(inputStream, "ISO_8859_1"))

      val response = StringBuilder()

      var line: String?

      while (bufferedReader.readLine().also { line = it } != null) {
        response.append(line)
      }

      bufferedReader.close()
      inputStream.close()
      httpURLConnection.disconnect()

      handler.post { onPostExecute(response.toString()) }
    } catch (e: IOException) {
      onError(e)
    } catch (e: JSONException) {
      onError(e)
    }
  }

  private fun doInBackgroundOffline() {
    if (!hasOfflineEndpoint()) {
      onOfflineDataUnsupported()
      return
    }

    try {
      // Read data from file here
//      val response: String = Utils.readFromFile(context, offlineEndpoint)
      val response: String = ""

      handler.post { onPostExecute(response) }
    } catch (e: IOException) {
      onError(e)
    }
  }

  private fun onPostExecute(responseString: String?) {
    if (responseString == null) {
      onNoResponseError()
      return
    }

    onResponseReceived(responseString)

    try {
//      val response = JSONObject(responseString)

      if (hasOfflineEndpoint()) {
        try {
//          Utils.writeToFile(context, offlineEndpoint, responseString)
        } catch (e: IOException) {
          onError(e)
        }
      }
    } catch (e: JSONException) {
      onError(e)
    } finally {
      hideLoader()
    }
  }

  private fun isOfflineMode(): Boolean {
    return !Utils.isOnline(getContext())
  }

  // Callbacks

  abstract fun getContext(): Context

  abstract fun showLoader()

  abstract fun hideLoader()

  abstract fun handleOnRequestCreated(endpoint: String, data: T?)

  abstract fun handleOnResponseReceived(data: String?)

  abstract fun handleOnNoResponseError()

  abstract fun handleOnOfflineDataUnsupported()

  abstract fun handleOnOfflineDataUnavailable()

  abstract fun handleOnError(e: Exception)

  class Config(val baseEndpoint: String) {
  }
}