package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import coil.Coil
import coil.load
import coil.util.CoilUtils
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentDetailBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.ScreenshotAdapter
import okhttp3.internal.wait

class DetailFragment : Fragment() {

    private val detailLayoutBinding: FragmentDetailBinding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by activityViewModels()
    private val screenshotAdapter: ScreenshotAdapter by lazy { ScreenshotAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = detailLayoutBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailLayoutBinding.screenshotRecyclerView.adapter = screenshotAdapter

        gameViewModel.detailsOfSingleGame.observe(viewLifecycleOwner) { game ->
            with(detailLayoutBinding) {

                screenshotAdapter.submitList(game.screenshots)
                redirectButton.setOnClickListener {
                    Intent(Intent.ACTION_VIEW, Uri.parse(game.game_url)).also {
                        startActivity(it)
                    }
                }

                detailThumbnailImageView.load(game.thumbnail)
                detailGameTitleTextView.setText(game.title)

                descriptionTextView.setText(game.description)

                detailGenreTextView.setText(game.genre)
                detailPlatformTextView.setText(game.platform)
                detailPublisherTextView.setText(game.publisher)
                detailDeveloperTextView.setText(game.developer)

                osTextView.setText(game.minimum_system_requirements?.os ?: "No Information")
                processorTextView.setText(game.minimum_system_requirements?.processor ?: "No Information")
                memoryTextView.setText(game.minimum_system_requirements?.memory ?: "No Information")
                graphicsTextView.setText(game.minimum_system_requirements?.graphics ?: "No Information")
                storageTextView.setText(game.minimum_system_requirements?.storage ?: "No Information")
            }
        }

        with(detailLayoutBinding) {
            backButton.setOnClickListener { it.findNavController().navigateUp() }

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

    private fun formatReleaseDate(gameReleaseDate: String): String {
        gameReleaseDate.split("-").also {
            return "${it[2]}.${it[1]}.${it[0]}"
        }
    }
}
