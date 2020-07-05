package com.thomas.apps.nhatrosvkltn.view.screens.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.FilterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapViewModel : ViewModel() {
    private val repository = Repository()
    private val disposables = CompositeDisposable()

    private var _apartments = MutableLiveData<List<Apartment>>()

    val apartments: LiveData<List<Apartment>>
        get() = _apartments

    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun filter(filter: FilterModel) {
        //call api filter
        _isLoading.value = true
        disposables.add(
            repository.filterApartments(filter)
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

    fun search(query: String) {
        _isLoading.value = true
        disposables.add(
            repository.searchApartments(query)
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

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}