package com.jlbennett.musicpracticeprompter


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.jlbennett.musicpracticeprompter.databinding.FragmentModeSelectionBinding


class ModeSelectionFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    enum class Mode { TAP, TIMED }

    private lateinit var keyArray: Array<String>
    private lateinit var binding: FragmentModeSelectionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mode_selection, container, false)

        val args = ModeSelectionFragmentArgs.fromBundle(arguments!!)
        keyArray = args.keyArray

        binding.tapModeButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(
                ModeSelectionFragmentDirections.actionModeSelectionFragmentToPromptFragment(
                    keyArray,
                    Mode.TAP,
                    0
                )
            )
        }
        binding.timedPromptButton.setOnClickListener {
            binding.delaySelectionLayout.visibility = View.VISIBLE
            binding.readyButton.visibility = View.VISIBLE
        }

        binding.readyButton.setOnClickListener { view: View ->
            val delayPeriod: Int = Integer.parseInt(binding.timeEntry.text.toString())
            if (delayPeriod in 1..600) {
                Navigation.findNavController(view).navigate(
                    ModeSelectionFragmentDirections.actionModeSelectionFragmentToPromptFragment(keyArray,Mode.TIMED,delayPeriod)
                )
            } else {
                Toast.makeText(activity, "Enter a time between 1 and 600 seconds.", Toast.LENGTH_LONG).show()
            }
        }
        binding.delaySeekBar.setOnSeekBarChangeListener(this)
        return binding.root
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.i("delay", "SeekBar progress $progress")
        binding.timeEntry.setText((progress * 2 + 2).toString())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.i("touch", "Started tracking touch")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.i("touch", "Stopped tracking touch")
    }
}
