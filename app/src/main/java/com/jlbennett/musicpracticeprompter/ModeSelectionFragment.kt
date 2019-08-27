package com.jlbennett.musicpracticeprompter


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.jlbennett.musicpracticeprompter.databinding.FragmentModeSelectionBinding


class ModeSelectionFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    enum class Mode { TAP, TIMED }

    private lateinit var keyArray: Array<String>
    //private lateinit var binding: FragmentModeSelectionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentModeSelectionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mode_selection, container, false)

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val seekBar = view.findViewById<SeekBar>(R.id.delaySeekBar)
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.i("delay", "SeekBar progress $progress")
        val textView = view!!.findViewById<TextView>(R.id.timeEntry)
        textView.text = (progress * 2 + 2).toString()
        //Previous cause of memory leak. Solved by replacing a binding reference with a findByViewId() call
        //and reducing the scope of binding.

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.i("touch", "Started tracking touch")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        Log.i("touch", "Stopped tracking touch")
    }
}
