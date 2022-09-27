package id.hizari.common.list.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.databinding.ListItemEmptyDefaultBinding
import id.hizari.common.list.DiffableListItemType

class DefaultEmptyListItem(
    private val label: String? = null,
    private val caption: String? = null
) : AbstractBindingItem<ListItemEmptyDefaultBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemEmptyDefaultBinding {
        return ListItemEmptyDefaultBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemEmptyDefaultBinding, payloads: List<Any>) {
        binding.label = label
        binding.caption = caption
    }
}