package com.thomas.apps.nhatrosvkltn.view.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.User
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import com.thomas.apps.nhatrosvkltn.utils.saveUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val application: Application) : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()

    private var _isLogging = MutableLiveData<Boolean>()

    val isLogging: LiveData<Boolean>
        get() = _isLogging

    private var _toastMessage = MutableLiveData<String>()

    val toastMessage: LiveData<String>
        get() = _toastMessage

    private var _loginSuccess = MutableLiveData<Boolean>()

    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun login(email: String, pass: String) {
        _isLogging.value = true
        disposables.add(
            repository.login(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginResponse ->
                    _isLogging.postValue(false)
                    if (loginResponse.data == "Error") {
                        _toastMessage.postValue("Sai tên đăng nhập hoặc mật khẩu. Vui lòng kiểm tra lại")
                    } else {
                        val user: User = loginResponse.toUser()
                        saveUser(application, user)
                        _loginSuccess.postValue(true)

                    }
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLogging.postValue(false)
                    _toastMessage.postValue("Sai tên đăng nhập hoặc mật khẩu. Vui lòng kiểm tra lại")
                })
        )
    }


    fun loginWithGoogle(account: GoogleSignInAccount) {
        val user = User(
            id = 0,
            firstName = account.displayName ?: "",
            dateOfBirth = "",
            address = "",
            districtId = 0,
            cityId = 0,
            phoneNumber = "",
            avatar = account.photoUrl.toString(),
            email = account.email ?: "",
            pass = account.id.toString(),
            lastName = "",
            token = ""
        )
        _isLogging.value = true
        disposables.add(
            repository.loginWithGoogle(Register.fromUser(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginResponse ->
                    _isLogging.postValue(false)
                    if (loginResponse.data == "Error") {
                        _toastMessage.postValue("Lỗi kết nối. Vui lòng kiểm tra lại")
                    } else {
                        val userResponse: User = loginResponse.toUser()
                        saveUser(application, userResponse)
                        _loginSuccess.postValue(true)
                    }
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLogging.postValue(false)
                    _toastMessage.postValue("Lỗi kết nối. Vui lòng kiểm tra lại")
                })
        )
    }
}