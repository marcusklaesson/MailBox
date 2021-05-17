package com.example.mailbox.Inbox

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mailbox.R
import com.example.mailbox.databinding.FragmentInboxBinding
import junit.framework.Assert.assertEquals
import org.testng.annotations.Test

class InboxFragment : Fragment(), OnMailClickListener {
    private lateinit var binding: FragmentInboxBinding
    private lateinit var inboxAdapter: InboxAdapter
    private val inboxViewModel: InboxViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inbox, container, false)

        setupAdapter()
        setupButtons()

        return binding.root
    }


    private fun setupAdapter() {
        inboxViewModel.mails.observe(viewLifecycleOwner, {
            inboxAdapter = InboxAdapter(it, this)
            binding.recyclerview.adapter = inboxAdapter
            inboxAdapter.notifyDataSetChanged()
        })
    }

    private fun setupButtons() {
        binding.mailButton.setOnClickListener {
            findNavController().navigate(R.id.action_InboxFragment_to_NewMailFragment)
        }
    }

    override fun onMailClicked(mail: MailModel): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(mail.sender)
        builder.setMessage(mail.message)
        builder.setPositiveButton(getString(R.string.stäng)) { dialog, which ->
            Toast.makeText(
                context,
                ":)", Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
        return builder
    }

    @Test
    fun test(context: Context) {
        val builder = onMailClicked(MailModel("", "", ""))
        assertEquals(AlertDialog.Builder(context), builder)
    }
}