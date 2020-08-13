package com.thomas.apps.nhatrosvkltn.view.screens.apartmentdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.api.Repository
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.servermodel.CommentResponse
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
    val postSuccess = MutableLiveData<Boolean>()

    private var _apartment = MutableLiveData<Apartment>()

    val apartment: LiveData<Apartment>
        get() = _apartment

    private var _comments = MutableLiveData<List<Comment>>()

    val comments: LiveData<List<Comment>>
        get() = _comments

    private var _recommend = MutableLiveData<List<Apartment>>()

    val recommend: LiveData<List<Apartment>>
        get() = _recommend

    private val disposables = CompositeDisposable()
    private val repository = Repository()

    fun getApartment(apartmentId: Int) {
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

                    getRecommend(it.idNhatro, it.idQuan)
                }, {
                    Log.e(TAG, it?.message.toString())
                    _isApartmentLoading.postValue(false)
                })
        )
    }

    fun getComments(apartmentId: Int) {

        disposables.add(
            repository.getComments(apartmentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listCommentResponse ->
                    _comments.postValue(listCommentResponse.map { it.toComment() })
                }, {
                    Log.e(TAG, it?.message.toString())
                })
        )
    }

    fun onDestroy() {
        disposables.dispose()
    }

    fun sendComment(token: String, comment: CommentResponse) {
        Log.i(TAG, comment.toString())
        _isPosting.postValue(true)
        disposables.add(
            repository.sendComment(token, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i(TAG, it.toString())
                    _isPosting.postValue(false)
                    postSuccess.postValue(true)

                }, {
                    Log.e(TAG, it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }

    fun rating(token: String, apartmentId: Int, commentResponse: CommentResponse) {
        _isPosting.postValue(true)
        disposables.add(
            repository.sendRating(token, apartmentId, commentResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, it.toString())
                    _isPosting.postValue(false)

                }, {
                    Log.e(TAG, it?.message.toString())
                    _isPosting.postValue(false)
                })
        )
    }

    fun getRecommend(idNhatro: Int, idQuan: Int) {
        disposables.add(
            repository.recommend(idNhatro, idQuan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listApartment ->
                    _recommend.postValue(listApartment.map { it.toApartment() })
                    _isApartmentLoading.postValue(false)
                }, {
                    Log.e(TAG, it?.message.toString())
                    _isApartmentLoading.postValue(false)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

private const val TAG = "ADetailsViewModel"