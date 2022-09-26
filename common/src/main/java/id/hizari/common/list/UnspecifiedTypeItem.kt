package id.hizari.common.list

import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

/**
 * Sound Tweet - id.hizari.common.list
 *
 * Created by Hudio Hizari on 26/09/2022.
 * https://github.com/hudiohizari
 *
 */

typealias UnspecifiedTypeItem = IItem<*>

operator fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.plusAssign(item: Item) {
    add(item)
    notifyAdapterDataSetChanged()
}

operator fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.plusAssign(items: List<Item>) {
    items.forEach { add(it) }
    notifyAdapterDataSetChanged()
}

fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.performUpdates(newItems: List<Item>) {
    val diffResult = FastAdapterDiffUtil.calculateDiff(this.itemAdapter, newItems, DiffableCallback())
    FastAdapterDiffUtil[this.itemAdapter] = diffResult
}