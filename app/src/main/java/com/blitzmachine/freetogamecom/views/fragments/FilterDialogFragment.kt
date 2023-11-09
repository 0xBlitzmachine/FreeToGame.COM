package com.blitzmachine.freetogamecom.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentDialogFilterBinding
import com.blitzmachine.freetogamecom.io.classes.Genre
import com.blitzmachine.freetogamecom.io.classes.Platform
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FilterDialogFragment : DialogFragment() {

    private val binding: FragmentDialogFilterBinding by lazy { FragmentDialogFilterBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            this.dismiss()
        }
        
        binding.filterButton.setOnClickListener {
            val selectedPlatform: Platform = getPlatformSelection(binding.platformChipGroup)
            val selectedGenres: List<Genre> = getGenreSelections(binding.genreChipGroup)

            when (selectedGenres.size) {
                0 -> gameViewModel.getFilteredCachedGames(selectedPlatform.value)
                1 -> gameViewModel.getFilteredCachedGames(selectedPlatform.value, listOf(selectedGenres[0].value))
                else -> {
                    val tags = selectedGenres.joinToString(".") { genre -> genre.value }
                    gameViewModel.getFilteredCachedGames(tags, listOf())
                }
            }
            this.dismiss()
        }


        val genreCollection = mutableSetOf<String>()
        val platformCollection = mutableSetOf<String>()

        // Unique Chip genre and platform generation, depending on the cached data
        for (cachedGame in gameViewModel.cachedGames.value!!) {
            if (genreCollection.contains(cachedGame.genre)) {
                continue
            }
            genreCollection += cachedGame.genre
            generateChip(cachedGame.genre, this.requireContext()).also { chipObject ->
                binding.genreChipGroup.addView(chipObject)
            }
        }

        for (cachedGame in gameViewModel.cachedGames.value!!) {
            if (platformCollection.contains(cachedGame.platform)) {
                continue
            }
            platformCollection += cachedGame.platform
            generateChip(
                text =
                if (cachedGame.platform == "PC (Windows), Web Browser") {
                    "Both"
                }
                else {
                    cachedGame.platform
                }, this.requireContext()).also { chipObject ->
                    binding.genreChipGroup.addView(chipObject)
            }
        }
    }

    private fun getPlatformSelection(chipGroup: ChipGroup): String {
        val selectedChip = getSelectChip(chipGroup)
        return if (selectedChip.text.toString() == "Both") {
            "PC (Windows), Web Browser"
        } else {
            selectedChip.text.toString()
        }
    }

    private fun getGenreSelections(chipGroup: ChipGroup): List<String>? {
        val selectedChips = getSelectChips(chipGroup)
        val genreCollection = mutableListOf<String>()

        if (selectedChips.isEmpty()) {
            return null
        }

        for (chip in selectedChips) {
            if (genreCollection.contains(chip.text.toString())) {
                continue
            }
            genreCollection += chip.text.toString()
        }
        return genreCollection
    }

    private fun getSelectChip(chipGroup: ChipGroup): Chip {
        return chipGroup.findViewById(chipGroup.checkedChipId)
    }

    private fun getSelectChips(chipGroup: ChipGroup): List<Chip> {
        return chipGroup.checkedChipIds.map { chipId -> chipGroup.findViewById(chipId) }
    }

    private fun generateChip(text: String, context: Context): Chip {
        return Chip(context).apply {
            this.text = text
            this.id = View.generateViewId()
            this.isCheckable = true
            this.isClickable = true
            this.isEnabled = true
            this.chipBackgroundColor = context.getColorStateList(R.color.chip_background_color)
        }
    }
}