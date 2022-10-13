package id.hizari.soundtweet.base

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import id.hizari.common.list.UnspecifiedTypeItem
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet

/**
 * Sound Tweet - id.hizari.soundtweet.base
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

abstract class BaseTweetListFragment : BaseFragment() {

    private val mediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }
    }

    private var lastTweet: Tweet? = null
    private var lastPlayPosition: Int = 0
    protected var lastList = mutableListOf<Tweet>()

    abstract fun getTweetAdapter(): FastItemAdapter<UnspecifiedTypeItem>

    override fun onPause() {
        super.onPause()

        stopAudio()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    private fun initObserver() {
        mediaPlayer.apply {
            setOnBufferingUpdateListener { _, percentage ->
                STLog.d("Buffering = $percentage")
                lastTweet = lastTweet?.apply { isBuffering = percentage < 100 }
                updateAudioItem(lastTweet, lastList)
            }
            setOnCompletionListener {
                STLog.d("Audio completed")
                lastTweet = lastTweet?.apply { isPLaying = false }
                updateAudioItem(lastTweet, lastList)
                lastPlayPosition = 0
            }
            setOnPreparedListener {
                start()
                seekTo(lastPlayPosition)
            }
        }
    }

    protected fun toggleAudio(item: Tweet, list: MutableList<Tweet>) {
        if (list.isNotEmpty()) {
            if (lastTweet != item) {
                if (lastTweet?.isPLaying == true) {
                    updateAudioItem(lastTweet?.apply { isPLaying = !(isPLaying ?: false) }, list)
                }
                lastTweet = item
                lastPlayPosition = 0
            }
            updateAudioItem(
                item.apply {
                    isPLaying = !(isPLaying ?: false)
                    when (isPLaying) {
                        true -> {
                            STLog.d("Play audio")
                            postUrl?.let {
                                try {
                                    mediaPlayer.apply {
                                        reset()
                                        setDataSource(it)
                                        prepareAsync()
                                        isBuffering = true
                                    }
                                } catch (e: Exception) {
                                    STLog.e("${e.message}")
                                    mediaPlayer.reset()
                                }
                            } ?: STLog.e("URL is null")
                        }
                        false -> stopAudio()
                        else -> STLog.e("isPlaying is empty")
                    }
                },
                list
            )
        } else STLog.e("List is empty")
    }

    private fun updateAudioItem(item: Tweet?, list: MutableList<Tweet>) {
        item?.let { tweet ->
            for (i in list.indices) {
                if (list[i].id == tweet.id) {
                    list.removeAt(i)
                    list.add(i, tweet)
                    getTweetAdapter().notifyAdapterItemChanged(i)
                    break
                }
            }
        } ?: STLog.e("Item is empty")
    }

    protected fun stopAudio() {
        STLog.d("Stop audio")
        mediaPlayer.apply {
            if (isPlaying) {
                stop()
                lastPlayPosition = currentPosition
                STLog.d("Audio stopped")
            } else STLog.d("Audio not played")
        }
    }

}