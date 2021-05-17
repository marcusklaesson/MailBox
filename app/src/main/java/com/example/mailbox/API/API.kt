package com.example.mailbox.API

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.example.mailbox.Inbox.MailModel
import junit.framework.Assert.assertEquals
import org.testng.annotations.Test
import java.nio.charset.Charset
import javax.security.auth.callback.Callback

class API {
    fun getMails(callback: (ArrayList<MailModel>) -> Unit): JsonArrayRequest {
        val url = "https://609d237404bffa001792e0ca.mockapi.io/mails"
        val GET = Request.Method.GET
        val jsonArrayRequest = JsonArrayRequest(
            GET, url, null,
            { response ->
                // Log.d("tag", "Response: %s".format(response.toString()))
                val mailList = arrayListOf<MailModel>()
                for (mail in 0 until response.length()) {
                    val mails = response.getJSONObject(mail)
                    val mailModel = MailModel().parseJson(mails)
                    mailList.add(mailModel)

                }
                callback(mailList)
            },
            { error ->
                Log.d("tag", error.toString())
            }
        )
        ControllerAPI.instance?.addToRequestQueue(jsonArrayRequest)
        return jsonArrayRequest
    }

    @Test
    fun testgetMailsCorrectResponseUrl() {
        val response = getMails {}
        assertEquals("https://609d237404bffa001792e0ca.mockapi.io/mails", response.url)
    }

    fun postMail(
        email: String,
        title: String,
        message: String,
        callback: (Boolean) -> Unit
    ): StringRequest {
        val url = "https://609d237404bffa001792e0ca.mockapi.io/mails"
        val requestBody =
            "&sender=$email&title=$title&message=$message"
        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    val strResp = response.toString()
                    callback(true)
                    Log.d("tag", strResp)
                },
                Response.ErrorListener { error ->
                    callback(false)
                    Log.d("tag", "error => $error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        ControllerAPI.instance?.addToRequestQueue(stringReq)
        return stringReq
    }

    @Test
    fun testpostMailCorrectResponseUrl() {
        val response = postMail("", "", "") {}
        assertEquals("https://609d237404bffa001792e0ca.mockapi.io/mails", response.url)
    }

}