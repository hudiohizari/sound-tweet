package id.hizari.soundtweet.ui.tweet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.hizari.common.extension.isNotNullOrEmpty
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseBottomSheetFragment
import id.hizari.soundtweet.databinding.BottomSheetSeeVoiceAsTextBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 14/10/2022.
 * https://github.com/hudiohizari
 *
 */

class SeeVoiceAsTextBottomSheet: BaseBottomSheetFragment() {

    companion object {
        const val TEXT = "text_seeVoiceAsTextBottomSheet"

        fun newInstance(
            text: String?
        ): SeeVoiceAsTextBottomSheet {
            return SeeVoiceAsTextBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(TEXT, text)
                }
            }
        }
    }

    private lateinit var binding: BottomSheetSeeVoiceAsTextBinding

    private val text by lazy { arguments?.getString(TEXT, null) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_see_voice_as_text,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvVoiceAsText.text = if (text.isNotNullOrEmpty()) text else {
            getString(R.string.audio_as_text_failed)
        }
    }

}