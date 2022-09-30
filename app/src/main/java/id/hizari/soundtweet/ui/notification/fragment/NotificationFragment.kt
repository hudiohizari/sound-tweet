package id.hizari.soundtweet.ui.notification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hizari.soundtweet.base.BaseFragment
import id.hizari.soundtweet.R

/**
 * Sound Tweet - id.hizari.soundtweet.ui.notification.fragment
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

class NotificationFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

}