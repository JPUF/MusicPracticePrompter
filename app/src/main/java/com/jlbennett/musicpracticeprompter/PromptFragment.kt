package com.jlbennett.musicpracticeprompter

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jlbennett.musicpracticeprompter.databinding.FragmentPromptBinding
import java.util.*

class PromptFragment : Fragment() {

    private lateinit var binding: FragmentPromptBinding
    private lateinit var mode: ModeSelectionFragment.Mode
    private var delayPeriod: Int = 0
    private var promptChange = Calendar.getInstance().timeInMillis
    private val progressBarHandler = Handler()
    private lateinit var progressBarRunnable: Runnable
    private val promptHandler = Handler()
    private lateinit var promptRunnable: Runnable
    private lateinit var keyArray: Array<String>
    private lateinit var currentKey: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prompt, container, false)

        val args = PromptFragmentArgs.fromBundle(arguments!!)
        keyArray = args.keyArray
        mode = args.mode
        delayPeriod = args.delayPeriod

        setPrompt()//set initial prompt.

        when (mode) {
            ModeSelectionFragment.Mode.TAP -> binding.promptText.setOnClickListener { setPrompt() }

            ModeSelectionFragment.Mode.TIMED -> {
                binding.timerProgressBar.visibility = View.VISIBLE
                setPromptWithTimer(delayPeriod)
                updateProgressBar()
            }
        }
        binding.noteReminderButton.setOnClickListener {
            binding.noteReminderText.text = getNotesFromKey(currentKey)
        }

        return binding.root
    }

    private fun updateProgressBar() {
        progressBarRunnable = Runnable {
            run {
                val currentTime = Calendar.getInstance().timeInMillis
                val difference = currentTime - promptChange//number of milliseconds since the prompt changed.
                val percentOfPeriod: Float = (difference / (1000F * delayPeriod.toFloat())) * 100F
                binding.timerProgressBar.progress = percentOfPeriod.toInt()
                if (binding.timerProgressBar.progress >= binding.timerProgressBar.max) {
                    binding.timerProgressBar.progress = binding.timerProgressBar.max
                }
                progressBarHandler.postDelayed(progressBarRunnable, 50)
            }
        }
        progressBarHandler.post(progressBarRunnable)
    }

    private fun setPromptWithTimer(period: Int) {
        promptRunnable = Runnable {
            run {
                setPrompt()//updates a TextView with a new prompt.
                promptHandler.postDelayed(promptRunnable, period * 1000L)
                Log.i("PromptFragment", "new prompt")
            }
        }
        promptHandler.post(promptRunnable)
    }

    private fun setPrompt() {
        promptChange = Calendar.getInstance().timeInMillis
        binding.noteReminderLayout.visibility = View.VISIBLE
        binding.noteReminderText.text = ""
        if (!::currentKey.isInitialized) currentKey = keyArray.random()

        var newKey: String = currentKey
        while (newKey == currentKey) {
            newKey = keyArray.random()//Select distinct new key (can't be given the same prompt sequentially)
        }
        currentKey = newKey
        binding.promptText.text = currentKey.replace(' ', '\n')
    }

    private fun getNotesFromKey(key: String): String {
        var notes = "notes"
        val noteLookupArray = resources.getStringArray(R.array.noteLookupArray)
        for (pair in noteLookupArray) {
            val splitResult = pair.split("\\|".toRegex(), 2)
            if (splitResult[0] == key) {
                notes = splitResult[1]
                break
            }
        }
        return notes
    }

    override fun onStop() {
        //TODO timers should be stopped in onStop() not onDestroy()
        if (mode == ModeSelectionFragment.Mode.TIMED) {
            progressBarHandler.removeCallbacks(progressBarRunnable)
            progressBarHandler.removeCallbacksAndMessages(null)
            promptHandler.removeCallbacks(promptRunnable)
            promptHandler.removeCallbacksAndMessages(null)
        }
        super.onStop()
    }
}
