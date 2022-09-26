package id.hizari.common.list.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.databinding.ListItemLoadMoreDefaultBinding
import id.hizari.common.list.DiffableListItemType

/**
 * Sound Tweet - id.hizari.common.list
 *
 * Created by Hudio Hizari on 26/09/2022.
 * https://github.com/hudiohizari
 *
 */

class DefaultLoadMoreListItem(
    val listener: Listener
) : AbstractBindingItem<ListItemLoadMoreDefaultBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadMoreDefaultBinding {
        return ListItemLoadMoreDefaultBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadMoreDefaultBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        listener.onLoadMore(true)
    }

    override fun unbindView(binding: ListItemLoadMoreDefaultBinding) {
        super.unbindView(binding)
        listener.onLoadMore(false)
    }

    interface Listener {
        fun onLoadMore(isLoadMore: Boolean)
    }
}