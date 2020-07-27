package com.thomas.quickbloxchat.model

import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBDialogCustomData
import com.quickblox.chat.model.QBDialogType

data class ChatDialog(
    val dialogId: String? = null,
    val lastMessage: String? = null,
    val lastMessageDateSent: Long = 0,
    val lastMessageUserId: Int? = null,
    val photo: String? = null,
    val userId: Int? = null,
    val roomJid: String? = null,
    val unreadMessageCount: Int? = null,
    val name: String? = null,
    val occupantsIds: List<Int>? = null,
    val type: QBDialogType? = null,
    var customData: QBDialogCustomData? = null
) {
    fun toQBChatDialog() = QBChatDialog(dialogId)

    companion object {
        fun fromQBChatDialog(qbChatDialog: QBChatDialog) = ChatDialog(
            qbChatDialog.dialogId,
            qbChatDialog.lastMessage,
            qbChatDialog.lastMessageDateSent,
            qbChatDialog.lastMessageUserId,
            qbChatDialog.photo,
            qbChatDialog.userId,
            qbChatDialog.roomJid,
            qbChatDialog.unreadMessageCount,
            qbChatDialog.name,
            qbChatDialog.occupants,
            qbChatDialog.type,
            qbChatDialog.customData
        )
    }
}