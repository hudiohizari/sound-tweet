package id.hizari.soundtweet.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.extension.setupHighlightedText
import id.hizari.common.list.DiffableListItemType
import id.hizari.domain.model.Notification
import id.hizari.soundtweet.R
import id.hizari.soundtweet.databinding.ListItemNotificationBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.notification
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

class NotificationListItem(
    private val model: Notification,
    private val listener: Listener
) : AbstractBindingItem<ListItemNotificationBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any = model.id ?: model.hashCode()

    override fun comparableContents(): List<Any> = listOf(
        model.caption ?: model.hashCode(),
        model.user?.name ?: model.hashCode()
    )

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemNotificationBinding {
        return ListItemNotificationBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemNotificationBinding, payloads: List<Any>) {
        binding.item = model

        binding.tvText.apply {
            val originText = context.getString(
                R.string.notification_text_format,
                model.user?.name,
                model.caption
            )
            val highlightedTexts = arrayOf("${model.user?.name}" as String?)
            val highlightedColors = arrayOf(R.color.cinder as Int?)
            setupHighlightedText(
                originText, highlightedTexts, highlightedColors, isBold = true
            )
        }

        binding.onClick = View.OnClickListener { listener.onClick(model) }
    }

    interface Listener {
        fun onClick(item: Notification)
    }

}