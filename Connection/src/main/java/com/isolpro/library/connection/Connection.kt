package com.isolpro.library.connection

import android.os.Handler
import android.os.Looper
import com.isolpro.custom.Callback
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class Connection() {
  var OFFLINE_MODE = false

  private val mExecutor: Executor = Executors.newSingleThreadExecutor()
  private val handler = Handler(Looper.getMainLooper())

  private val endpoint: String? = null
  private val request: JSONObject? = null
  private var then: Callback<Any>? = null
  private var catch: Callback<Any>? = null
  private val showLoader = true
  private var offlineEndpoint: String? = null

  fun execute() {
    if (showLoader) showLoader()
    handleOnSuccess("")
//    if (!OFFLINE_MODE) mExecutor.execute { this.doInBackground() } else mExecutor.execute { this.doInBackgroundOffline() }
  }

  fun setOfflineEndpoint(offlineEndpoint: String, uniqueRowId: String? = ""): Connection {
    val suffix = uniqueRowId ?: ""
    this.offlineEndpoint = offlineEndpoint + suffix
    return this
  }

  fun then(then: Callback<Any>): Connection {
    this.then = then;
    return this;
  }

  fun catch(catch: Callback<Any>): Connection {
    this.catch = catch;
    return this;
  }

  protected open fun handleOnSuccess(res: Any) {
    then?.exec(res)
  }

  protected open fun handleOnFailure(res: Any) {
    then?.exec(res)
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
      val apiURL = URL(getConfig().baseEndpoint + endpoint)

//      Utils.showLog(apiURL, request!!.toString(2))

      val httpURLConnection = apiURL.openConnection() as HttpURLConnection
      httpURLConnection.requestMethod = "POST"
      httpURLConnection.doOutput = true
      httpURLConnection.doInput = true

      val outputStream = httpURLConnection.outputStream

      val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
      bufferedWriter.write(request.toString())
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
//      handler.post { showUnexpectedError("Couldn't reach the server!") }
    } catch (e: JSONException) {
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

    try {
      val response = JSONObject(responseString)
//      Utils.showLog("Response", response.toString(2))

//      if (!validateResponse(response)) {
//        showUnexpectedError("Invalid response received!")
//        return
//      }

      if (hasOfflineEndpoint()) {
        try {
//          Utils.writeToFile(context, offlineEndpoint, responseString)
        } catch (e: IOException) {
//          Utils.showLog(e.message)
        }
      }

//      handleResponseUI(response)

//      when (response.getJSONObject("prop").getString("status")) {
//        "success" -> onSuccess?.exec(if (response.has("data")) response["data"] else null)
//        "failure" -> onFailure?.exec(if (response.has("data")) response["data"] else null)
//      }
    } catch (e: JSONException) {
//      Utils.showLog(e.message)
//      Utils.showLog("Response", responseString)
    } finally {
      hideLoader()
    }
  }

  abstract fun getConfig(): Config

  abstract fun showLoader()

  abstract fun hideLoader()

  class Config(val baseEndpoint: String, val supportOffline: Boolean) {
  }
}