package com.blitzmachine.freetogamecom.views.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = detailLayoutBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.detailsOfSingleGame.observe(viewLifecycleOwner) { game ->
            with(detailLayoutBinding) {

                detailThumbnailImageView.load(game.thumbnail)
                detailGameTitleTextView.setText(game.title)
                platformDetailChip.setText(game.platform)
                genreDetailChip.setText(game.genre)
                releaseDateDetailChip.setText(formatReleaseDate(game.release_date))
                publisherDetailChip.setText(game.publisher)
                developerDetailChip.setText(game.developer)
                descriptionDetailTextView.setText(game.description)
            }
        }
    }

    private fun formatReleaseDate(gameReleaseDate: String): String {
        gameReleaseDate.split("-").also {
            return "${it[2]}.${it[1]}.${it[0]}"
        }
    }
}
