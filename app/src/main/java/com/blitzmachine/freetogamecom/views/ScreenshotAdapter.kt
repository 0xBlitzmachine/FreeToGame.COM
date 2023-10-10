package com.blitzmachine.freetogamecom.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blitzmachine.freetogamecom.databinding.ScreenshotItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.GameScreenshots

class ScreenshotAdapter: ListAdapter<GameScreenshots, ScreenshotAdapter.ItemViewHolder>(ScreenshotDiff()) {
    inner class ItemViewHolder(private val binding: ScreenshotItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameScreenshots) {
            binding.screenshotImageView.load(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ScreenshotItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class ScreenshotDiff: DiffUtil.ItemCallback<GameScreenshots>() {
    override fun areItemsTheSame(oldItem: GameScreenshots, newItem: GameScreenshots): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GameScreenshots, newItem: GameScreenshots): Boolean {
        return oldItem.id == newItem.id && oldItem.image == newItem.image
    }

}