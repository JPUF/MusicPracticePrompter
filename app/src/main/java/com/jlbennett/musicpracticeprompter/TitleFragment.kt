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

        //TODO wy doesn't this function navigate to the prompt fragment...
        binding.titleButton.setOnClickListener { view: View ->
            Log.v("button", "click listener")
            Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_promptFragment)
        }

        return binding.root
    }

}
