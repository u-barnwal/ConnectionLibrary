package com.isolpro.library.connection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.isolpro.library.connection.helpers.ConnectionHelper

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val connection = ConnectionHelper(this)
      .then {
        Log.e("callback", "Success")
      }
      .catch {
        Log.e("callback", "Failure")
      }
      .execute()
  }
}