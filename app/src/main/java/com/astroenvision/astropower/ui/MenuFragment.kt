package com.astroenvision.astropower.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astroenvision.astropower.databinding.FragmentFamilyBinding
import com.astroenvision.astropower.databinding.FragmentMenuBinding
import com.astroenvision.astropower.databinding.FragmentProfileBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

}