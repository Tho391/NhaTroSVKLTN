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

    fun editUser(register: Register) {
        _isLoading.value = true
        disposables.add(
            repository.editUser(register)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listApartmentResponse ->


                    _isLoading.postValue(false)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLoading.postValue(false)
                })
        )
    }
}