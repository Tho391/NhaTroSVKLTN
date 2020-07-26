package com.thomas.apps.nhatrosvkltn.view.screens.addapartment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
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

class AddApartmentViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()

    private var _apartment = MutableLiveData<Apartment>()

    val apartment: LiveData<Apartment>
        get() = _apartment

    private var _isPosting = MutableLiveData<Boolean>()

    val isPosting: LiveData<Boolean>
        get() = _isPosting

    fun postApartment(token: String, file: File, apartment: Apartment) {
        _isPosting.postValue(true)

        val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", file.name, requestFile)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val fileName: RequestBody = file.name.toRequestBody(mediaType)



        disposables.add(
            repository.postApartment(token, fileName, body, apartment.toApartmentResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
//                    _apartment.postValue(it.data)

                    _isPosting.postValue(false)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }
}