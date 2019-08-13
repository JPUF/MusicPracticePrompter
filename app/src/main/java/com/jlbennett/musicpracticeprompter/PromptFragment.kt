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
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class PromptFragment : Fragment() {

    private lateinit var binding: FragmentPromptBinding
    private lateinit var mode: ModeSelectionFragment.Mode
    private var delayPeriod: Int = 0
    private var promptChange = Calendar.getInstance().timeInMillis
    private lateinit var updateThread: Thread
    private lateinit var keyArray: Array<String>
    private lateinit var currentKey: String

    private val delayedExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)


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
        updateThread = Thread{
            while(!updateThread.isInterrupted) {
                val currentTime = Calendar.getInstance().timeInMillis
                val difference = currentTime - promptChange//number of milliseconds since the prompt changed.
                val percentOfPeriod: Float = ( difference / (1000F * delayPeriod.toFloat() )) * 100F
                Log.i("progress", "Period: ${1000F * delayPeriod.toFloat()}  Difference: $difference  Percent: $percentOfPeriod")
                binding.timerProgressBar.progress = percentOfPeriod.toInt()
                if(binding.timerProgressBar.progress >= binding.timerProgressBar.max) {
                    binding.timerProgressBar.progress = binding.timerProgressBar.max
                }
                try {Thread.sleep(25)}
                catch (e: InterruptedException) {
                    Log.i("progress", "Thread Interrupted")
                }
            }
        }
        updateThread.start()
    }

    override fun onDestroy() {
        if(mode == ModeSelectionFragment.Mode.TIMED)
            updateThread.interrupt()//TODO not working. Thread should not execute whilst not in this fragment.
        super.onDestroy()
    }


    private fun setPromptWithTimer(period: Int) {
        val timedPrompt = Runnable {
            run {
                Log.i("timedPrompt", "setting prompt")
                setPrompt()
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

    private fun setPrompt() {//TODO seems not to work on emulator. Maybe thread gets interrupted before promptText is set.
        Log.i("timedPrompt", "setPrompt called")
        promptChange = Calendar.getInstance().timeInMillis
        binding.noteReminderLayout.visibility = View.VISIBLE
        binding.noteReminderText.text = ""
        if (!::currentKey.isInitialized)
            currentKey = keyArray.random()

        var newKey: String = currentKey
        while (newKey == currentKey) {
            newKey = keyArray.random()//Select distinct new key (can't be given the same prompt sequentially)
            Log.i("timedPrompt","setPrompt:           newKey: $newKey")
        }
        currentKey = newKey
        Log.i("timedPrompt","setPrompt: setting prompt as $currentKey")
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
