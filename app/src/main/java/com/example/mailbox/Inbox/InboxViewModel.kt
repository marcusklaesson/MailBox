package com.example.mailbox.Inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mailbox.API.API
import org.json.JSONObject

data class MailModel(
    var sender: String? = "",
    var title: String? = "",
    var message: String? = ""

) {
    fun parseJson(jsonObject: JSONObject): MailModel {
        sender = jsonObject.getString("sender") as String
        title = jsonObject.getString("title") as String
        message = jsonObject.getString("message") as String

        return this
    }
}

class InboxViewModel : ViewModel() {
    private val _mails = MutableLiveData<ArrayList<MailModel>>().apply { value = arrayListOf() }
    val mails: LiveData<ArrayList<MailModel>> = _mails

    init {
        getMails()
    }

    fun getMails() {
        API().getMails {
            _mails.postValue(it)
        }
    }

}