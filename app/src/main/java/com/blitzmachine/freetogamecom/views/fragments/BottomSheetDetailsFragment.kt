package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import at.blogc.android.views.ExpandableTextView
import coil.load
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentBottomSheetBinding
import com.blitzmachine.freetogamecom.io.classes.Genre
import com.blitzmachine.freetogamecom.io.classes.Platform
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

class BottomSheetDetailsFragment : BottomSheetDialogFragment() {

    private val bottomSheetLayoutBinding: FragmentBottomSheetBinding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return bottomSheetLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bottomSheetLayoutBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            // Return true if the query has been handled by the listener.
            // Return false to let the SearchView perform the default action.
            override fun onQueryTextSubmit(query: String?): Boolean {

                // You normal Filter function of API WHEN Genre ChipGroup has ONLY ONE SELECTION
                // else use the filter Endpoint of the API.
                gameViewModel.getAllLiveGames(
                    bottomSheetLayoutBinding.platformChipGroup.findViewById<Chip?>(
                        bottomSheetLayoutBinding.platformChipGroup.checkedChipId).text.toString())
                dismiss()
                return true
            }

            // Return true if the action was handled by the listener.
            // Return false if the SearchView should perform the default action of showing any suggestions if available-
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Null-Check for not existing Tags.
        Platform.values().forEach { platform ->
            generateChip(platform.value, this.requireContext()).also { chip ->
                bottomSheetLayoutBinding.platformChipGroup.addView(chip)
            }
        }

        // ChipGroup currently on singleSelection for testing before multiple selection can be done
        Genre.values().forEach { genre ->
            generateChip(genre.value, this.requireContext()).also { chip ->
                bottomSheetLayoutBinding.genreChipGroup.addView(chip)
            }
        }
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