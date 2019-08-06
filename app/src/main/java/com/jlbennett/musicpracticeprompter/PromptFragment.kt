package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jlbennett.musicpracticeprompter.databinding.FragmentPromptBinding

class PromptFragment : Fragment() {

    private lateinit var keyArray: Array<String>
    private lateinit var currentKey: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPromptBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_prompt, container, false
        )

        val args = PromptFragmentArgs.fromBundle(arguments!!)
        keyArray = resources.getStringArray(args.preset)

        binding.promptText.setOnClickListener { setPrompt(binding) }
        /*
        binding.promptText.setOnClickListener {
            binding.noteReminderLayout.visibility = View.VISIBLE
            binding.noteReminderText.text = ""
            if(!::currentKey.isInitialized)
                currentKey = keyArray.random()

            var newKey: String = currentKey
            while (newKey == currentKey) {
                newKey = keyArray.random()//Select distinct new key (can't be given the same prompt sequentially)
            }
            currentKey = newKey
            binding.promptText.text = currentKey.replace(' ', '\n')
        }*/

        binding.noteReminderButton.setOnClickListener {
            binding.noteReminderText.text = getNotesFromKey(currentKey)
        }

        return binding.root
    }

    private fun setPrompt(binding: FragmentPromptBinding) {
        binding.noteReminderLayout.visibility = View.VISIBLE
        binding.noteReminderText.text = ""
        if (!::currentKey.isInitialized)
            currentKey = keyArray.random()

        var newKey: String = currentKey
        while (newKey == currentKey) {
            newKey = keyArray.random()//Select distinct new key (can't be given the same prompt sequentially)
        }
        currentKey = newKey
        binding.promptText.text = currentKey.replace(' ', '\n')
    }

    private fun getNotesFromKey(key: String): String {
        var notes = "notes"
        val noteLookupArray = resources.getStringArray(R.array.noteLookupArray)
        for (pair in noteLookupArray) {
            val splitResult = pair.split("\\|".toRegex(), 2)
            if (splitResult[0] == key) {
                notes = splitResult[1]
                break
            }
        }
        return notes
    }
}
