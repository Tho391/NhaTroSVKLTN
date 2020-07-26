package com.thomas.apps.nhatrosvkltn.view.screens.changepass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChangePassViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()
    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val message = MutableLiveData<String>()

    fun changePass(token: String, userId: Int, email: String, oldPass: String, newPass: String) {
        //call api
        _isLoading.value = true
        disposables.add(
            repository.changePass(token, userId, email, oldPass, newPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isLoading.postValue(false)
                    message.postValue("Đổi mật khẩu thành công")
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLoading.postValue(false)
                    message.postValue("Mật khẩu không đúng!")
                })
        )
    }
}