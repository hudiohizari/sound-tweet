package id.hizari.soundtweet.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.list.DiffableListItemType
import id.hizari.domain.model.User
import id.hizari.soundtweet.databinding.ListItemUserBinding

/**
 * Sound User - id.hizari.soundUser.ui.user
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

class UserListItem(
    private val model: User,
    private val listener: Listener
) : AbstractBindingItem<ListItemUserBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any = model.id ?: model.hashCode()

    override fun comparableContents(): List<Any> = listOf(
        model.name ?: model.hashCode(),
        model.userName ?: model.hashCode(),
        model.isFollowed ?: model.hashCode()
    )

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemUserBinding {
        return ListItemUserBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemUserBinding, payloads: List<Any>) {
        binding.item = model
        binding.onClick = View.OnClickListener { listener.onClick(model) }
        binding.onClickFollow = View.OnClickListener { listener.onClickFollow(model) }
    }

    interface Listener {
        fun onClick(item: User)
        fun onClickFollow(item: User)
    }

}