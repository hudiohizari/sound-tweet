package id.hizari.soundtweet.ui.tweet

import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.toast
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseViewModel
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 14/10/2022.
 * https://github.com/hudiohizari
 *
 */


@HiltViewModel
class EditCaptionViewModel @Inject constructor(): BaseViewModel() {

    val caption = MutableLiveData<String>()

    fun View.onClickSave() {
        context.apply { toast(getString(R.string.tweet_caption_edited)) }
        listener?.dismiss()
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun dismiss()
    }

}