package id.hizari.soundtweet.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hizari.common.extension.loadingAnimation
import id.hizari.common.list.DiffableListItemType
import id.hizari.soundtweet.databinding.ListItemLoadingUserBinding

/**
 * Sound Tweet - id.hizari.soundtweet.ui.user
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

class UserListItemLoading : AbstractBindingItem<ListItemLoadingUserBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadingUserBinding {
        return ListItemLoadingUserBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadingUserBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.container.loadingAnimation()
    }

}