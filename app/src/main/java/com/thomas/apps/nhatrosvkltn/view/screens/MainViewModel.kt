package com.thomas.apps.nhatrosvkltn.view.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()
    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var _apartments = MutableLiveData<List<Apartment>>()

    val apartments: LiveData<List<Apartment>>
        get() = _apartments


    fun loadApartments() {
        //get apartments from api
        //_apartments.value = listApartments

        //call api
        _isLoading.value = true
        disposables.add(
            repository.getApartments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listApartmentResponse ->
                    _apartments.postValue(listApartmentResponse.map { it.toApartment() })

                    _isLoading.postValue(false)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isLoading.postValue(false)
                })
        )
    }

    fun onDestroy() {
        disposables.dispose()
    }


}