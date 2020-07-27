package com.thomas.quickbloxchat.screen.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.QBRoster
import com.quickblox.chat.listeners.QBRosterListener
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBPresence
import com.quickblox.chat.model.QBRosterEntry
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBRequestGetBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import com.thomas.quickbloxchat.model.ChatDialog

class MainViewModel : ViewModel() {
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var _isLoading = MutableLiveData<Boolean>()

    val message: LiveData<String>
        get() = _message
    private var _message = MutableLiveData<String>()

    var isLogin: Boolean = false

    val chatDialogs: LiveData<List<ChatDialog>>
        get() = _chatDialogs
    private var _chatDialogs = MutableLiveData<List<ChatDialog>>()

    private lateinit var qbUser: QBUser
    private lateinit var chatService: QBChatService

    val listPresence = MutableLiveData<List<QBPresence>>()
    var entries = MutableLiveData<List<QBRosterEntry>>()

    fun login(userName: String, pass: String) {
        _isLoading.postValue(true)

        qbUser = QBUser(userName, pass)
        chatService = QBChatService.getInstance()
        QBUsers.signIn(qbUser).performAsync(object : QBEntityCallback<QBUser> {
            override fun onSuccess(user: QBUser?, args: Bundle?) {
                user?.let {
                    qbUser = it

                    qbUser.password = pass
                    chatService.login(qbUser, object : QBEntityCallback<Void> {
                        override fun onSuccess(p0: Void?, p1: Bundle?) {
                            isLogin = true
                            getListDialog()
                        }

                        override fun onError(p0: QBResponseException?) {
                            Log.e(TAG, p0?.message)
                            _isLoading.postValue(false)
                        }
                    })
                }
            }

            override fun onError(error: QBResponseException?) {
                Log.e(TAG, error?.message)
                _message.postValue("Error!Try login again!")
                _isLoading.postValue(false)
            }
        })
    }

    private fun getListContact() {
        val contactsRoster = QBChatService.getInstance()
            .getRoster(QBRoster.SubscriptionMode.mutual) { userId: Int ->
                // Subscription was requested by user with ID = userId

            }
        contactsRoster.addRosterListener(object : QBRosterListener {
            override fun entriesDeleted(p0: MutableCollection<Int>?) {
                p0?.let { p ->
                    val list = entries.value?.filter { qbRosterEntry ->
                        p.any { it == qbRosterEntry.userId }
                    }
                    entries.postValue(list)
                }
            }

            override fun presenceChanged(p0: QBPresence?) {
                p0?.let { p ->
                    listPresence.postValue(listPresence.value?.map { if (it.userId == p.userId) p else it })
                }
            }

            override fun entriesUpdated(p0: MutableCollection<Int>?) {
                //todo update entries
            }

            override fun entriesAdded(p0: MutableCollection<Int>?) {
                //todo add entries
            }

        })

        entries.postValue(contactsRoster.entries.toList())
        listPresence.postValue(entries.value?.map { contactsRoster.getPresence(it.userId) })
    }

    fun getListDialog() {
        val requestBuilder = QBRequestGetBuilder()
        requestBuilder.limit = 50
        //requestBuilder.setSkip(100);
        //requestBuilder.sortAsc("last_message_date_sent");

        QBRestChatService.getChatDialogs(null, requestBuilder)
            .performAsync(object : QBEntityCallback<ArrayList<QBChatDialog>> {
                override fun onSuccess(result: ArrayList<QBChatDialog>?, params: Bundle?) {
                    _chatDialogs.postValue(result?.map { ChatDialog.fromQBChatDialog(it) })
                    Log.i(TAG, "get ${result?.size} dialog")
                    _isLoading.postValue(false)
                }

                override fun onError(responseException: QBResponseException?) {
                    Log.e(TAG, responseException?.message)
                    _message.postValue("Error!Try refresh again!")
                    _isLoading.postValue(false)
                }
            })
    }
}

private const val TAG = "MainViewModel"