package com.blitzmachine.freetogamecom.views.fragments

import android.animation.LayoutTransition
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import at.blogc.android.views.ExpandableTextView
import coil.load
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.databinding.FragmentBottomSheetBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDetailsFragment : BottomSheetDialogFragment() {

    private val bottomSheetLayoutBinding: FragmentBottomSheetBinding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return bottomSheetLayoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.detailsOfSingleGame.observe(viewLifecycleOwner) { game ->
            with(bottomSheetLayoutBinding) {
                detailGameTitleTextView.setText(game.title)
                expandableTextView.setText(game.description)

                osTextView.setText(game.minimum_system_requirements.os)
                processorTextView.setText(game.minimum_system_requirements.processor)
                memoryTextView.setText(game.minimum_system_requirements.memory)
                graphicsTextView.setText(game.minimum_system_requirements.graphics)
                storageTextView.setText(game.minimum_system_requirements.storage)
            }
        }


        with(bottomSheetLayoutBinding) {
            expandableTextView.setAnimationDuration(700L)
            expandableTextView.setInterpolator(OvershootInterpolator())
            descriptionExpandHandler.setOnClickListener {
                if (expandableTextView.isExpanded) {
                    descriptionExpandHandler.setImageResource(R.drawable.arrow_dropdown)
                    expandableTextView.collapse()
                } else {
                    descriptionExpandHandler.setImageResource(R.drawable.arrow_dropup)
                    expandableTextView.expand()
                }
            }

            informationExpandHandler.setOnClickListener {
                if (gameInformationLayout.visibility == View.VISIBLE) {
                    gameInformationLayout.visibility = View.GONE
                } else {
                    gameInformationLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //uiViewModel.showMainLogo(true)
    }
}