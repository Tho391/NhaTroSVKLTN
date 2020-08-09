package com.thomas.quickbloxchat.screen.call

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.quickblox.chat.QBChatService
import com.quickblox.videochat.webrtc.*
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientAudioTracksCallback
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacks
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks
import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack
import com.thomas.quickbloxchat.R
import com.thomas.quickbloxchat.databinding.FragmentCallBinding
import org.webrtc.CameraVideoCapturer
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer

class CallFragment(private val qbrtcSession: QBRTCSession? = null) : Fragment(),
    QBRTCClientVideoTracksCallbacks<QBRTCSession>,
    QBRTCClientAudioTracksCallback<QBRTCSession>, QBRTCClientSessionCallbacks {

    private var occupantIds: java.util.ArrayList<Int>? = null

    private lateinit var rtcClient: QBRTCClient
    private var audioManager: AppRTCAudioManager? = null
    private var currentSession: QBRTCSession? = null
    private var isCall = false

    private lateinit var viewModel: CallViewModel
    private lateinit var binding: FragmentCallBinding

    private var isAudioEnable = true
    private var isCameraEnable = true

    val userInfo = mutableMapOf<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(CallViewModel::class.java)

        init()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        currentSession = qbrtcSession

        val bundle = arguments

        bundle?.let {
            occupantIds = bundle.getIntegerArrayList("occupantIds")
            isCall = bundle.getBoolean("isCall", false)

            binding.containerCall.root.isEnabled = false

            with(binding.containerCall) {
                imageViewArrange.setOnTouchListener { v, event ->
                    when (event.action) {
                        ACTION_MOVE -> {
                            val layoutParams: ConstraintLayout.LayoutParams =
                                v.layoutParams as ConstraintLayout.LayoutParams
                            val y = event.rawY
                            if (y in binding.root.height / 3F..2 * binding.root.measuredHeight / 3F) {
                                layoutParams.verticalBias = y / binding.root.measuredHeight
                                v.layoutParams = layoutParams
                            }
                            return@setOnTouchListener true
                        }
                        else -> return@setOnTouchListener true
                    }
                }
            }

            with(binding.containerCall.containerCallFunction) {
                cardViewCamOff.setOnClickListener {
                    isCameraEnable = !isCameraEnable
                    offCam(isCameraEnable)
                }

                cardViewEndCall.setOnClickListener {
                    endCall()
                }

                cardViewMute.setOnClickListener {
                    isAudioEnable = !isAudioEnable
                    muteAudio(isAudioEnable)
                }

//                cardViewShareScreen.setOnClickListener {
//                    shareScreen()
//                }

                cardViewSwapCam.setOnClickListener {
                    swapCam()
                }

                viewModel.timer.observe(viewLifecycleOwner, Observer {
                    textViewTime.text = it
                })
            }

            with(binding.containerStartingCall) {
                cardViewDecline.setOnClickListener {
                    currentSession?.rejectCall(userInfo)
//
//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .remove(this@CallFragment).commit()
//                    parentFragmentManager.beginTransaction().remove(this@CallFragment).commit()
                    activity?.supportFragmentManager?.beginTransaction()?.remove(this@CallFragment)
                        ?.commit()
                }

                cardViewAccept.setOnClickListener {

                    currentSession?.acceptCall(userInfo)

                    setUpSession(currentSession)

                    viewModel.startTimer()

                    hideCallInfoUI()

                    showCallUI()
                }

            }

            configVideoCall()

            if (isCall)
                startCall()
            else {
                receiveCall(qbrtcSession)
            }
        }

    }

    private fun shareScreen() {
    }

    private fun offCam(offCam: Boolean) {
        val qbMediaStreamManager = currentSession?.mediaStreamManager

        qbMediaStreamManager?.let {
            it.isVideoEnabled = offCam

            Log.i(TAG, "offCam = $offCam | manager = ${it.isVideoEnabled}")

            binding.containerCall.containerCallFunction.cardViewCamOff.backgroundTintList =
                when {
                    Build.VERSION.SDK_INT >= 23 -> {
                        if (!offCam)
                            resources.getColorStateList(R.color.button_disable_background, null)
                        else
                            resources.getColorStateList(R.color.button_light_background, null)
                    }
                    else -> {
                        if (!offCam)
                            resources.getColorStateList(R.color.button_disable_background)
                        else
                            resources.getColorStateList(R.color.button_light_background)
                    }
                }
        }
    }

    private fun swapCam() {
        val videoCapture =
            currentSession?.mediaStreamManager?.videoCapturer as QBRTCCameraVideoCapturer
        videoCapture.switchCamera(object : CameraVideoCapturer.CameraSwitchHandler {
            override fun onCameraSwitchDone(p0: Boolean) {
            }

            override fun onCameraSwitchError(p0: String?) {
            }
        })
    }

    private fun muteAudio(isAudioEnable: Boolean) {
        val qbMediaStreamManager = currentSession?.mediaStreamManager

        qbMediaStreamManager?.let {
            it.isAudioEnabled = isAudioEnable

            Log.i(TAG, "isAudioEnable = $isAudioEnable | manager = ${it.isAudioEnabled}")

            binding.containerCall.containerCallFunction.cardViewMute.backgroundTintList =
                when {
                    Build.VERSION.SDK_INT >= 23 -> {
                        if (!isAudioEnable)
                            resources.getColorStateList(R.color.button_disable_background, null)
                        else
                            resources.getColorStateList(R.color.button_light_background, null)
                    }
                    else -> {
                        if (!isAudioEnable)
                            resources.getColorStateList(R.color.button_disable_background)
                        else
                            resources.getColorStateList(R.color.button_light_background)
                    }
                }
        }
    }

    private fun showCallUI() {
        with(binding) {
            containerCall.root.visibility = View.VISIBLE
            containerCall.root.elevation = 1F
            containerCall.root.isEnabled = true
        }
    }

    private fun hideCallUI() {
        with(binding) {
            containerCall.root.visibility = View.INVISIBLE
        }
    }

    private fun hideCallInfoUI() {
        with(binding) {
            containerStartingCall.root.visibility = View.INVISIBLE
            containerStartingCall.root.isEnabled = false
            containerStartingCall.root.elevation = 0F
        }
    }

    private fun showCallInfoUI() {
        with(binding) {
            containerStartingCall.root.visibility = View.VISIBLE
        }
    }

    private fun receiveCall(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let {
            currentSession = it
            setUpSession(currentSession)
        }
    }

    private fun configVideoCall() {
        // Add signalling manager
        rtcClient = QBRTCClient.getInstance(requireContext())

        QBChatService.getInstance().videoChatWebRTCSignalingManager.addSignalingManagerListener { qbSignaling, createdLocally ->
            if (!createdLocally) {
                rtcClient.addSignaling(qbSignaling)
            }
        }

        // Configure
        QBRTCConfig.setDebugEnabled(true)
        QBRTCConfig.setAnswerTimeInterval(answerTimeInterval)
        QBRTCConfig.setDisconnectTime(disconnectTimeInterval)
        QBRTCConfig.setDialingTimeInterval(dialingTimeInterval)

        val a = AppRTCAudioManager.create(requireContext()).also {
            it.androidAudioManager.isSpeakerphoneOn = true
        }

        Log.i(TAG, "Audio devices: " + a.audioDevices.toString())

        rtcClient.prepareToProcessCalls()
    }

    private fun startCall() {
        hideCallInfoUI()
        showCallUI()

        val qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO

        // Init session
        currentSession = QBRTCClient.getInstance(requireContext())
            .createNewSessionWithOpponents(occupantIds, qbConferenceType)

        setUpSession(currentSession)

        // Start call
        val user = QBChatService.getInstance().user

        //val userInfo = mutableMapOf<String, String>()

        //userInfo["name"] = user.login

        currentSession?.startCall(userInfo)
    }

    private fun endCall() {
        currentSession?.hangUp(userInfo)

        viewModel.stopTimer()

        releaseResource()

        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun setUpSession(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let { session ->
            session.addVideoTrackCallbacksListener(this)
            session.addAudioTrackCallbacksListener(this)

            session.addEventsCallback(this)
        }
    }

    private fun removeListener(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let { session ->
            session.removeVideoTrackCallbacksListener(this)
            session.removeAudioTrackCallbacksListener(this)
            session.removeEventsCallback(this)
        }
    }

    private fun releaseResource() {
        with(binding.containerCall) {
            localVideoView.apply {
                release()
                refreshDrawableState()
            }
            remoteVideoView.apply {
                release()
                refreshDrawableState()
            }
        }

    }

    private fun fillVideoView(videoView: QBRTCSurfaceView?, videoTrack: QBRTCVideoTrack) {
        // To remove renderer if Video Track already has another one
        videoTrack.cleanUp()

        if (videoView != null) {
            videoTrack.addRenderer(videoView)
            updateVideoView(videoView)
        }
    }

    private fun updateVideoView(videoView: SurfaceViewRenderer) {
        val scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FILL
        videoView.setScalingType(scalingType)
        videoView.setMirror(false)
        videoView.requestLayout()
    }

    //region manage audio & video
    override fun onLocalVideoTrackReceive(
        session: QBRTCSession?,
        qbrtcVideoTrack: QBRTCVideoTrack?
    ) {
        qbrtcVideoTrack?.let { fillVideoView(binding.containerCall.localVideoView, it) }
        Log.i(TAG, "onLocalVideoTrackReceive" + session?.sessionID)
    }

    override fun onRemoteVideoTrackReceive(
        session: QBRTCSession?,
        qbrtcVideoTrack: QBRTCVideoTrack?,
        userID: Int?
    ) {
        qbrtcVideoTrack?.let { fillVideoView(binding.containerCall.remoteVideoView, it) }
        Log.i(TAG, "onRemoteVideoTrackReceive" + session?.sessionID)

    }

    override fun onRemoteAudioTrackReceive(
        session: QBRTCSession?,
        qbrtcAudioTrack: QBRTCAudioTrack?,
        userID: Int?
    ) {
        Log.i(TAG, "onRemoteAudioTrackReceive" + session?.sessionID)

    }

    override fun onLocalAudioTrackReceive(
        session: QBRTCSession?,
        qbrtcAudioTrack: QBRTCAudioTrack?
    ) {
        Log.i(TAG, "onLocalAudioTrackReceive" + session?.sessionID)
    }
    //endregion

    //region manage session
    override fun onReceiveNewSession(qbrtcSession: QBRTCSession?) {
        Log.i(TAG, "onReceiveNewSession" + qbrtcSession?.sessionID)
        currentSession = qbrtcSession

//        qbrtcSession?.let {
//            binding.containerStartingCall.textViewName.text = it.userInfo["name"]
//        }
    }

    override fun onUserNoActions(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onUserNoActions" + qbrtcSession?.sessionID)
    }

    override fun onSessionStartClose(qbrtcSession: QBRTCSession?) {
        Log.i(TAG, "onSessionStartClose" + qbrtcSession?.sessionID)
    }

    override fun onUserNotAnswer(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onUserNotAnswer" + qbrtcSession?.sessionID)
    }

    override fun onCallRejectByUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onCallRejectByUser" + qbrtcSession?.sessionID)

        removeListener(qbrtcSession)
        releaseResource()
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    override fun onCallAcceptByUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onCallAcceptByUser" + qbrtcSession?.sessionID)

        viewModel.startTimer()

        hideCallInfoUI()
        showCallUI()

        setUpSession(qbrtcSession)
    }

    override fun onReceiveHangUpFromUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onReceiveHangUpFromUser" + qbrtcSession?.sessionID)
        qbrtcSession?.hangUp(userInfo)
    }

    override fun onSessionClosed(qbrtcSession: QBRTCSession?) {
        Log.i(TAG, "onSessionClosed" + qbrtcSession?.sessionID)

        removeListener(qbrtcSession)
        releaseResource()

        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
    //endregion
}

private const val TAG = "CallFragment"
private const val answerTimeInterval = (10 * 1000).toLong()
private const val disconnectTimeInterval = 10 * 1000
private const val dialingTimeInterval = (10 * 1000).toLong()