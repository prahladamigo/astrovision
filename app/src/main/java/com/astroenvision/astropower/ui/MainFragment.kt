package com.astroenvision.astropower.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astroenvision.astropower.databinding.FragmentFamilyBinding
import com.astroenvision.astropower.databinding.FragmentMainBinding
import com.astroenvision.astropower.databinding.FragmentProfileBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

}