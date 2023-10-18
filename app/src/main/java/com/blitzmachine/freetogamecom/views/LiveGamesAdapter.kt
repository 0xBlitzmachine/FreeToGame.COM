package com.blitzmachine.freetogamecom.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.GameItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Games
import com.blitzmachine.freetogamecom.views.fragments.UiViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope

class LiveGamesAdapter(
    private val gameViewModel: GameViewModel,
    private val uiViewModel: UiViewModel): ListAdapter<Games, LiveGamesAdapter.ItemViewHolder>(GameDiffUtil()) {

    inner class ItemViewHolder(private val itemLayoutBinding: GameItemLayoutBinding): RecyclerView.ViewHolder(itemLayoutBinding.root) {
        fun bind(item: Games) {
            with(itemLayoutBinding) {

                gameViewModel.loadImage(item, thumbnailImageView, root.context)

                gameTitleTextView.setText(item.title)
                platformChip.setText(item.platform)
                genreChip.setText(item.genre)

                startMaterialCardView.setOnClickListener {
                    gameViewModel.getDetailsOfGame(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(GameItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class GameDiffUtil(): DiffUtil.ItemCallback<Games>() {
    override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.game_url == newItem.game_url &&
                oldItem.genre == newItem.genre &&
                oldItem.platform == newItem.platform &&
                oldItem.developer == newItem.developer &&
                oldItem.publisher == newItem.publisher &&
                oldItem.release_date == newItem.release_date &&
                oldItem.short_description == newItem.short_description &&
                oldItem.thumbnail == newItem.thumbnail
    }
}