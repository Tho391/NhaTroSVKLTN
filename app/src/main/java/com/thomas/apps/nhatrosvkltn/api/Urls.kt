package com.thomas.apps.nhatrosvkltn.api

class Urls {

    companion object {
        //        const val BASE_URL = "https://quanlynhatro01.herokuapp.com/"
//        const val BASE_URL = "http://192.168.2.102:3000/"

        const val BASE_URL = "http://thomas-nguyen.educationhost.cloud/"

        //                const val BASE_URL = "http://ae07b2710f6e.ngrok.io/"
        const val APARTMENTS = "apartments"
        const val APARTMENT = "apartments/{id}"
        const val USER_APARTMENTS = "apartments/{id}/user"

        //        const val COMMENTS = "apartments/{id}/comments"
        const val COMMENTS = "apartments/{id}/comments"
        const val RATING = "apartments/{id}/rating"
        const val LOGIN = "login"
        const val LOGIN_GOOGLE = "login-google"
        const val REGISTER = "register"
        const val IMAGES = "apartments/{id}/image"
        const val EDIT_USER = "user/{id}"
        const val EDIT_APARTMENT = "apartments/{id}"
        const val CHANGE_PASS = "user/{id}/account"
        const val FILTER = "filter"
        const val SEARCH = "search"

        const val TEST_URL = "https://my.api.mockaroo.com/"
        const val KEY_TEST = "2fbbbb10"
        const val COMMENTS_TEST = "comments"

        const val TEST_APARTMENTS = "apartments"
        const val TEST_APARTMENT_ID = "apartments/{id}"
        const val TEST_SEARCH_APARTMENT = "apartments/{id}"

    }
}