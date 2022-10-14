package id.hizari.soundtweet.ui.tweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseBottomSheetFragment
import id.hizari.soundtweet.databinding.BottomSheetEditCaptionBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 14/10/2022.
 * https://github.com/hudiohizari
 *
 */

@AndroidEntryPoint
class EditCaptionBottomSheet : BaseBottomSheetFragment(), EditCaptionViewModel.Listener {

    private lateinit var binding: BottomSheetEditCaptionBinding
    private val viewModel: EditCaptionViewModel by viewModels()

    companion object {
        const val TWEET_ID = "tweetId_editCaptionBottomSheet"
        const val EXISTING_CAPTION = "existingCaption_editCaptionBottomSheet"

        fun newInstance(
            tweetId: Long?,
            existingCaption: String?
        ): EditCaptionBottomSheet {
            return EditCaptionBottomSheet().apply {
                arguments = Bundle().apply {
                    putLong(TWEET_ID, tweetId ?: -1)
                    putString(EXISTING_CAPTION, existingCaption)
                }
            }
        }
    }

    private val tweetId by lazy { arguments?.getLong(TWEET_ID) }
    private val existingCaption by lazy { arguments?.getString(EXISTING_CAPTION, null) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_edit_caption,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        initObserver()
    }

    private fun initArgs() {
        viewModel.caption.postValue(existingCaption)
    }

    private fun initObserver() {
        viewModel.setListener(this)
    }

}