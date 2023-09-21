package com.blitzmachine.freetogamecom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blitzmachine.freetogamecom.databinding.GameItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.Game
import com.google.android.material.chip.Chip

class LiveGamesAdapter: ListAdapter<Game, LiveGamesAdapter.ItemViewHolder>(GameDiffUtil()) {
    inner class ItemViewHolder(private val itemLayoutBinding: GameItemLayoutBinding): RecyclerView.ViewHolder(itemLayoutBinding.root) {
        fun bind(item: Game) {
            with(itemLayoutBinding) {
                thumbnailImageView.load(item.thumbnail)
                gameTitleTextView.setText(item.title)
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

class GameDiffUtil(): DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
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