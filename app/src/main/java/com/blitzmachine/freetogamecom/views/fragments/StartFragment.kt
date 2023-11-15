package com.blitzmachine.freetogamecom.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.views.LiveGamesAdapter
import com.blitzmachine.freetogamecom.databinding.FragmentStartBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.UiViewModel

class StartFragment : Fragment() {

    private val binding: FragmentStartBinding by lazy { FragmentStartBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by activityViewModels()
    private val liveGamesAdapter: LiveGamesAdapter by lazy { LiveGamesAdapter(gameViewModel, this.requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (binding) {
            scrollToBeginFAB.setOnClickListener {
                liveGamesReyclerView.scrollToPosition(0)
            }

            liveGamesReyclerView.apply {
                setHasFixedSize(true)
                adapter = liveGamesAdapter
                addOnScrollListener(object : OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.computeVerticalScrollOffset() > 1200) {
                            if (scrollToBeginFAB.visibility != View.VISIBLE) {
                                scrollToBeginFAB.visibility = View.VISIBLE
                            }
                        } else if (recyclerView.computeVerticalScrollOffset() < 1200) {
                            scrollToBeginFAB.visibility = View.INVISIBLE
                        }
                    }
                })
            }

            searchButton.setOnClickListener {
                FilterDialogFragment().apply {
                    this.isCancelable = true
                }.also { dialog ->
                    dialog.show((activity as MainActivity).supportFragmentManager, dialog.tag)
                }
            }
        }

        gameViewModel.cachedGames.observe(viewLifecycleOwner) { cachedGames ->
            if (!gameViewModel.isFiltered) {
                liveGamesAdapter.submitList(cachedGames)
                binding.liveGamesReyclerView.scrollToPosition(0)
            }
        }

        gameViewModel.filteredCachedGames.observe(viewLifecycleOwner) { filteredCachedGames ->
            if (filteredCachedGames.isEmpty()) {
                uiViewModel.enableErrorScreen("NO RESULT", "No information about games found. Maybe we do not own the information of a game containing your selected Genre!")
            } else {
                liveGamesAdapter.submitList(filteredCachedGames)
                uiViewModel.enableLoadingScreen()
                binding.liveGamesReyclerView.scrollToPosition(0)
            }
        }
    }
}