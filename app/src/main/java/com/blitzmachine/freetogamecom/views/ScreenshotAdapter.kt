package com.blitzmachine.freetogamecom.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transition.Transition
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.ScreenshotItemLayoutBinding
import com.blitzmachine.freetogamecom.io.classes.GameScreenshots
import com.blitzmachine.freetogamecom.utils.Utils
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.job

class ScreenshotAdapter(private val context: Context): ListAdapter<GameScreenshots, ScreenshotAdapter.ItemViewHolder>(ScreenshotDiff()) {
    inner class ItemViewHolder(private val binding: ScreenshotItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameScreenshots) {
            binding.screenshotImageView.load(item.image) {
                this.placeholder(Utils.createCircularProgressDrawable(context))
                this.error(R.drawable.image_not_available)
                this.crossfade(true)
                this.crossfade(1000)
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