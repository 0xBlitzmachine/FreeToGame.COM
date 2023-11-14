package com.blitzmachine.freetogamecom.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentDialogFilterBinding
import com.blitzmachine.freetogamecom.utils.GenreObserver
import com.blitzmachine.freetogamecom.utils.PlatformObserver
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


        PlatformObserver(gameViewModel).observe(viewLifecycleOwner) { platformCollection ->
            platformCollection.map { platform ->
                generateChip(
                    if (platform == "PC (Windows), Web Browser") {
                    "Both"
                } else {
                       platform
                }, this.requireContext()).also { chip ->
                    binding.platformChipGroup.addView(chip)
                }
            }
            val chips = emptyList<Chip>().toMutableList()
            for (i in 0 until binding.platformChipGroup.childCount) {
                chips += binding.platformChipGroup.getChildAt(i) as Chip
            }
            chips.find { chip -> chip.text == "Both" }.also { foundChip ->
                binding.platformChipGroup.check(foundChip!!.id)
            }
        }

        GenreObserver(gameViewModel).observe(viewLifecycleOwner) { genreCollection ->
            genreCollection.map { genre ->
                generateChip(genre, this.requireContext()).also { chip ->
                    binding.genreChipGroup.addView(chip)
                }
            }
        }

        binding.backButton.setOnClickListener {
            this.dismiss()
        }

        binding.filterButton.setOnClickListener {
            try {
                val selectedPlatform = getPlatformSelection(binding.platformChipGroup)
                val selectedGenre = getGenreSelections(binding.genreChipGroup)

                if (selectedGenre.isEmpty()) {
                    gameViewModel.isFiltered = true
                    gameViewModel.getFilteredCachedGames(selectedPlatform)
                } else {
                    gameViewModel.isFiltered = true
                    gameViewModel.getFilteredCachedGames(selectedPlatform, selectedGenre)
                }
            } catch (ex: Exception) {
                Log.e("FilterDialogFragment", "OnFilterGames failed! - ${ex.message}")
                this.dismiss()
            }
            this.dismiss()
        }
    }

    private fun getPlatformSelection(chipGroup: ChipGroup): Set<String> {
        val selectedChip = getSelectChip(chipGroup)
        val selection = mutableSetOf<String>()

        when (selectedChip.text) {
            "PC (Windows)" -> {
                selection += selectedChip.text.toString()
            }
            "Web Browser" -> {
                selection += selectedChip.text.toString()
            }
            else -> {
                selection += "PC (Windows), Web Browser"
                selection += "PC (Windows)"
                selection += "Web Browser"
            }
        }
        return selection
    }

    private fun getGenreSelections(chipGroup: ChipGroup): Set<String> {
        val selectedChips = getSelectChips(chipGroup)
        val selection = mutableSetOf<String>()

        if (selectedChips.isEmpty()) {
            return emptySet()
        }

        for (chip in selectedChips) {
            if (selection.contains(chip.text.toString())) {
                continue
            }
            selection += chip.text.toString()
        }
        return selection
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