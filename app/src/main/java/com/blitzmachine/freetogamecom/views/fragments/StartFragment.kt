package com.blitzmachine.freetogamecom.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.views.LiveGamesAdapter
import com.blitzmachine.freetogamecom.databinding.FragmentStartBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

        startLayoutBinding.searchButton.setOnClickListener {
            BottomSheetDetailsFragment().apply {
                this.isCancelable = true
            }.also {
                it.show((activity as MainActivity).supportFragmentManager, it.tag)
            }
        }

        gameViewModel.listOfLiveGames.observe(viewLifecycleOwner) { listOfLiveGames ->
            liveGamesAdapter.submitList(listOfLiveGames)
        }
    }
}