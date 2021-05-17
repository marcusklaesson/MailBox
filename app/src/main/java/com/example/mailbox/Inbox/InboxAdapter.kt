package com.example.mailbox.Inbox

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mailbox.R
import kotlinx.android.synthetic.main.item_mail.view.*

interface OnMailClickListener {
    fun onMailClicked(mail: MailModel): AlertDialog.Builder
}

class InboxAdapter(
    private val mails: ArrayList<MailModel>,
    private val clickListener: OnMailClickListener

) : RecyclerView.Adapter<InboxAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mail, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mails.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val mail = mails[position]

        holder.itemView.title.text = mail.sender
        holder.itemView.subtitle.text = mail.title

        holder.itemView.container.setOnClickListener {
            clickListener.onMailClicked(mail)
        }

    }

}