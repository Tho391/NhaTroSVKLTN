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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
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

    /**
     * register with server store image
     */
    fun register(register: Register, file: File?) {
        _isPosting.value = true
        _message.postValue("Đang đăng kí...")
        var upFile: MultipartBody.Part? = null
        if (file != null) {

            val requestFile: RequestBody =
                file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            upFile = MultipartBody.Part.createFormData("fileavatar", file.name, requestFile)

        }

        disposables.add(
            repository.register(register, upFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->

                    Log.i(TAG, it.data + "")

                    if (it.data == "Da Dang Ky") {
                        _message.postValue("Email đã đăng kí")
                    } else {
                        _message.postValue("Đăng kí thành công.")
                        _finish.postValue(true)
                    }
                    _isPosting.postValue(false)

                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                    _message.postValue("Đăng kí thất bại. Vui lòng thử lại.")

                })
        )
    }

    /**
     * register with image store in imgbb
     */
    fun register2(register: Register, file: File?, key: String) {
        _isPosting.value = true
        _message.postValue("Đang đăng kí...")
        var upfile: MultipartBody.Part? = null
        if (file != null) {
            val requestFile: RequestBody =
                file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            upfile = MultipartBody.Part.createFormData("image", file.name, requestFile)

            upfile.let { part ->
                disposables.add(
                    repository.uploadImage(key, part)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                if (it.success) {
                                    val url = it.data.url
                                    register.photoUrl = url
                                    repository.register2(register)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe({ register ->
                                            Log.i(TAG, register.data + "")

                                            if (register.data == "Da Dang Ky") {
                                                _message.postValue("Email đã đăng kí")
                                            } else {
                                                _message.postValue("Đăng kí thành công.")
                                                _finish.postValue(true)
                                            }
                                            _isPosting.postValue(false)
                                        },
                                            {
                                                _isPosting.postValue(false)
                                                _message.postValue("Đăng kí thất bại. Vui lòng thử lại.")
                                            })
                                }
                            }, {
                                _isPosting.postValue(false)
                                _message.postValue("Đăng kí thất bại. Vui lòng thử lại.")
                            }
                        )
                )
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

private const val TAG = "RegisterViewModel"