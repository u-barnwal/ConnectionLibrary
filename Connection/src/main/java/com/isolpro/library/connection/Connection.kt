package com.isolpro.library.connection

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Connection {
  var OFFLINE_MODE = false

  private val mExecutor: Executor = Executors.newSingleThreadExecutor()
  private val handler = Handler(Looper.getMainLooper())
}