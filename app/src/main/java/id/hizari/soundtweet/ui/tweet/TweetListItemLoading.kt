package id.hizari.soundtweet.ui.tweet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.extension.loadingAnimation
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.list.DiffableListItemType
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.R
import id.hizari.soundtweet.databinding.ListItemLoadingTweetBinding
import id.hizari.soundtweet.databinding.ListItemTweetBinding

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}