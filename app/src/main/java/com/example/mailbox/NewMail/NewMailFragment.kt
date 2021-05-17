package com.example.mailbox.NewMail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mailbox.API.API
import com.example.mailbox.Inbox.InboxViewModel
import com.example.mailbox.R
import com.example.mailbox.databinding.FragmentNewMailBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class NewMailFragment : Fragment() {

    private lateinit var binding: FragmentNewMailBinding
    private val inboxViewModel: InboxViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_mail, container, false)

        setupButtons()

        return binding.root
    }


    private fun setupButtons() {
        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().navigate(R.id.action_NewMailFragment_to_InboxFragment)
            }
            buttonSend.setOnClickListener {
                checkIfInputIsCorrect()
            }
        }
    }

    private fun checkIfInputIsCorrect() {
        if (binding.email.text.isNullOrEmpty() || !binding.email.text.contains("@")) {
            Snackbar.make(
                requireView(),
                getString(R.string.Du_måste_fylla_i_en_giltlig_mottagare),
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()

        } else {
            API().postMail(
                binding.email.text.toString(),
                binding.title.text.toString(),
                binding.message.text.toString(),
            ) { inboxViewModel.getMails() }
            Snackbar.make(
                requireView(),
                getString(R.string.Ditt_mail_är_skickat),
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }

    }

}

