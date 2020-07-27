package com.thomas.quickbloxchat.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.quickblox.auth.session.QBSettings
import com.quickblox.chat.QBChatService
import com.thomas.quickbloxchat.R
import com.thomas.quickbloxchat.adapter.ChatDialogAdapter
import com.thomas.quickbloxchat.databinding.ActivityMainBinding
import com.thomas.quickbloxchat.screen.call.CallFragment
import com.thomas.quickbloxchat.screen.chat.ChatActivity
import com.thomas.quickbloxchat.screen.contact.ContactActivity
import com.thomas.quickbloxchat.utils.TestData
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private val user = TestData.user2
    private val pass = TestData.pass
    private val receiverIds = if (user == TestData.user1) TestData.userId2 else TestData.userId1
    private var adapter = ChatDialogAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainViewModel::class.java
            )

        init()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isLogin)
            binding.swipeContainer.post {
                viewModel.getListDialog()
            }
    }

    private fun init() {
        with(binding) {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = this@MainActivity.adapter
            }

            swipeContainer.setOnRefreshListener {
                viewModel.getListDialog()
            }
        }

        viewModel.chatDialogs.observe(this, Observer { adapter.submitList(it) })
        viewModel.isLoading.observe(this, Observer { binding.swipeContainer.isRefreshing = it })
        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        setUpQB()
    }

    private fun setUpQB() {
        //Initialize QuickBlox SDK
        QBSettings.getInstance().init(
            this,
            resources.getString(R.string.APPLICATION_ID),
            resources.getString(R.string.AUTH_KEY),
            resources.getString(R.string.AUTH_SECRET)
        )
        QBSettings.getInstance().accountKey = resources.getString(R.string.ACCOUNT_KEY)
        QBChatService.getInstance().setUseStreamManagement(true)
        // Chat connection configuration
        val configurationBuilder = QBChatService.ConfigurationBuilder()
        configurationBuilder.socketTimeout = 300
        configurationBuilder.isUseTls = true
        configurationBuilder.isKeepAlive = true
        configurationBuilder.isAutojoinEnabled = false
        configurationBuilder.setAutoMarkDelivered(true)
        configurationBuilder.isReconnectionAllowed = true
        configurationBuilder.setAllowListenNetwork(true)
        configurationBuilder.port = 5223

        QBChatService.setConfigurationBuilder(configurationBuilder)

        viewModel.login(user, pass)

    }

    private fun enableMessageCarbons() {
        try {
            QBChatService.getInstance().enableCarbons()
        } catch (e: XMPPException) {
            e.printStackTrace()
        } catch (e: SmackException) {
            e.printStackTrace()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_search -> {
                true
            }
            R.id.action_new_chat -> {
                launchChatActivity(receiverIds)
                true
            }
            R.id.action_contact -> {
                //todo launch activity contact
                //launchContactActivity()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragment_container, CallFragment(qbrtcSession))
                fragmentTransaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun launchContactActivity() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }

    private fun launchChatActivity(occupantIds: ArrayList<Int>) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putIntegerArrayListExtra("occupantIds", occupantIds)
        }
        startActivity(intent)
    }
}

private const val TAG = "MainActivity"