package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.opengl.Visibility
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import coil.load
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentDetailBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DetailFragment : Fragment() {

    private val detailLayoutBinding: FragmentDetailBinding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = detailLayoutBinding.root
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.detailsOfSingleGame.observe(viewLifecycleOwner) { game ->
            with(detailLayoutBinding) {
                detailLayoutBinding.detailThumbnailImageView.load(game.thumbnail)
                detailGameTitleTextView.setText(game.title)
                expandableTextView.setText(game.description)
                osTextView.setText(game.minimum_system_requirements.os)
                processorTextView.setText(game.minimum_system_requirements.processor)
                memoryTextView.setText(game.minimum_system_requirements.memory)
                graphicsTextView.setText(game.minimum_system_requirements.graphics)
                storageTextView.setText(game.minimum_system_requirements.storage)

                expandableTextView.setAnimationDuration(500L)
                expandableTextView.setInterpolator(FastOutLinearInInterpolator())
                descriptionExpandHandler.setOnClickListener {
                    if (expandableTextView.isExpanded) {
                        descriptionExpandHandler.setImageResource(R.drawable.arrow_dropdown)
                        expandableTextView.collapse()
                    } else {
                        descriptionExpandHandler.setImageResource(R.drawable.arrow_dropup)
                        expandableTextView.expand()
                    }
                }

                //gameInformationCardView.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                informationExpandHandler.setOnClickListener {
                    if (gameInformationLayout.visibility == View.VISIBLE) {
                        gameInformationLayout.visibility = View.GONE
                    } else {
                        gameInformationLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uiViewModel.showMainLogo(true)
    }
    private fun formatReleaseDate(gameReleaseDate: String): String {
        gameReleaseDate.split("-").also {
            return "${it[2]}.${it[1]}.${it[0]}"
        }
    }
}
