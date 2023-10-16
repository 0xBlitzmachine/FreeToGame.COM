package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
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

        bottomSheetLayoutBinding.backButton.setOnClickListener {
            this.dismiss()
        }

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