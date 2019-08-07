package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.chip.Chip
import com.jlbennett.musicpracticeprompter.databinding.FragmentCustomKeyBinding

class CustomKeyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCustomKeyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_key, container, false)
        binding.readyButton.setOnClickListener { view: View ->
            //TODO handle "none selected"
            Navigation.findNavController(view).navigate(CustomKeyFragmentDirections.actionCustomKeyFragmentToPromptFragment(getCurrentKeyArray(binding)))
        }

        return binding.root
    }

    private fun getCurrentKeyArray(binding: FragmentCustomKeyBinding): Array<String> {
        val keyList: MutableList<String> = mutableListOf()
        binding.majorLayout.children.forEach {
            val chip: Chip? = it as Chip
            if (chip!!.isChecked) {
                val key = chip.text.toString()
                keyList.add(key)
            }
        }
        binding.minorLayout.children.forEach {
            val chip: Chip? = it as Chip
            if (chip!!.isChecked) {
                val key = chip.text.toString()
                keyList.add(key)
            }
        }
        return keyList.toTypedArray()
    }
}
