package id.hizari.soundtweet.ui.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hizari.common.base.BaseFragment
import id.hizari.soundtweet.R

/**
 * Sound Tweet - id.hizari.soundtweet.ui.search.fragment
 *
 * Created by Hudio Hizari on 27/09/2022.
 * https://github.com/hudiohizari
 *
 */

class SearchFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

}