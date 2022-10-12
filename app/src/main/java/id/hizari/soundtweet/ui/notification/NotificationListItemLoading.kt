package id.hizari.soundtweet.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.extension.loadingAnimation
import id.hizari.common.list.DiffableListItemType
import id.hizari.soundtweet.databinding.ListItemLoadingNotificationBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.notification
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

class NotificationListItemLoading : AbstractBindingItem<ListItemLoadingNotificationBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadingNotificationBinding {
        return ListItemLoadingNotificationBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadingNotificationBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.container.loadingAnimation()
    }

}