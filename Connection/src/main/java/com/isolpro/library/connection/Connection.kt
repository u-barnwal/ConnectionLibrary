package com.isolpro.library.connection

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.isolpro.custom.Callback
import org.json.JSONException
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executor
import java.util.concurrent.Executors


abstract class Connection<T>() {
  var OFFLINE_MODE = false

  val REQUST_MODE_POST = "POST";
  val REQUST_MODE_GET = "GET";

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

  fun post(endpoint: String): Connection<T> {
    this.requestMode = REQUST_MODE_POST;
    this.endpoint = endpoint;

    return this;
  }

  fun get(endpoint: String): Connection<T> {
    this.requestMode = REQUST_MODE_GET;
    this.endpoint = endpoint;

    return this;
  }

  fun execute() {
    if (loader) showLoader()
    if (!OFFLINE_MODE) mExecutor.execute { this.doInBackground() } else mExecutor.execute { this.doInBackgroundOffline() }
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

  protected open fun handleOnSuccess(res: T?) {
    success?.exec(res)
  }

  protected open fun handleOnFailure(res: T?) {
    failure?.exec(res)
  }

  private fun hasOfflineEndpoint(): Boolean {
    return offlineEndpoint != null
  }

  private fun doInBackground() {
    // TODO: check for network connectivity
//    if (!Utils.dataNetworkAvailable(context)) {
//      handler.post { onPostExecute(null) }
//      return
//    }

    try {
      val apiEndpoint = config.baseEndpoint + endpoint;
      val apiURL = URL(apiEndpoint)

      handleOnRequestCreated(apiEndpoint, payload);

//      Utils.showLog(apiURL, request!!.toString(2))

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
      e.message?.let { Log.e("5", it) };
//      handler.post { showUnexpectedError("Couldn't reach the server!") }
    } catch (e: JSONException) {
      e.message?.let { Log.e("4", it) };
//      handler.post { showUnexpectedError("Couldn't reach the server!") }
    }
  }

  private fun doInBackgroundOffline() {
    if (!hasOfflineEndpoint()) {
//      handler.post { this.handleOperationUnavailable() }
      return
    }

    try {
      // Read data from file here
//      val response: String = Utils.readFromFile(context, offlineEndpoint)
      val response: String = ""

      handler.post { onPostExecute(response) }
    } catch (e: IOException) {
      e.message?.let { Log.e("3", it) };
      handler.post {
//        Dialog.show(
//          context as Activity?,
//          "Offline Data Unavailable",
//          "Please connect to internet at least once to download data!",
//          Dialog.Button.generateOkay(),
//          null,
//          UIDialog.getAnimation(UIDialog.Type.INFO)
//        )

        hideLoader()
      }
    }
  }

  private fun onPostExecute(responseString: String?) {
//    if (responseString == null) {
//      handleDataNetworkUnavailable()
//      return
//    }

//    handleOnResponseReceived(responseString)

    try {
//      val response = JSONObject(responseString)

      handleOnResponseReceived(responseString)

      if (hasOfflineEndpoint()) {
        try {
//          Utils.writeToFile(context, offlineEndpoint, responseString)
        } catch (e: IOException) {
          e.message?.let { Log.e("2", it) };
//          Utils.showLog(e.message)
        }
      }

//      handleResponseUI(response)

//      when (response.getJSONObject("prop").getString("status")) {
//        "success" -> onSuccess?.exec(if (response.has("data")) response["data"] else null)
//        "failure" -> onFailure?.exec(if (response.has("data")) response["data"] else null)
//      }
    } catch (e: JSONException) {
      e.message?.let { Log.e("1", it) };
//      Utils.showLog(e.message)
//      Utils.showLog("Response", responseString)
    } finally {
      hideLoader()
    }
  }

  // Callbacks

//  abstract fun getConfig(): Config

  abstract fun showLoader()

  abstract fun hideLoader()

  abstract fun handleOnRequestCreated(endpoint: String, data: T?)

  abstract fun handleOnResponseReceived(data: String?)

  abstract fun handleOnConnectionError()

  abstract fun handleOnOfflineDataUnsupported()

  abstract fun handleOnOfflineDataUnavailable()

  class Config(val baseEndpoint: String, val supportOffline: Boolean) {
  }
}