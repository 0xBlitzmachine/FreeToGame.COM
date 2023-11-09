package com.blitzmachine.freetogamecom.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
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

        // Sprichst die ChipGroup and und mit "check" selectest du ein Chip.
        binding.backButton.setOnClickListener {
            this.dismiss()
        }

        binding.filterButton.setOnClickListener {
            val selectedPlatform = getPlatformSelection(binding.platformChipGroup)
            val selectedGenre = getGenreSelections(binding.genreChipGroup)
        }





        val genreCollection = mutableSetOf<String>()
        for (cachedGame in gameViewModel.cachedGames.value!!) {
            if (genreCollection.contains(cachedGame.genre)) {
                continue
            }
            genreCollection += cachedGame.genre
            generateChip(cachedGame.genre, this.requireContext()).also { chipObject ->
                binding.genreChipGroup.addView(chipObject)
            }
        }

        val platformCollection = mutableSetOf<String>()
        for (cachedGame in gameViewModel.cachedGames.value!!) {
            if (platformCollection.contains(cachedGame.platform)) {
                continue
            }
            platformCollection += cachedGame.platform
            generateChip(cachedGame.platform, this.requireContext()).also { chipObject ->
                    binding.platformChipGroup.addView(chipObject)
            }
        }

        binding.platformChipGroup.isSelectionRequired = true
        binding.platformChipGroup.isSingleSelection = true
    }

    private fun getPlatformSelection(chipGroup: ChipGroup): String? {
        val selectedChip = getSelectChip(chipGroup)

        return if (selectedChip.text == "PC (Windows), Web Browser") {
            null
        } else {
            selectedChip.text.toString()
        }
    }

    private fun getGenreSelections(chipGroup: ChipGroup): List<String>? {
        val selectedChips = getSelectChips(chipGroup)
        val genreCollection = mutableSetOf<String>()

        if (selectedChips.isEmpty()) {
            return null
        }

        for (chip in selectedChips) {
            if (genreCollection.contains(chip.text.toString())) {
                continue
            }
            genreCollection += chip.text.toString()
        }
        return genreCollection.toList()
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