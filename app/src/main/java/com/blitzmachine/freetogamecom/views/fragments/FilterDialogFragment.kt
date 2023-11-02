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
                0 -> gameViewModel.fetchNewData(selectedPlatform.value)
                1 -> gameViewModel.fetchNewData(selectedPlatform.value, selectedGenres[0].value)
                else -> {
                    val tags = selectedGenres.joinToString(".") { genre -> genre.value }
                    gameViewModel.fetchNewFilteredData(tags, selectedPlatform.value)
                }
            }
            this.dismiss()
        }

        // Null-Check for not existing Tags.
        Platform.values().map { platform ->
            generateChip(platform.value, this.requireContext()).also { chip ->
                chip.isChecked = chip.text == "All"
                binding.platformChipGroup.addView(chip)
            }
        }

        // ChipGroup currently on singleSelection for testing before multiple selection can be done
        Genre.values().map { genre ->
            generateChip(genre.value, this.requireContext()).also { chip ->
                binding.genreChipGroup.addView(chip)
            }
        }
    }

    private fun getPlatformSelection(chipGroup: ChipGroup): Platform {
        return Platform.valueOf(getSelectChip(chipGroup).text.toString().uppercase())
    }

    private fun getGenreSelections(chipGroup: ChipGroup): List<Genre> {
        val selectedGenre = emptyList<Genre>().toMutableList()
        getSelectChips(chipGroup).map {chipObject ->
            when (chipObject.text.toString()) {
                Genre.THREE_D.value -> selectedGenre.add(Genre.THREE_D)
                Genre.TWO_D.value -> selectedGenre.add(Genre.TWO_D)
                else -> selectedGenre.add(Genre.valueOf(chipObject.text.toString().uppercase().replace("-", "_")))
            }
        }
        return selectedGenre
    }

    private fun getSelectChip(chipGroup: ChipGroup): Chip {
        return chipGroup.findViewById(chipGroup.checkedChipId)
    }

    private fun getSelectChips(chipGroup: ChipGroup): List<Chip> {
        return chipGroup.checkedChipIds.map { chipId -> chipGroup.findViewById(chipId) }
    }

    private fun generateChip(text: String, context: Context): Chip {
        return Chip(context).apply {
            this.text = text.replaceFirstChar { it.uppercase() }
            this.id = View.generateViewId()
            this.isCheckable = true
            this.isClickable = true
            this.isEnabled = true
            this.chipBackgroundColor = context.getColorStateList(R.color.chip_background_color)
        }
    }
}