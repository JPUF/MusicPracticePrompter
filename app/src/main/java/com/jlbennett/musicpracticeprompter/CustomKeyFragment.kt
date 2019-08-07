package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jlbennett.musicpracticeprompter.databinding.FragmentCustomKeyBinding

class CustomKeyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCustomKeyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_key, container, false)

        return binding.root
    }
}
