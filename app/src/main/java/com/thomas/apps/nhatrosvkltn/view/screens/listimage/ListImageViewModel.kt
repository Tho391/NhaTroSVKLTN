package com.thomas.apps.nhatrosvkltn.view.screens.listimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListImageViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()
    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var _images = MutableLiveData<List<Image>>()

    val images: LiveData<List<Image>>
        get() = _images

    fun getImages(apartmentId: Int) {
        _isLoading.value = true
        disposables.add(
            repository.getImages(apartmentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ imageResponse ->
                    _images.postValue(imageResponse.map { it.toImage() })
                    _isLoading.postValue(false)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLoading.postValue(false)
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}