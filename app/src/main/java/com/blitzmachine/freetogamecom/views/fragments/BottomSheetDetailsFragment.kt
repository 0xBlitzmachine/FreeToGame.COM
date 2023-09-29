package com.blitzmachine.freetogamecom.views.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.blitzmachine.freetogamecom.databinding.FragmentBottomSheetBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val detailsBottomSheetTag = "DetailsBottomSheet"

class BottomSheetDetailsFragment : BottomSheetDialogFragment() {

    private val bottomSheetLayoutBinding: FragmentBottomSheetBinding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = bottomSheetLayoutBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.detailsOfSingleGame.observe(viewLifecycleOwner) { game ->
            with(bottomSheetLayoutBinding) {
                textView.setText(game.title)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //uiViewModel.showMainLogo(true)
    }
}