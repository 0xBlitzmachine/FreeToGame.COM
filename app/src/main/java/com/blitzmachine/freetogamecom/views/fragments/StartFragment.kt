package com.blitzmachine.freetogamecom.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blitzmachine.freetogamecom.LiveGamesAdapter
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.databinding.FragmentStartBinding
import com.blitzmachine.freetogamecom.views.GameViewModel

class StartFragment : Fragment() {

    private val startLayoutBinding: FragmentStartBinding by lazy { FragmentStartBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by activityViewModels()
    private val liveGamesAdapter: LiveGamesAdapter by lazy { LiveGamesAdapter(gameViewModel, uiViewModel) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = startLayoutBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startLayoutBinding.liveGamesReyclerView.apply {
            setHasFixedSize(true)
            adapter = liveGamesAdapter
        }

        gameViewModel.allLiveGames.observe(viewLifecycleOwner) { changedListOfLiveGames ->
            liveGamesAdapter.submitList(changedListOfLiveGames)
        }
    }

    override fun onResume() {
        super.onResume()
        uiViewModel.showMainLogo(true)
    }
}