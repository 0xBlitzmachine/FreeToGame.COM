package com.blitzmachine.freetogamecom.views.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentBottomSheetBinding
import com.blitzmachine.freetogamecom.io.classes.Games
import com.blitzmachine.freetogamecom.io.classes.Genre
import com.blitzmachine.freetogamecom.io.classes.Platform
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class BottomSheetFilterFragment : BottomSheetDialogFragment() {

    private val bottomSheetLayoutBinding: FragmentBottomSheetBinding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return bottomSheetLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetLayoutBinding.backButton.setOnClickListener {
            this.dismiss()
        }

        bottomSheetLayoutBinding.filterButton.setOnClickListener {
            val selectedPlatform: Platform = getPlatformSelection(bottomSheetLayoutBinding.platformChipGroup)
            val selectedGenres: List<Genre> = getGenreSelections(bottomSheetLayoutBinding.genreChipGroup)

            when (selectedGenres.size) {
                0 -> gameViewModel.getAllLiveGames(selectedPlatform.value)
                1 -> gameViewModel.getAllLiveGames(selectedPlatform.value, selectedGenres[0].value)
                else -> {
                    val tags = selectedGenres.joinToString(".") { genre -> genre.value }
                    gameViewModel.getFilteredGameList(tags, selectedPlatform.value)
                }
            }
            this.dismiss()
        }

        // Null-Check for not existing Tags.
        Platform.values().map { platform ->
            generateChip(platform.value, this.requireContext()).also { chip ->
                chip.isChecked = chip.text == "All"
                bottomSheetLayoutBinding.platformChipGroup.addView(chip)
            }
        }

        // ChipGroup currently on singleSelection for testing before multiple selection can be done
        Genre.values().map { genre ->
            generateChip(genre.value, this.requireContext()).also { chip ->
                bottomSheetLayoutBinding.genreChipGroup.addView(chip)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).also {sheetDialog ->
            sheetDialog.setOnShowListener {dialogInterface ->
                (dialogInterface as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet).also {frameLayout ->
                    if (frameLayout != null) {
                        BottomSheetBehavior.from(frameLayout).apply {
                            this.state = BottomSheetBehavior.STATE_EXPANDED
                            this.isDraggable = false
                            this.skipCollapsed = true
                        }
                    }
                }
            }
        }.also {dialog -> return dialog }
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
        return chipGroup.checkedChipIds.map { chipObject -> chipGroup.findViewById(chipObject) }
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