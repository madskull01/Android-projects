package com.example.viewbinding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.viewbinding.databinding.FragmentTestBinding

class TestFragment:Fragment(R.layout.fragment_test) {
    private  lateinit var binding: FragmentTestBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentTestBinding.bind(view)
        binding.tvHelloWorld

    }
}