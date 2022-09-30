package id.hizari.soundtweet.ui.tweet

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.extension.loadingAnimation
import id.hizari.common.list.DiffableListItemType
import id.hizari.soundtweet.databinding.ListItemLoadingTweetBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class TweetListItemLoading : AbstractBindingItem<ListItemLoadingTweetBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadingTweetBinding {
        return ListItemLoadingTweetBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadingTweetBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.container.loadingAnimation()
    }

}