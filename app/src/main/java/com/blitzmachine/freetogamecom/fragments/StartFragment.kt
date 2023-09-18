package com.blitzmachine.freetogamecom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private val binding: FragmentStartBinding by lazy { FragmentStartBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root
}