package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.jlbennett.musicpracticeprompter.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.titleButton.setOnClickListener { view: View ->
            Log.i("selection", binding.presetSpinner.selectedItem.toString())
            var presetID: Int = R.array.allKeys //Defaults to all keys.

            when(binding.presetSpinner.selectedItem) {
                "All keys" -> presetID = R.array.allKeys
                "All major keys" -> presetID = R.array.allMajorKeys
                "All minor keys" -> presetID = R.array.allMinorKeys
            }
            Navigation.findNavController(view).navigate(TitleFragmentDirections.actionTitleFragmentToPromptFragment(presetID))
        }

        return binding.root
    }
}
