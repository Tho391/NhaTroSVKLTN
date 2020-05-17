package com.thomas.apps.nhatrosvkltn.view.ui.addapartment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.model.Apartment

class AddApartmentViewModel : ViewModel(){
    private var _apartment = MutableLiveData<Apartment>()

    val apartment: LiveData<Apartment>
        get() = _apartment

    fun postApartment(apartment: Apartment){
        //todo gọi api request post apartment

    }
}