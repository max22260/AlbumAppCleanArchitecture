package com.nagy.assessment.common.domian.model


data class User(

    val website: String,

    val address: Address,

    val phone: String,

    val name: String,

    val company: Company,

    val id: Long,

    val email: String,

    val userName: String
) {

    fun formattedAddress():String = address.getFullAddress()

    data class Address(

        val zipcode: String,

        val geo: Geo,

        val suite: String,

        val city: String,

        val street: String
    ) {

        fun getFullAddress() :String = "$street , $city , $suite , $zipcode"


    }
    data class Geo(

        val lng: String,

        val lat: String
    )

    data class Company(

        val bs: String,

        val catchPhrase: String,

        val name: String
    )

}


