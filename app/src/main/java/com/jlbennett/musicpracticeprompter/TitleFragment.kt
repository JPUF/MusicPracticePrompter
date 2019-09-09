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
import java.lang.System.gc


class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.allKeysImage.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(TitleFragmentDirections.actionTitleFragmentToModeSelectionFragment(resources.getStringArray(R.array.allKeys)))
        }
        binding.allMajorKeysImage.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(TitleFragmentDirections.actionTitleFragmentToModeSelectionFragment(resources.getStringArray(R.array.allMajorKeys)))
        }
        binding.allMinorKeysImage.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(TitleFragmentDirections.actionTitleFragmentToModeSelectionFragment(resources.getStringArray(R.array.allMinorKeys)))
        }
        binding.customImage.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(TitleFragmentDirections.actionTitleFragmentToCustomKeyFragment())
        }

        return binding.root
    }

    override fun onStop() {
        gc()
        super.onStop()
    }
}
