package com.thomas.apps.nhatrosvkltn.view.screens.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.servermodel.Register
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()

    private var _isPosting = MutableLiveData<Boolean>().apply { postValue(false) }

    val isPosting: LiveData<Boolean>
        get() = _isPosting

    private var _message = MutableLiveData<String>()

    val message: LiveData<String>
        get() = _message

    private var _finish = MutableLiveData<Boolean>()

    val finish: LiveData<Boolean>
        get() = _finish

    fun register(register: Register, file: File?) {
        _isPosting.value = true
        _message.postValue("Đang đăng kí...")
        var body: MultipartBody.Part? = null
        var fileName: RequestBody? = null
        if (file != null) {
            val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            fileName = file.name.toRequestBody(mediaType)
        }



        disposables.add(
            repository.register(register, fileName, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listApartmentResponse ->

                    _isPosting.postValue(false)
                    _message.postValue("Đăng kí thành công.")
                    _finish.postValue(true)

                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                    _message.postValue("Đăng kí thất bại. Vui lòng thử lại.")

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}