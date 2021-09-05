package ru.site.developerslife.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.site.developerslife.R
import ru.site.developerslife.databinding.FragmentDisplayDataBinding

class DisplayDataFragment : Fragment() {
    private lateinit var binding: FragmentDisplayDataBinding
    private lateinit var viewModel: DisplayDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayDataBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

}