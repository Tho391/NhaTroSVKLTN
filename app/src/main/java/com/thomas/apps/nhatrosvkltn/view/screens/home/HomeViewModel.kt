package com.thomas.apps.nhatrosvkltn.view.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.model.Apartment

class HomeViewModel : ViewModel() {

    private var _apartments = MutableLiveData<List<Apartment>>()

    val apartments: LiveData<List<Apartment>>
        get() = _apartments

}
