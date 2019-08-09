package com.jlbennett.musicpracticeprompter


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.jlbennett.musicpracticeprompter.databinding.FragmentModeSelectionBinding


class ModeSelectionFragment : Fragment() {

    enum class Mode{ TAP, TIMED }

    private lateinit var keyArray: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentModeSelectionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mode_selection, container, false)

        val args = ModeSelectionFragmentArgs.fromBundle(arguments!!)
        keyArray = args.keyArray

        binding.tapModeButton.setOnClickListener {view: View ->
            Navigation.findNavController(view).navigate(ModeSelectionFragmentDirections.actionModeSelectionFragmentToPromptFragment(keyArray, Mode.TAP, 0))
        }
        binding.timedPromptButton.setOnClickListener {view: View ->
            val delayPeriod: Int = Integer.parseInt(binding.timeEntry.text.toString())
            Log.i("delay", "Delay: $delayPeriod")
            //Delay period not functional yet.
            Navigation.findNavController(view).navigate(ModeSelectionFragmentDirections.actionModeSelectionFragmentToPromptFragment(keyArray, Mode.TIMED, 2))
        }

        return binding.root
    }


}
