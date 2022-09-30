package id.hizari.soundtweet.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

abstract class BaseContextViewModel(application: Application) : AndroidViewModel(application) {

    val context: Context by lazy { getApplication<App>().baseContext }

}