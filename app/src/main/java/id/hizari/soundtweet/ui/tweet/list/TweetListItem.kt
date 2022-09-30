package id.hizari.soundtweet.ui.tweet.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.list.DiffableListItemType
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.databinding.ListItemTweetBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet.list
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class TweetListItem(
    private val model: Tweet,
    private val listener: Listener
) : AbstractBindingItem<ListItemTweetBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any = model.id ?: model.hashCode()

    override fun comparableContents(): List<Any> = listOf(
        model.imgUrl ?: model.hashCode(),
        model.name ?: model.hashCode(),
        model.userName ?: model.hashCode(),
        model.postedTime ?: model.hashCode(),
        model.caption ?: model.hashCode(),
        model.mediaUrl ?: model.hashCode(),
        model.mediaDuration ?: model.hashCode(),
        model.likes ?: model.hashCode(),
        model.comments ?: model.hashCode(),
        model.plays ?: model.hashCode(),
        model.friendsLike ?: model.hashCode(),
    )

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemTweetBinding {
        return ListItemTweetBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemTweetBinding, payloads: List<Any>) {
        binding.item = model
        binding.onClick = View.OnClickListener { listener.onClick(model) }
    }

    interface Listener {
        fun onClick(item: Tweet)
    }

}