package com.thomas.quickbloxchat.screen.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.ui.kit.chatmessage.adapter.QBMessagesAdapter
import com.quickblox.videochat.webrtc.AppRTCAudioManager
import com.quickblox.videochat.webrtc.QBRTCClient
import com.quickblox.videochat.webrtc.QBRTCConfig
import com.quickblox.videochat.webrtc.QBRTCSession
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacks
import com.thomas.quickbloxchat.R
import com.thomas.quickbloxchat.databinding.ActivityChatBinding
import com.thomas.quickbloxchat.screen.call.CallFragment
import com.thomas.quickbloxchat.utils.Utils
import org.apache.commons.io.FileUtils
import java.io.File

class ChatActivity : AppCompatActivity() {
    private val CODE_PICK_IMAGE: Int = 1001
    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ActivityChatBinding
    private lateinit var qbMessagesAdapter: QBMessagesAdapter<QBChatMessage>
    private lateinit var rtcClient: QBRTCClient

    private var occupantIds: java.util.ArrayList<Int>? = null
    private var currentSession: QBRTCSession? = null
    private var audioManager: AppRTCAudioManager? = null
    private var userInfo = mapOf<String, String>()

    private var isCalling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ChatViewModel::class.java
            )

        init()

        configVideoCall()
    }

    private fun init() {
        qbMessagesAdapter = QBMessagesAdapter(this, ArrayList())
        with(binding) {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
//                supportActionBar!!.title = resources.getString(R.string.chat)
            }

            recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@ChatActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    ).apply {
                        stackFromEnd = true
                    }
                adapter = qbMessagesAdapter
            }

            buttonSend.setOnClickListener {
                if (editTextMessage.text.toString().isNotEmpty())
                    viewModel.sendTextMessage(editTextMessage.text.toString())
            }

            buttonCamera.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_GET_CONTENT
                    type = "image/*"
                }

//                startActivityForResult(intent, CODE_PICK_IMAGE)

                //kotlin-ktx
                registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                    Toast.makeText(this@ChatActivity, "$it", Toast.LENGTH_SHORT)
                        .show()
                    val fileName = Utils.queryName(contentResolver, uri)
                    fileName?.let {
                        val tempFile =
                            File.createTempFile(it.split(".")[0], "." + it.split(".")[1])
                        val inputStream = contentResolver.openInputStream(uri)
                        FileUtils.copyToFile(inputStream, tempFile)
                        viewModel.uploadImage(tempFile, tempFile.name)
                    }
                }.launch("image/*")
            }
        }

        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.qbChatMessage.observe(this, Observer {
            qbMessagesAdapter.addList(it)

            updateRecyclerViewAndClearTextOnTextView()
        })

        viewModel.newQBChatMessage.observe(this, Observer {
            qbMessagesAdapter.add(it)

            updateRecyclerViewAndClearTextOnTextView()
        })
        viewModel.isLoading.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        occupantIds = intent.getIntegerArrayListExtra("occupantIds")
        if (!occupantIds.isNullOrEmpty()) {
            viewModel.createChat(occupantIds as java.util.ArrayList<Int>)
        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            CODE_PICK_IMAGE -> {
//                data?.data?.let { uri ->
//                    val fileName = Utils.queryName(contentResolver, uri)
//                    fileName?.let {
//                        val tempFile =
//                            File.createTempFile(it.split(".")[0], "." + it.split(".")[1])
//                        val inputStream = contentResolver.openInputStream(uri)
//                        FileUtils.copyToFile(inputStream, tempFile)
//                        viewModel.uploadImage(tempFile, tempFile.name)
//                    }
//                }
//            }
//        }
//    }

    private fun updateRecyclerViewAndClearTextOnTextView() {
        binding.editTextMessage.text.clear()
        binding.recyclerView.smoothScrollToPosition(qbMessagesAdapter.itemCount + 1)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_call -> {
                Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_video_call -> {
                Toast.makeText(this, "Video call", Toast.LENGTH_SHORT).show()
                if (supportFragmentManager.fragments.size > 1) {
                    isCalling = true
                } else {
                    isCalling = false
                    launchFragmentCall(isCall = true)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun launchFragmentCall(
        isCall: Boolean,
        qbrtcSession: QBRTCSession? = null
    ) {
        when {
            occupantIds == null -> {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
            occupantIds != null -> {
                val callFragment = if (isCall) {
                    CallFragment(null)
                    //CallFragment.newInstance(occupantIds!!, session)
                } else
                    CallFragment(qbrtcSession)
                //CallFragment.newInstance(occupantIds!!, qbrtcSession)

                callFragment.arguments = Bundle().apply {
                    putIntegerArrayList("occupantIds", occupantIds)
                    putBoolean("isCall", isCall)
                }

                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.fragment_container, callFragment)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.fragments.size > 1) {
            isCalling = false
            fragmentManager.beginTransaction().remove(fragmentManager.fragments.last()).commit()
        } else {
            QBRTCClient.getInstance(this).destroy()

            super.onBackPressed()
        }
    }

    private fun configVideoCall() {
        // Add signalling manager
        rtcClient = QBRTCClient.getInstance(this)

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

        audioManager = AppRTCAudioManager.create(this).apply {
            androidAudioManager.isSpeakerphoneOn = true
        }

        rtcClient.addSessionCallbacksListener(object : QBRTCClientSessionCallbacks {
            override fun onUserNotAnswer(qbrtcSession: QBRTCSession?, userId: Int?) {

            }

            override fun onSessionStartClose(qbrtcSession: QBRTCSession?) {

            }

            override fun onReceiveHangUpFromUser(
                qbrtcSession: QBRTCSession?,
                userId: Int?,
                userInfo: MutableMap<String, String>?
            ) {

            }

            override fun onCallAcceptByUser(
                qbrtcSession: QBRTCSession?,
                userId: Int?,
                userInfo: MutableMap<String, String>?
            ) {

            }

            override fun onReceiveNewSession(qbrtcSession: QBRTCSession?) {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.fragments.size <= 1) {
                    isCalling = false
                    Log.i(TAG, "onReceiveNewSession: ${qbrtcSession?.sessionID}")
                    launchFragmentCall(isCall = false, qbrtcSession = qbrtcSession)
                } else
                    isCalling = true
            }

            override fun onUserNoActions(qbrtcSession: QBRTCSession?, userId: Int?) {

            }

            override fun onSessionClosed(qbrtcSession: QBRTCSession?) {

            }

            override fun onCallRejectByUser(
                qbrtcSession: QBRTCSession?,
                userId: Int?,
                userInfo: MutableMap<String, String>?
            ) {

            }
        })

        rtcClient.prepareToProcessCalls()
    }
}

private const val TAG = "ChatActivity"
private const val answerTimeInterval = (10 * 1000).toLong()
private const val disconnectTimeInterval = 10 * 1000
private const val dialingTimeInterval = (10 * 1000).toLong()