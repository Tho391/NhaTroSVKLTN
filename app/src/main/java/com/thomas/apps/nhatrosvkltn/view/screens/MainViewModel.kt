package com.thomas.apps.nhatrosvkltn.view.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.ApiResponse
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listApartments
import io.reactivex.disposables.CompositeDisposable


class MainViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()

    private var _apartments = MutableLiveData<List<Apartment>>()

    val apartments: LiveData<List<Apartment>>
        get() = _apartments

    fun loadApartments() {
        //get apartments from api
        _apartments.value = listApartments

        //call api
//        disposables.add(
//            repository.getApartments()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    _apartments.value = it
//                }, {
//                    Log.e("lỗi", it?.message.toString())
//                })
//        )
    }
}