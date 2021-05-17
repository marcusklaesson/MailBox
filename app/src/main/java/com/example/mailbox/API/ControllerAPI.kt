package com.example.mailbox.API

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ControllerAPI : Application() {
    private var mRequestQueue: RequestQueue? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue
        }
    fun addToRequestQueue(req: StringRequest) {
        req.tag = TAG
        requestQueue!!.add(req)
    }

    fun addToRequestQueue(req: JsonArrayRequest) {
        req.tag = TAG
        requestQueue!!.add(req)
    }

    companion object {
        val TAG: String = ControllerAPI::class.java.simpleName

        @get:Synchronized
        var instance: ControllerAPI? = null
            private set
    }
}