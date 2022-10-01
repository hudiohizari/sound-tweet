package id.hizari.soundtweet.ui.tweet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.list.DiffableListItemType
import id.hizari.domain.model.Tweet
import id.hizari.soundtweet.R
import id.hizari.soundtweet.databinding.ListItemTweetBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.tweet
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
        model.caption ?: model.hashCode(),
        model.postUrl ?: model.hashCode(),
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

        binding.tvName.apply {
//            val originText = "${model.name} ${model.userName} ·${model.getPostedTimeAgo(context)}"
            val originText = "User @user ·${model.getPostedTimeAgo(context)}"
            val highlightedTexts = arrayOf("User" as String?)
            val highlightedColors = arrayOf(R.color.cinder as Int?)
            setupHighlightedText(
                originText, highlightedTexts, highlightedColors, isBold = true
            )
        }

        binding.onClick = View.OnClickListener { listener.onClick(model) }
        binding.onClickMedia = View.OnClickListener { listener.onClickMedia(model) }
//        binding.onClickLike = View.OnClickListener { listener.onClickLike(model) }
    }

    interface Listener {
        fun onClick(item: Tweet)
        fun onClickMedia(item: Tweet)
//        fun onClickLike(item: Tweet)
    }

}