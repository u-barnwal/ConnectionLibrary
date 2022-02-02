package com.isolpro.library.connection.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.io.*

object Utils {
  const val FILE_EXTENSION = ".connection";

  fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val n = cm.activeNetwork
      if (n != null) {
        val nc = cm.getNetworkCapabilities(n)
        //It will check for both wifi and cellular network
        return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
          NetworkCapabilities.TRANSPORT_WIFI
        )
      }
      return false
    } else {
      val netInfo = cm.activeNetworkInfo
      return netInfo != null && netInfo.isConnectedOrConnecting
    }
  }

  @Throws(IOException::class)
  fun writeToFile(context: Context, fileName: String, data: String?) {
    val folderPath = context.externalCacheDir.toString() + File.separator + ".offline"
    val path = File(folderPath)
    if (!path.exists()) {
      path.mkdirs()
    }
    val file = File(path, "$fileName$FILE_EXTENSION")
    file.createNewFile()
    val fOut = FileOutputStream(file)
    val myOutWriter = OutputStreamWriter(fOut)
    myOutWriter.append(data)
    myOutWriter.close()
    fOut.flush()
    fOut.close()
  }

  @Throws(IOException::class)
  fun readFromFile(context: Context, fileName: String): String? {
    val filePath =
      context.externalCacheDir.toString() + File.separator + ".offline" + File.separator + "$fileName$FILE_EXTENSION"
    val result = StringBuilder()
    val br = BufferedReader(FileReader(filePath))
    var strLine: String?
    while (br.readLine().also { strLine = it } != null) {
      result.append(strLine)
    }
    return result.toString()
  }
}