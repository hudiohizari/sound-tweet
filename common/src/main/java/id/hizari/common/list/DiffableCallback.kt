package id.hizari.common.list

import com.mikepenz.fastadapter.diff.DiffCallback

/**
 * Sound Tweet - id.hizari.common.list
 *
 * Created by Hudio Hizari on 26/09/2022.
 * https://github.com/hudiohizari
 *
 */

interface DiffableListItemType {
    fun itemIdentifier(): Any?
    fun comparableContents(): List<Any?>
}

class DiffableCallback<Item : UnspecifiedTypeItem> : DiffCallback<Item> {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem is DiffableListItemType && newItem is DiffableListItemType) {
            return oldItem.itemIdentifier() == newItem.itemIdentifier()
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem is DiffableListItemType && newItem is DiffableListItemType) {
            return oldItem.comparableContents().withIndex().none {
                it.value != newItem.comparableContents()[it.index]
            }
        }
        return false
    }

    override fun getChangePayload(
        oldItem: Item, oldItemPosition: Int,
        newItem: Item, newItemPosition: Int
    ): Any? = null

}