package com.example.popcornify.ui.player

import android.app.Dialog
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.popcornify.R
import com.example.popcornify.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.http.Url
import java.net.URI
import android.content.Intent.getIntent





class Player : BottomSheetDialogFragment() {

    private var player: SimpleExoPlayer? = null
    private val TAG = "PlayerActivity"
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var playUrl : String? = null
//    private var playUrl : String? = "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
    private val playbackStateListener: Player.EventListener = playbackStateListener()

    private val dialogBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentPlayerBinding.inflate(layoutInflater)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dataplayUrl = arguments?.getString("playUrl")
        playUrl = dataplayUrl

        Log.i("Anna","$playUrl")

        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val parent = dialogBinding.root.parent as View
        val bottomSheetBehavior = BottomSheetBehavior.from(parent)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialogBinding.closeDialog.setOnClickListener {
            player?.run {
                playbackPosition = this.currentPosition
                currentWindow = this.currentWindowIndex
                playWhenReady = this.playWhenReady
                removeListener(playbackStateListener)
                release()
            }
            player = null
            dialog.dismiss()
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        if (com.google.android.exoplayer2.util.Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (com.google.android.exoplayer2.util.Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (com.google.android.exoplayer2.util.Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (com.google.android.exoplayer2.util.Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector(requireContext()).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = SimpleExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector)
            .build()
            .also { exoPlayer ->
                dialogBinding.videoView.player = exoPlayer
                dialogBinding.videoView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

                val mediaItem = MediaItem.Builder()
                    .setUri(playUrl)
//                    .setMimeType(MimeTypes.BASE_TYPE_APPLICATION)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                exoPlayer.addListener(playbackStateListener)
                exoPlayer.prepare()
            }

    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            removeListener(playbackStateListener)
            release()
        }
        player = null
    }

    private fun playbackStateListener() = object : Player.EventListener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> {

                    "ExoPlayer.STATE_IDLE-"}
                ExoPlayer.STATE_BUFFERING -> {

                    "ExoPlayer.STATE_BUFFERING -"}
                ExoPlayer.STATE_READY -> {

                    "ExoPlayer.STATE_READY-"}
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED-"
                else -> {

                    "UNKNOWN_STATE-"}
            }
            Log.d(TAG, "changed state to $stateString")
        }
    }

    override fun getTheme(): Int = R.style.SheetDialog
}