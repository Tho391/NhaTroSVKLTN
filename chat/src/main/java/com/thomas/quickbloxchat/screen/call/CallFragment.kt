package com.thomas.quickbloxchat.screen.call

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.quickblox.chat.QBChatService
import com.quickblox.videochat.webrtc.*
import com.quickblox.videochat.webrtc.callbacks.*
import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack
import com.thomas.quickbloxchat.databinding.FragmentCallBinding
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer
import java.util.*

class CallFragment(val qbrtcSession: QBRTCSession? = null) : Fragment(), QBRTCSessionEventsCallback,
    QBRTCClientVideoTracksCallbacks<QBRTCSession>,
    QBRTCClientAudioTracksCallback<QBRTCSession>, QBRTCClientSessionCallbacks,
    QBRTCSessionConnectionCallbacks {

    private var occupantIds: java.util.ArrayList<Int>? = null
    private var userInfo = mapOf<String, String>()

    private lateinit var rtcClient: QBRTCClient
    private var audioManager: AppRTCAudioManager? = null
    private var currentSession: QBRTCSession? = null
    private var isCall = false

    private lateinit var viewModel: CallViewModel
    private lateinit var binding: FragmentCallBinding

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

        val bundle = arguments

        bundle?.let {
            occupantIds = bundle.getIntegerArrayList("occupantIds")
            isCall = bundle.getBoolean("isCall", false)

            binding.imageViewArrange.setOnTouchListener { v, event ->

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

            binding.buttonDecline.setOnClickListener {
                //todo cancel call
            }

            binding.buttonAccept.setOnClickListener {
                //todo start call
            }

            configVideoCall()

            if (isCall)
                startCall()
            else {
                receiveCall(qbrtcSession)
            }
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

        audioManager = AppRTCAudioManager.create(requireContext()).apply {
            defaultAudioDevice = AppRTCAudioManager.AudioDevice.SPEAKER_PHONE
        }

        rtcClient.addSessionCallbacksListener(this)
        rtcClient.prepareToProcessCalls()

        val qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO

        currentSession = QBRTCClient.getInstance(requireContext())
            .createNewSessionWithOpponents(occupantIds, qbConferenceType)

        setUpSession(currentSession)
    }

    private fun startCall() {
        // Start call
        val userInfo = mapOf<String, String>()
        currentSession?.startCall(userInfo)
    }

    private fun setUpSession(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let { session ->
            session.addVideoTrackCallbacksListener(this)
            session.addAudioTrackCallbacksListener(this)
            session.addSessionCallbacksListener(this)
        }
    }

    private fun releaseResource() {
        binding.localVideoView.apply {
            release()
            refreshDrawableState()
        }
        binding.remoteVideoView.apply {
            release()
            refreshDrawableState()
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
        qbrtcVideoTrack?.let { fillVideoView(binding.localVideoView, it) }
        Log.i(TAG, "onLocalVideoTrackReceive" + session?.sessionID)
    }

    override fun onRemoteVideoTrackReceive(
        session: QBRTCSession?,
        qbrtcVideoTrack: QBRTCVideoTrack?,
        userID: Int?
    ) {
        qbrtcVideoTrack?.let { fillVideoView(binding.remoteVideoView, it) }
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

        //todo show incoming call
        val userInfo = HashMap<String, String>()
        userInfo["key"] = "value"

        qbrtcSession?.acceptCall(userInfo)
        setUpSession(qbrtcSession)
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
    }

    override fun onCallAcceptByUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onCallAcceptByUser" + qbrtcSession?.sessionID)

        userInfo?.let { this.userInfo = it }
        setUpSession(qbrtcSession)
    }

    override fun onReceiveHangUpFromUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onReceiveHangUpFromUser" + qbrtcSession?.sessionID)

        userInfo?.let { this.userInfo = userInfo }
        qbrtcSession?.hangUp(this.userInfo)
    }

    override fun onSessionClosed(qbrtcSession: QBRTCSession?) {
        Log.i(TAG, "onSessionClosed" + qbrtcSession?.sessionID)
        releaseResource()
    }
    //endregion

    //region Monitor peer connection(s) state
    override fun onStartConnectToUser(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onStartConnectToUser" + qbrtcSession?.sessionID)
    }

    override fun onDisconnectedTimeoutFromUser(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onDisconnectedTimeoutFromUser" + qbrtcSession?.sessionID)
    }

    override fun onConnectionFailedWithUser(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onConnectionFailedWithUser" + qbrtcSession?.sessionID)
    }

    override fun onStateChanged(
        session: QBRTCSession?,
        qbrtcSessionState: BaseSession.QBRTCSessionState?
    ) {
        Log.i(TAG, "onStateChanged " + qbrtcSessionState?.name + session?.sessionID)
    }

    override fun onConnectedToUser(session: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onConnectedToUser" + session?.sessionID)
    }

    override fun onDisconnectedFromUser(session: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onDisconnectedFromUser" + session?.sessionID)
    }

    override fun onConnectionClosedForUser(session: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onConnectionClosedForUser" + session?.sessionID)
    }
    //endregion


    companion object {
        fun newInstance(
            occupantIds: ArrayList<Int>,
            qbrtcSession: QBRTCSession? = null
        ): CallFragment {
            val args = Bundle()
            args.putIntegerArrayList("occupantIds", occupantIds)
            val fragment = CallFragment(qbrtcSession)
            fragment.arguments = args
            return fragment
        }
    }
}

private const val TAG = "CallFragment"
private const val answerTimeInterval = (10 * 1000).toLong()
private const val disconnectTimeInterval = 10 * 1000
private const val dialingTimeInterval = (10 * 1000).toLong()