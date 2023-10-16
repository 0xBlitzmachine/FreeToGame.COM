package com.blitzmachine.freetogamecom.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.blitzmachine.freetogamecom.databinding.ScreenshotItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.GameScreenshots
import kotlinx.coroutines.Dispatchers

class ScreenshotAdapter(private val context: Context): ListAdapter<GameScreenshots, ScreenshotAdapter.ItemViewHolder>(ScreenshotDiff()) {
    inner class ItemViewHolder(private val binding: ScreenshotItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameScreenshots) {
            ImageRequest.Builder(context)
                .data(item.image)
                .target(
                    onStart = {
                        Log.d("Coil", "Image started to load ...")
                    },
                    onSuccess = {
                        Log.d("Coil", "Image successfully downloaded.")
                        binding.screenshotImageView.setImageDrawable(it)
                    },
                    onError = {
                        Log.d("Coil", "Image failed to load! ...")
                    }
                )
                .crossfade(true)
                .crossfade(1000)
                .build().also {
                    context.imageLoader.enqueue(it)
                }
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