package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
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

class BottomSheetDetailsFragment : BottomSheetDialogFragment() {

    private val bottomSheetLayoutBinding: FragmentBottomSheetBinding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return bottomSheetLayoutBinding.root
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Genre.values().forEach { genre ->
            Chip(this.context).also {chip ->
                chip.text = genre.value
                bottomSheetLayoutBinding.chipGroup.addView(chip)
            }
        }

        Platform.values().forEach {
            RadioButton(this.context).also {radioButton ->
                radioButton.text = it.value
                radioButton.id = View.generateViewId()
                bottomSheetLayoutBinding.platformRadioGroup.addView(radioButton)
            }
        }
            // Handle Filter
            // Enum for Platform and Genre
            // gameViewModel.getAllLiveGames("platform", "category", "sortBy")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}