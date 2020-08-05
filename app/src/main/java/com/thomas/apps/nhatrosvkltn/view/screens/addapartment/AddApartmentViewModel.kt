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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
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

    val postSuccess = MutableLiveData<Boolean>().apply { value = false }

    fun postApartment(token: String, userId: Int, files: List<File>, apartment: Apartment) {
        _isPosting.postValue(true)

        val requestFiles = ArrayList<MultipartBody.Part>()

        files.forEach { file ->
            val requestFile: RequestBody =
                file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            requestFiles.add(
                MultipartBody.Part.createFormData(
                    "files",
                    file.name,
                    requestFile
                )
            )
        }
        disposables.add(
            repository.postApartment(token, userId, requestFiles, apartment.toApartmentResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.data.isNullOrEmpty()) {
                        Log.e("lỗi", it.data)
                        _isPosting.postValue(false)
                        postSuccess.postValue(true)
                    } else {
                        _isPosting.postValue(false)
                    }

                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }
}