package com.blitzmachine.freetogamecom.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FavoriteItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.utils.Utils

class FavoriteAdapter(private val gameViewModel: GameViewModel, private val context: Context): ListAdapter<Game, FavoriteAdapter.ItemViewHolder>(GameDiffUtil()) {
    inner class ItemViewHolder(private val itemLayoutBinding: FavoriteItemLayoutBinding): RecyclerView.ViewHolder(itemLayoutBinding.root) {
        fun bind(item: Game) {
            with (itemLayoutBinding) {
                imageView.load(item.thumbnail) {
                    this.placeholder(Utils.createCircularProgressDrawable(context))
                    this.error(R.drawable.image_not_available)
                    this.crossfade(true)
                    this.crossfade(1000)
                }

                titleTextView.setText(item.title)

                favoriteImageButton.setOnClickListener {
                    gameViewModel.cacheGame(item.copy(isLiked = false))
                }

                favoriteCardView.setOnClickListener {
                    gameViewModel.getDetailsOfGame(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}