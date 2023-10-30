package com.blitzmachine.freetogamecom.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.imageLoader
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.views.LiveGamesAdapter
import com.blitzmachine.freetogamecom.databinding.FragmentStartBinding
import com.blitzmachine.freetogamecom.views.GameViewModel

class StartFragment : Fragment() {

    private val binding: FragmentStartBinding by lazy { FragmentStartBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val liveGamesAdapter: LiveGamesAdapter by lazy { LiveGamesAdapter(gameViewModel, this.requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.liveGamesReyclerView.apply {
            setHasFixedSize(true)
            adapter = liveGamesAdapter
        }

        binding.searchButton.setOnClickListener {
            FilterDialogFragment().apply {
                this.isCancelable = true
            }.also {
                it.show((activity as MainActivity).supportFragmentManager, it.tag)
            }
        }

        gameViewModel.listOfLiveGames.observe(viewLifecycleOwner) { listOfLiveGames ->
            liveGamesAdapter.submitList(listOfLiveGames)
        }

        gameViewModel.allCachedGames.observe(viewLifecycleOwner) {
            Log.d("Cache", it.toString())
        }
    }
}