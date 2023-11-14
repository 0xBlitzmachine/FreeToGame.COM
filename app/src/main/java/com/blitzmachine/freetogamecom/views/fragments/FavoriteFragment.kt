package com.blitzmachine.freetogamecom.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.blitzmachine.freetogamecom.databinding.FragmentFavoriteBinding
import com.blitzmachine.freetogamecom.views.FavoriteAdapter
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.LiveGamesAdapter

class FavoriteFragment : Fragment() {

    private val favoriteLayoutBinding: FragmentFavoriteBinding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter(gameViewModel, this.requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = favoriteLayoutBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (favoriteLayoutBinding) {
            favoriteRecyclerView.apply {
                setHasFixedSize(true)
                adapter = favoriteAdapter
            }
        }

        gameViewModel.cachedGames.observe(viewLifecycleOwner) { games ->
            favoriteAdapter.submitList(games.filter { game -> game.isLiked })
        }
    }
}