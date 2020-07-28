package com.thomas.apps.nhatrosvkltn.view.screens.editapartment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditApartmentViewModel : ViewModel() {

    private val repository = Repository()
    private val disposables = CompositeDisposable()

    private var _apartment = MutableLiveData<Apartment>()

    val apartment: LiveData<Apartment>
        get() = _apartment

    private var _isPosting = MutableLiveData<Boolean>()

    val isPosting: LiveData<Boolean>
        get() = _isPosting

    fun editApartment(token: String, apartment: Apartment) {
        _isPosting.postValue(true)
        disposables.add(
            repository.editApartment(token, apartment.toApartmentResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apartmentResponse ->

                    _isPosting.postValue(false)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }
}
