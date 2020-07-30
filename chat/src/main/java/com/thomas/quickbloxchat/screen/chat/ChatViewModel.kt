package com.thomas.quickbloxchat.screen.chat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.model.QBAttachment
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.model.QBDialogType
import com.quickblox.chat.request.QBMessageGetBuilder
import com.quickblox.content.QBContent
import com.quickblox.content.model.QBFile
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.videochat.webrtc.QBRTCAudioTrack
import com.quickblox.videochat.webrtc.QBRTCClient
import com.quickblox.videochat.webrtc.QBRTCSession
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientAudioTracksCallback
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks
import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer
import java.io.File
import java.util.*

class ChatViewModel : ViewModel() {

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var _isLoading = MutableLiveData<Boolean>()

    val message: LiveData<String>
        get() = _message
    private var _message = MutableLiveData<String>()

    val qbChatMessage: LiveData<List<QBChatMessage>>
        get() = _qbChatMessage
    private var _qbChatMessage = MutableLiveData<List<QBChatMessage>>()

    val newQBChatMessage: LiveData<QBChatMessage>
        get() = _newQBChatMessage
    private val _newQBChatMessage = MutableLiveData<QBChatMessage>()

    private var qbChatDialog = QBChatDialog()

    fun createChat(occupantIds: ArrayList<Int>) {
        _isLoading.postValue(true)

        qbChatDialog.type = QBDialogType.PRIVATE
        qbChatDialog.setOccupantsIds(occupantIds)
        // or just use DialogUtils
        //QBChatDialog dialog = DialogUtils.buildPrivateDialog(recipientId);

        QBRestChatService.createChatDialog(qbChatDialog).performAsync(object :
            QBEntityCallback<QBChatDialog> {
            override fun onSuccess(result: QBChatDialog?, params: Bundle?) {
                result?.let {
                    qbChatDialog = it
                    qbChatDialog.initForChat(QBChatService.getInstance())

                    getDialogMessage()
                }
                Log.i(TAG, result.toString())
            }

            override fun onError(responseException: QBResponseException?) {
                Log.e(TAG, responseException?.message)
                _message.postValue("Error!Try again!")
                _isLoading.postValue(false)
            }
        })
    }

    private fun getDialogMessage() {
        val messageGetBuilder = QBMessageGetBuilder()
        messageGetBuilder.limit = 100

        // If you want to retrieve messages using filtering:
        //messageGetBuilder.gte("date_sent", "508087800")
        //messageGetBuilder.lte("date_sent", "1170720000")
        //messageGetBuilder.markAsRead(false)

        QBRestChatService.getDialogMessages(qbChatDialog, messageGetBuilder)
            .performAsync(object : QBEntityCallback<ArrayList<QBChatMessage>> {
                override fun onSuccess(qbChatMessages: ArrayList<QBChatMessage>?, bundle: Bundle?) {
                    qbChatMessages?.let {
                        _isLoading.postValue(false)

                        //qbChatMessages.addAll(it)
                        _qbChatMessage.postValue(it)
                    }
                }

                override fun onError(e: QBResponseException?) {
                    Log.e(TAG, e?.message)
                    _message.postValue("Error!Try again!")
                    _isLoading.postValue(false)
                }
            })

        QBChatService.getInstance().incomingMessagesManager.addDialogMessageListener(object :
            QBChatDialogMessageListener {
            override fun processMessage(
                dialogID: String?,
                qbChatMessage: QBChatMessage?,
                senderID: Int?
            ) {
                qbChatMessage?.let {
                    _newQBChatMessage.postValue(it)
                }

            }

            override fun processError(
                dialogID: String?,
                e: QBChatException?,
                qbChatMessage: QBChatMessage?,
                senderID: Int?
            ) {
                Log.e(TAG, e?.message)
                _message.postValue(e?.message)
            }
        })
    }

    fun sendTextMessage(message: String) {
        val chatMessage = QBChatMessage()
        chatMessage.dateSent = Date().time / 1000
        chatMessage.body = message
        chatMessage.setSaveToHistory(true)

//        If you want to use this feature without callbacks :
//        try {
//            privateDialog.sendMessage(chatMessage);
//        } catch (SmackException.NotConnectedException e) {
//
//        }

        qbChatDialog.sendMessage(chatMessage, object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                _newQBChatMessage.postValue(chatMessage)
            }

            override fun onError(e: QBResponseException?) {
                _message.postValue("Send error!Try again!")
                Log.e(TAG, e?.message)
            }
        })
    }

    private fun sendImageMessage(chatMessage: QBChatMessage) {
        qbChatDialog.sendMessage(chatMessage, object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                _newQBChatMessage.postValue(chatMessage)
                _isLoading.postValue(false)
            }

            override fun onError(e: QBResponseException?) {
                _message.postValue("Send error!Try again!")
                Log.e(TAG, e?.message)
            }
        })
    }

    fun sendAttachmentMessage() {
        val filePhoto = File("image.png")
        val fileIsPublic = false
        val tags = arrayOf("tag_1", "tag_2")

        QBContent.uploadFileTask(filePhoto, fileIsPublic, tags.toString()) {
            // i - progress in percents
        }.performAsync(object : QBEntityCallback<QBFile> {
            override fun onSuccess(qbFile: QBFile?, bundle: Bundle?) {
                // create a message
                val chatMessage = QBChatMessage()
                chatMessage.setSaveToHistory(true) // Save a message to history

                // attach a photo
                val attachment = QBAttachment("photo")
                attachment.id = qbFile?.id.toString()
                chatMessage.addAttachment(attachment)

                // send the message
                // ...
            }

            override fun onError(e: QBResponseException?) {
                // error
            }
        })
    }

    fun uploadImage(filePhoto: File, tags: String) {
        _isLoading.postValue(true)
        QBContent.uploadFileTask(filePhoto, false, tags) { progress ->
            Log.i(TAG, "upload image progress: $progress")
        }.performAsync(object : QBEntityCallback<QBFile> {
            override fun onSuccess(qbFile: QBFile?, bundle: Bundle?) {
                Log.e(TAG, "hình public: ${qbFile?.publicUrl}")
                Log.e(TAG, "hình private: ${qbFile?.privateUrl}")

                // create a message
                val chatMessage = QBChatMessage()
                chatMessage.setSaveToHistory(true) // Save a message to history
                chatMessage.dateSent = Date().time / 1000

                // attach a photo
                val attachment = QBAttachment("photo")
                attachment.id = qbFile?.uid
                chatMessage.addAttachment(attachment)

                sendImageMessage(chatMessage)
            }

            override fun onError(p0: QBResponseException?) {
                Log.e(TAG, p0?.message)
                _isLoading.postValue(false)
            }

        })
    }

    private var currentSession: QBRTCSession? = null
    private lateinit var rtcClient: QBRTCClient

    private fun endCall() {
        val userInfo = HashMap<String, String>()
        userInfo["key"] = "value"
        currentSession?.hangUp(userInfo)
    }

    private fun receiveCall(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let {
            currentSession = it
            setUpSession(currentSession)
        }
    }

    private fun setUpSession(qbrtcSession: QBRTCSession?) {
        qbrtcSession?.let { session ->
            session.addVideoTrackCallbacksListener(object :
                QBRTCClientVideoTracksCallbacks<QBRTCSession> {
                override fun onLocalVideoTrackReceive(
                    p0: QBRTCSession?,
                    qbrtcVideoTrack: QBRTCVideoTrack?
                ) {
//                    qbrtcVideoTrack?.let { fillVideoView(localVideoView, it) }
                }

                override fun onRemoteVideoTrackReceive(
                    p0: QBRTCSession?,
                    p1: QBRTCVideoTrack?,
                    p2: Int?
                ) {
                    //TODO("Not yet implemented")
                }

            })
            session.addAudioTrackCallbacksListener(object :
                QBRTCClientAudioTracksCallback<QBRTCSession> {
                override fun onRemoteAudioTrackReceive(
                    p0: QBRTCSession?,
                    p1: QBRTCAudioTrack?,
                    p2: Int?
                ) {

                }

                override fun onLocalAudioTrackReceive(p0: QBRTCSession?, p1: QBRTCAudioTrack?) {

                }

            })
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
}

private const val TAG = "ChatViewModel"
