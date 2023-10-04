package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentDetailBinding
import com.blitzmachine.freetogamecom.views.GameViewModel

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

                descriptionTextView.setText(game.description)

                detailGenreTextView.setText(game.genre)
                detailPlatformTextView.setText(game.platform)
                detailPublisherTextView.setText(game.publisher)
                detailDeveloperTextView.setText(game.developer)

                osTextView.setText(game.minimum_system_requirements.os)
                processorTextView.setText(game.minimum_system_requirements.processor)
                memoryTextView.setText(game.minimum_system_requirements.memory)
                graphicsTextView.setText(game.minimum_system_requirements.graphics)
                storageTextView.setText(game.minimum_system_requirements.storage)

            }
        }
        with(detailLayoutBinding) {
            gameDescriptionLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            gameInformationLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            gameSpecsLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            detailScrollView.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            descriptionExpandHandler.setOnClickListener {
                if (gameDescriptionLayout.visibility == View.VISIBLE) {
                    descriptionExpandHandler.setImageResource(R.drawable.arrow_dropdown)
                    gameDescriptionLayout.visibility = View.GONE
                } else {
                    descriptionExpandHandler.setImageResource(R.drawable.arrow_dropup)
                    gameDescriptionLayout.visibility = View.VISIBLE
                }
            }

            informationExpandHandler.setOnClickListener {
                if (gameInformationLayout.visibility == View.VISIBLE) {
                    informationExpandHandler.setImageResource(R.drawable.arrow_dropdown)
                    gameInformationLayout.visibility = View.GONE
                } else {
                    informationExpandHandler.setImageResource(R.drawable.arrow_dropup)
                    gameInformationLayout.visibility = View.VISIBLE
                }
            }

            specsExpandHandler.setOnClickListener {
                if (gameSpecsLayout.visibility == View.VISIBLE) {
                    specsExpandHandler.setImageResource(R.drawable.arrow_dropdown)
                    gameSpecsLayout.visibility = View.GONE
                } else {
                    specsExpandHandler.setImageResource(R.drawable.arrow_dropup)
                    gameSpecsLayout.visibility = View.VISIBLE
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
