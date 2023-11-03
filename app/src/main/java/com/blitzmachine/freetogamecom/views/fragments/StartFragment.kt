package com.blitzmachine.freetogamecom.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
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

        with (binding) {
            scrollToBeginFAB.setOnClickListener {
                it.visibility = View.INVISIBLE
                liveGamesReyclerView.scrollToPosition(0)
            }

            liveGamesReyclerView.apply {
                setHasFixedSize(true)
                adapter = liveGamesAdapter
                addOnScrollListener(object : OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (recyclerView.computeVerticalScrollOffset() > 1000) {
                            if (scrollToBeginFAB.visibility != View.VISIBLE) {
                                scrollToBeginFAB.visibility = View.VISIBLE
                            }
                        }
                    }

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (newState == SCROLL_STATE_DRAGGING) {
                            Log.d("Scroll", "SCROLL_STATE_DRAGGING")
                        }
                    }
                })
            }

            searchButton.setOnClickListener {
                FilterDialogFragment().apply {
                    this.isCancelable = true
                }.also {
                    it.show((activity as MainActivity).supportFragmentManager, it.tag)
                }
            }
        }

        gameViewModel.cachedGames.observe(viewLifecycleOwner) { cachedGames ->
            liveGamesAdapter.submitList(cachedGames)
        }
    }
}