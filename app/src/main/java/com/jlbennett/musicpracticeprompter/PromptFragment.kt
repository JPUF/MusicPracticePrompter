package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jlbennett.musicpracticeprompter.databinding.FragmentPromptBinding

class PromptFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPromptBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prompt, container, false)
        return binding.root
    }
}
