package com.thomas.apps.nhatrosvkltn.view.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.thomas.apps.nhatrosvkltn.databinding.ActivityLoginBinding
import com.thomas.apps.nhatrosvkltn.utils.TOAST
import com.thomas.apps.nhatrosvkltn.utils.launchActivity
import com.thomas.apps.nhatrosvkltn.view.screens.forgetpass.ForgetPassActivity
import com.thomas.apps.nhatrosvkltn.view.screens.register.RegisterActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = LoginViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        init()


    }

    private fun init() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        setSupportActionBar(binding.toolBar.toolBar)

        with(binding) {
            progressBar.hide()

            buttonLogin.setOnClickListener { login() }
            buttonLoginGoogle.setOnClickListener {
                loginWithGoogle()
            }
            textForgetPass.setOnClickListener {
                launchActivity<ForgetPassActivity> { }
            }
            textRegister.setOnClickListener {
                launchActivity<RegisterActivity> { }
            }

            viewModel.isLogging.observe(
                this@LoginActivity,
                Observer {
                    if (it) progressBar.show()
                    else {
                        progressBar.hide()
                        mGoogleSignInClient.signOut()
                    }
                })
        }

        viewModel.toastMessage.observe(this, Observer { TOAST(it) })
        viewModel.loginSuccess.observe(this, Observer { if (it) finish() })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun login() {
        val email = binding.editTextEmail.text.toString()
        val pass = binding.editTextPass.text.toString()

        //todo validate email
        viewModel.login(email, pass)

    }

    private fun loginWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null)
                viewModel.loginWithGoogle(account)
            else TOAST("Lỗi. Hãy đăng nhập lại")
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
            TOAST("Lỗi. Hãy đăng nhập lại")
        }
    }
}

private const val RC_SIGN_IN = 1000
private const val TAG = "LoginActivity Tag"