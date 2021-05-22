package com.cainiaowo.livedatademo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import com.cainiaowo.livedatademo.databinding.FragmentLivedataBinding

class LiveDataFragment : Fragment() {

    companion object {
        const val TAG = "LiveDataFragment"
    }

    private lateinit var binding: FragmentLivedataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLivedataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as LiveDataActivity).apply {
            liveData.observe(viewLifecycleOwner) {
                binding.tvLiveData.text = "当前LiveData的值：$it"
                Log.e(TAG, "LiveData在Fragment中的值：$it")
            }

            val mappedLiveData = liveData.map {
                it.takeLast(7)
            }
            mappedLiveData.observe(viewLifecycleOwner) {
                binding.tvMappedLiveData.text = "MappedLiveData:$it"
                Log.e(TAG, "MappedLiveData在Fragment中的值: $it")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }
}