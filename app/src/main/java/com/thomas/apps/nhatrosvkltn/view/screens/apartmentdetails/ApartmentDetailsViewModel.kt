package com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listApartments
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments1
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments10
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments2
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments3
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments4
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments5
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments6
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments7
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments8
import com.thomas.apps.nhatrosvkltn.utils.Data.Companion.listComments9

class ApartmentDetailsViewModel : ViewModel() {

    private var _apartment = MutableLiveData<Apartment>()

    val apartment: LiveData<Apartment>
        get() = _apartment

    private var _comments = MutableLiveData<List<Comment>>()

    val comments: LiveData<List<Comment>>
        get() = _comments

    fun getApartment(apartmentId: Int) {
        //TODO("call api get apartment from id")
        val apartment = listApartments.find { it.id == apartmentId }
        _apartment.value = apartment
    }

    fun getComments(apartmentId: Int) {
        when (apartmentId) {
            1 -> _comments.value = listComments1
            2 -> _comments.value = listComments2
            3 -> _comments.value = listComments3
            4 -> _comments.value = listComments4
            5 -> _comments.value = listComments5
            6 -> _comments.value = listComments6
            7 -> _comments.value = listComments7
            8 -> _comments.value = listComments8
            9 -> _comments.value = listComments9
            10 -> _comments.value = listComments10
        }
    }


}