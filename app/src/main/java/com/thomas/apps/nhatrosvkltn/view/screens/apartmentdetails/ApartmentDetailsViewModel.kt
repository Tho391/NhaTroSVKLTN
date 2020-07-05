package com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApartmentDetailsViewModel : ViewModel() {

    private var _isApartmentLoading = MutableLiveData<Boolean>()

    val isApartmentLoading: LiveData<Boolean>
        get() = _isApartmentLoading

    private var _isPosting = MutableLiveData<Boolean>()

    val isPosting: LiveData<Boolean>
        get() = _isPosting

    private var _apartment = MutableLiveData<Apartment>()

    val apartment: LiveData<Apartment>
        get() = _apartment

    private var _comments = MutableLiveData<List<Comment>>()

    val comments: LiveData<List<Comment>>
        get() = _comments

    private val disposables = CompositeDisposable()
    private val repository = Repository()

    fun getApartment(apartmentId: Int) {
        //TODO("call api get apartment from id")
//        val apartment = listApartments.find { it.id == apartmentId }
//        _apartment.value = apartment
        _isApartmentLoading.postValue(true)

        disposables.add(
            repository.getApartment(apartmentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _apartment.postValue(it.toApartment())
                    _isApartmentLoading.postValue(false)
                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isApartmentLoading.postValue(false)
                })
        )
    }

    fun getComments(apartmentId: Int) {
//        when (apartmentId) {
//            1 -> _comments.value = listComments1
//            2 -> _comments.value = listComments2
//            3 -> _comments.value = listComments3
//            4 -> _comments.value = listComments4
//            5 -> _comments.value = listComments5
//            6 -> _comments.value = listComments6
//            7 -> _comments.value = listComments7
//            8 -> _comments.value = listComments8
//            9 -> _comments.value = listComments9
//            10 -> _comments.value = listComments10
//        }


        disposables.add(
            repository.getComments(apartmentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listCommentResponse ->
                    _comments.postValue(listCommentResponse.map { it.toComment() })
                }, {
                    Log.e("lỗi", it?.message.toString())
                })
        )
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun sendComment(token: String, comment: Comment) {
        _isPosting.postValue(true)
        disposables.add(
            repository.sendComment(token, comment.toCommentResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("send comment", it.toString())
                    _isPosting.postValue(false)

                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }

    fun rating(token: String, apartmentId: Int, rating: Float) {
        _isPosting.postValue(true)
        disposables.add(
            repository.sendRating(token, apartmentId, rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("rating", it.toString())
                    _isPosting.postValue(false)

                }, {
                    Log.e("lỗi", it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }
}