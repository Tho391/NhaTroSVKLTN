package com.thomas.apps.nhatrosvkltn.view.screens.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()

    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var editSuccess = MutableLiveData<Boolean>()

    fun editUser(token: String, userId: Int, register: Register) {
        _isLoading.value = true
        disposables.add(
            repository.editUser(token, userId, register)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->

                    Log.i(TAG, it.data + "")

                    _isLoading.postValue(false)
                    editSuccess.postValue(true)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLoading.postValue(false)
                })
        )
    }
}

private const val TAG = "UserViewModel"