package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import coil.load
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentDetailBinding
import com.blitzmachine.freetogamecom.utils.Utils
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.ScreenshotAdapter

class DetailFragment : Fragment() {

    private val binding: FragmentDetailBinding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val screenshotAdapter: ScreenshotAdapter by lazy { ScreenshotAdapter(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.screenshotRecyclerView.adapter = screenshotAdapter

        gameViewModel.detailsOfSingleGame.observe(viewLifecycleOwner) { game ->
            with(binding) {

                screenshotAdapter.submitList(game.screenshots)
                redirectButton.setOnClickListener {
                    Intent(Intent.ACTION_VIEW, Uri.parse(game.game_url)).also {
                        startActivity(it)
                    }
                }

                detailThumbnailImageView.load(game.thumbnail) {
                    this.placeholder(Utils.createCircularProgressDrawable(requireContext()))
                    this.error(R.drawable.logo_footer)
                    this.crossfade(true)
                    this.crossfade(2000)
                }


                detailGameTitleTextView.setText(game.title)
                descriptionTextView.setText(game.description)

                detailGenreTextView.setText(game.genre)
                detailPlatformTextView.setText(game.platform)
                detailPublisherTextView.setText(game.publisher)
                detailDeveloperTextView.setText(game.developer)
                detailReleaseDateTextView.setText(formatReleaseDate(game.release_date))

                osTextView.setText(game.minimum_system_requirements?.os ?: "No Information")
                processorTextView.setText(game.minimum_system_requirements?.processor ?: "No Information")
                memoryTextView.setText(game.minimum_system_requirements?.memory ?: "No Information")
                graphicsTextView.setText(game.minimum_system_requirements?.graphics ?: "No Information")
                storageTextView.setText(game.minimum_system_requirements?.storage ?: "No Information")
            }
        }

        with(binding) {
            backButton.setOnClickListener {
                it.findNavController().navigateUp()
            }

            val expandHandlers = listOf<ImageView> (
                descriptionExpandHandler,
                informationExpandHandler,
                specsExpandHandler
            )

            val expandedRootLayouts = listOf<LinearLayout>(
                gameDescriptionLayout,
                gameInformationLayout,
                gameSpecsLayout
            )

            var indexer = 0
            do {
                setupExpandableCardView(expandHandlers[indexer], expandedRootLayouts[indexer])
                expandedRootLayouts[indexer].layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                indexer++
            } while (expandHandlers.size != indexer)
        }
    }

    private fun setupExpandableCardView(expandHandler: ImageView, rootLayout: LinearLayout) {
        expandHandler.setOnClickListener {
            rootLayout.visibility = when (rootLayout.visibility) {
                View.GONE -> {
                    expandHandler.setImageResource(R.drawable.arrow_dropup)
                    View.VISIBLE
                }
                else -> {
                    expandHandler.setImageResource(R.drawable.arrow_dropdown)
                    View.GONE
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
