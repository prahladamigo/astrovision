package com.astroenvision.astropower.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astroenvision.astropower.databinding.FragmentAddMemberBinding
import com.astroenvision.astropower.databinding.FragmentFamilyBinding

class FamilyFragment : Fragment() {
    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)

        return binding.root
    }

}