package id.hizari.common.extension

import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.hizari.common.R
import id.hizari.common.list.item.decoration.DefaultDividerItemDecoration

/**
 * Sound Tweet - id.hizari.common.extension
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

fun RecyclerView.addDividerItem(
    skipDividerIndex: MutableList<Int> = mutableListOf()
) {
    DefaultDividerItemDecoration(ColorDrawable(ContextCompat.getColor(context, R.color.pale_sky)),
        skipDividerIndex
    ).also { addItemDecoration(it) }
}