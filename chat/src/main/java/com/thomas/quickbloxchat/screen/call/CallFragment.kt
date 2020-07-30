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

class CallFragment(private val qbrtcSession: QBRTCSession? = null) : Fragment(),
    QBRTCSessionEventsCallback,
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
                currentSession?.rejectCall(userInfo)

                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }

            binding.buttonAccept.setOnClickListener {
                //todo start call
                currentSession?.acceptCall(userInfo)

                //setUpSession(qbrtcSession)

                hideStartingCall()
                showVideo()
            }

            with(binding.callContainer) {
                buttonEndCall.setOnClickListener {
                    endCall()
                }
            }

            configVideoCall()

            showStartingCall()
            hideVideo()

            if (isCall)
                startCall()
            else {
                receiveCall(qbrtcSession)
            }
        }

    }

    private fun endCall() {
        val userInfo = HashMap<String, String>()
        userInfo["key"] = "value"

        currentSession?.hangUp(userInfo)

        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

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

    }

    private fun startCall() {
        val qbConferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO

        currentSession = QBRTCClient.getInstance(requireContext())
            .createNewSessionWithOpponents(occupantIds, qbConferenceType)

        setUpSession(currentSession)
        // Start call
        val userInfo = mapOf<String, String>()
        currentSession?.startCall(userInfo)
    }

    private fun setUpSession(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let { session ->
            session.removeVideoTrackCallbacksListener(this)
            session.removeAudioTrackCallbacksListener(this)

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

//        Toast.makeText(
//            requireContext(),
//            "new session ${qbrtcSession?.sessionID}",
//            Toast.LENGTH_SHORT
//        ).show()

//        val userInfo = HashMap<String, String>()
//        userInfo["key"] = "value"
//
        qbrtcSession?.let {
            userInfo = qbrtcSession.userInfo
            currentSession = qbrtcSession
        }
        //qbrtcSession?.acceptCall(userInfo)
        //setUpSession(qbrtcSession)
    }

    override fun onUserNoActions(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onUserNoActions" + qbrtcSession?.sessionID)
    }

    override fun onSessionStartClose(qbrtcSession: QBRTCSession?) {
        Log.i(TAG, "onSessionStartClose" + qbrtcSession?.sessionID)
    }

    override fun onUserNotAnswer(qbrtcSession: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onUserNotAnswer" + qbrtcSession?.sessionID)

//        Toast.makeText(
//            requireContext(),
//            "onUserNotAnswer ${qbrtcSession?.sessionID}",
//            Toast.LENGTH_SHORT
//        ).show()
    }

    override fun onCallRejectByUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onCallRejectByUser" + qbrtcSession?.sessionID)

//        Toast.makeText(
//            requireContext(),
//            "onCallRejectByUser ${qbrtcSession?.sessionID}",
//            Toast.LENGTH_SHORT
//        ).show()
    }

    override fun onCallAcceptByUser(
        qbrtcSession: QBRTCSession?,
        integer: Int?,
        userInfo: Map<String, String>?
    ) {
        Log.i(TAG, "onCallAcceptByUser" + qbrtcSession?.sessionID)

//        Toast.makeText(
//            activity,
//            "onCallAcceptByUser ${qbrtcSession?.sessionID}",
//            Toast.LENGTH_SHORT
//        ).show()

        userInfo?.let { this.userInfo = it }

        currentSession = qbrtcSession
        //setUpSession(currentSession)

        hideStartingCall()
        showVideo()
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

        //requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
//
//        Toast.makeText(
//            activity,
//            "onSessionClosed ${qbrtcSession?.sessionID}",
//            Toast.LENGTH_SHORT
//        ).show()
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

        setUpSession(session)
    }

    override fun onDisconnectedFromUser(session: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onDisconnectedFromUser" + session?.sessionID)
    }

    override fun onConnectionClosedForUser(session: QBRTCSession?, integer: Int?) {
        Log.i(TAG, "onConnectionClosedForUser" + session?.sessionID)
    }
    //endregion

    private fun showStartingCall() {
        with(binding) {
            imageViewAvatar.visibility = View.VISIBLE
            buttonAccept.visibility = View.VISIBLE
            buttonDecline.visibility = View.VISIBLE
            textViewAccept.visibility = View.VISIBLE
            textViewDecline.visibility = View.VISIBLE

            buttonAccept.elevation = 1F
            buttonDecline.elevation = 1F

            buttonAccept.isEnabled = true
            buttonDecline.isEnabled = true
        }
    }

    private fun hideStartingCall() {
        with(binding) {
            imageViewAvatar.visibility = View.INVISIBLE
            buttonAccept.visibility = View.INVISIBLE
            buttonDecline.visibility = View.INVISIBLE
            textViewAccept.visibility = View.INVISIBLE
            textViewDecline.visibility = View.INVISIBLE

            buttonAccept.elevation = 0F
            buttonDecline.elevation = 0F

            buttonAccept.isEnabled = false
            buttonDecline.isEnabled = false
        }
    }

    private fun showVideo() {
        with(binding) {
            imageViewArrange.visibility = View.VISIBLE
            separate.visibility = View.VISIBLE
            localVideoView.visibility = View.VISIBLE
            remoteVideoView.visibility = View.VISIBLE
            callContainer.root.visibility = View.VISIBLE

            root.isEnabled = true
            root.elevation = 2F
        }
    }

    private fun hideVideo() {
        with(binding) {
            imageViewArrange.visibility = View.INVISIBLE
            separate.visibility = View.INVISIBLE
            localVideoView.visibility = View.INVISIBLE
            remoteVideoView.visibility = View.INVISIBLE
            callContainer.root.visibility = View.INVISIBLE

            root.isEnabled = false
            root.elevation = 0F
        }
    }

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