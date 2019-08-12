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
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class PromptFragment : Fragment() {

    private lateinit var mode: ModeSelectionFragment.Mode
    private var delayPeriod: Int = 0
    private lateinit var keyArray: Array<String>
    private lateinit var currentKey: String

    private val delayedExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPromptBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_prompt, container, false)

        val args = PromptFragmentArgs.fromBundle(arguments!!)
        keyArray = args.keyArray
        mode = args.mode
        delayPeriod = args.delayPeriod

        setPrompt(binding)//set initial prompt.

        when (mode) {
            ModeSelectionFragment.Mode.TAP -> binding.promptText.setOnClickListener { setPrompt(binding) }
            ModeSelectionFragment.Mode.TIMED -> setPromptWithTimer(binding, delayPeriod)
        }

        binding.noteReminderButton.setOnClickListener {
            binding.noteReminderText.text = getNotesFromKey(currentKey)
        }

        return binding.root
    }


    private fun setPromptWithTimer(binding: FragmentPromptBinding, period: Int) {
        val timedPrompt = Runnable {
            run {
                setPrompt(binding)
            }
        }
        val promptHandle =
            delayedExecutor.scheduleAtFixedRate(timedPrompt, 0, period.toLong(), TimeUnit.SECONDS)//DELAY FOR 1 SECOND
        delayedExecutor.run {
            schedule({
                run {
                    promptHandle.cancel(true)
                }
            }, 1, TimeUnit.HOURS)
        }//REPEAT FOR 1 HOUR


    }

    private fun setPrompt(binding: FragmentPromptBinding) {
        binding.noteReminderLayout.visibility = View.VISIBLE
        binding.noteReminderText.text = ""
        if (!::currentKey.isInitialized)
            currentKey = keyArray.random()

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
}
