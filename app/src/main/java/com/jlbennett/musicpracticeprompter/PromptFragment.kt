package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jlbennett.musicpracticeprompter.databinding.FragmentPromptBinding

class PromptFragment : Fragment() {

    private lateinit var keyArray: Array<String>

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

        binding.promptText.setOnClickListener {
            val keyPrompt: String = keyArray.random()
            binding.promptText.text = keyPrompt.replace(' ','\n')
        }

        return binding.root
    }
}
