package com.blitzmachine.freetogamecom.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.GameItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.utils.Utils

class LiveGamesAdapter(
    private val gameViewModel: GameViewModel,
    private val context: Context): ListAdapter<Game, LiveGamesAdapter.ItemViewHolder>(GameDiffUtil()) {

    inner class ItemViewHolder(private val itemLayoutBinding: GameItemLayoutBinding): RecyclerView.ViewHolder(itemLayoutBinding.root) {
        fun bind(item: Game) {
            with(itemLayoutBinding) {


                thumbnailImageView.load(item.thumbnail) {
                    this.placeholder(Utils.createCircularProgressDrawable(context))
                    this.error(R.drawable.image_not_available)
                    this.crossfade(true)
                    this.crossfade(1000)
                }

                gameTitleTextView.setText(item.title)
                platformChip.setText(item.platform)
                genreChip.setText(item.genre)

                startMaterialCardView.setOnClickListener {
                    gameViewModel.getDetailsOfGame(item.id)
                }

                favoriteImageButton.setImageResource(
                    if (item.isLiked) {
                        R.drawable.icon_star_filled
                    } else {
                        R.drawable.icon_star_outlined
                    }
                )

                favoriteImageButton.setOnClickListener {
                    if (item.isLiked) {
                        gameViewModel.cacheGame(item.copy(isLiked = false))
                        favoriteImageButton.setImageResource(R.drawable.icon_star_outlined)
                    } else {
                        gameViewModel.cacheGame(item.copy(isLiked = true))
                        favoriteImageButton.setImageResource(R.drawable.icon_star_filled)
                    }
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

class GameDiffUtil(): DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.game_url == newItem.game_url &&
                oldItem.genre == newItem.genre &&
                oldItem.platform == newItem.platform &&
                oldItem.developer == newItem.developer &&
                oldItem.publisher == newItem.publisher &&
                oldItem.release_date == newItem.release_date &&
                oldItem.short_description == newItem.short_description &&
                oldItem.thumbnail == newItem.thumbnail &&
                oldItem.isLiked == newItem.isLiked
    }
}