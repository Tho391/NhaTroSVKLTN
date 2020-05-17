package com.thomas.apps.nhatrosvkltn.view.ui.apartmentdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.model.User

class ApartmentDetailsViewModel : ViewModel() {
    val image1 = Image(1, "http://dummyimage.com/103x196.jpg/ff4444/ffffff")
    val image2 = Image(2, "http://dummyimage.com/204x236.bmp/ff4444/ffffff")
    val image3 = Image(3, "http://dummyimage.com/179x106.bmp/5fa2dd/ffffff")
    val image4 = Image(4, "http://dummyimage.com/238x149.jpg/dddddd/000000")
    val image5 = Image(5, "http://dummyimage.com/219x188.jpg/ff4444/ffffff")
    val image6 = Image(6, "http://dummyimage.com/185x173.jpg/5fa2dd/ffffff")
    val image7 = Image(7, "http://dummyimage.com/240x165.png/cc0000/ffffff")
    val image8 = Image(8, "http://dummyimage.com/152x104.png/5fa2dd/ffffff")
    val image9 = Image(9, "http://dummyimage.com/117x189.jpg/ff4444/ffffff")
    val image10 = Image(10, "http://dummyimage.com/169x119.bmp/dddddd/000000")
    val images1 = listOf<Image>(image1, image2, image3, image4, image5)
    val images2 = listOf<Image>(image2, image3, image4, image5, image6)
    val images3 = listOf<Image>(image3, image4, image5, image6, image7)
    val images4 = listOf<Image>(image4, image5, image6, image7, image8)
    val images5 = listOf<Image>(image5, image6, image7, image8, image9)

    val user1 = User(1, "John", "https://robohash.org/etfugabeatae.jpg?size=50x50&set=set1")
    val user2 =
        User(2, "Christos", "https://robohash.org/excepturiquieligendi.jpg?size=50x50&set=set1")
    val user3 =
        User(3, "Gerhard", "https://robohash.org/nostrumquisquamautem.bmp?size=50x50&set=set1")
    val user4 =
        User(4, "Thaine", "https://robohash.org/culpaitaquevoluptatem.bmp?size=50x50&set=set1")
    val user5 =
        User(5, "Derek", "https://robohash.org/ipsammodilaudantium.bmp?size=50x50&set=set1")

    val a1 = Apartment(1, "tittle1", "address1", "district1", 1F, 1.1, 1.1, "mo ta 1", "owner1", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = true, time = false, key = true, parking = false, air = true, heater = false, images = images1, user = user1)
    val a2 = Apartment(2, "tittle2", "address2", "district2", 2F, 100.1, 1.1, "mo ta 2", "owner2", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = false, time = true, key = true, parking = true, air = false, heater = true, images = images2, user = user2)
    val a3 = Apartment(3, "tittle3", "address3", "district3", 3F, 20.1, 1.1, "mo ta 3", "owner3", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = true, time = true, key = true, parking = false, air = true, heater = false, images = images3, user = user3)
    val a4 = Apartment(4, "tittle4", "address4", "district4", 4F, 41.1, 1.1, "mo ta 4", "owner4", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = true, time = false, key = true, parking = false, air = true, heater = true, images = images4, user = user4)
    val a5 = Apartment(5, "tittle5", "address5", "district5", 5F, 71.1, 1.1, "mo ta 5", "owner5", "0345653577", "1/1/2020", 3000000, 5000, 15000, 20, wifi = false, time = true, key = true, parking = true, air = false, heater = false, images = images5, user = user5)

    val listApartments = listOf(a1, a2, a3, a4, a5)

    val comment1 = Comment(1,"comment1","2/1/2022", user1)
    val comment2 = Comment(2,"comment2","1/4/2022", user2)
    val comment3 = Comment(3,"comment3","5/6/2022", user3)
    val comment4 = Comment(4,"comment4","11/3/2022", user4)
    val comment5 = Comment(5,"comment5","5/5/2022", user5)

    val listComments = listOf(comment1,comment2,comment3,comment4,comment5)


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

    fun getComments(apartmentId: Int){
        _comments.value = listComments
    }


}