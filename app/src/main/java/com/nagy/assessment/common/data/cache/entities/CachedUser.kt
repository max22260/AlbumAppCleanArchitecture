package com.nagy.assessment.common.data.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nagy.assessment.common.domian.model.User


@Entity(tableName = "users")
data class CachedUser(

    @PrimaryKey(autoGenerate = false)
    val userId: Long,

    val website: String,

    val phone: String,

    val name: String,

    val email: String,

    val userName: String,

    val addressZipcode: String,

    val addressGeoLng: String,

    val addressGeoLat: String,

    val addressSuite: String,

    val addressCity: String,

    val addressStreet: String,

    val companyBs: String,

    val companyCatchPhrase: String,

    val companyName: String
) {

    companion object {

        fun fromDomain(domainUser: User): CachedUser {

            val address = domainUser.address
            val geo = address.geo
            val company = domainUser.company

            return CachedUser(
                userId = domainUser.id,
                name = domainUser.name,
                userName = domainUser.userName,
                website = domainUser.website,
                email = domainUser.email,
                phone = domainUser.phone,
                addressCity = address.city,
                addressStreet = address.street,
                addressSuite = address.suite,
                addressZipcode = address.zipcode,
                addressGeoLat = geo.lat,
                addressGeoLng = geo.lng,
                companyBs = company.bs,
                companyCatchPhrase = company.catchPhrase,
                companyName = company.name
            )
        }

        fun toDomain(cachedUser: CachedUser): User {

            return User(
                id = cachedUser.userId,
                name = cachedUser.name,
                userName = cachedUser.userName,
                website = cachedUser.website,
                phone = cachedUser.phone,
                email = cachedUser.email,

                address = User.Address(
                    zipcode = cachedUser.addressZipcode,
                    User.Geo(lat = cachedUser.addressGeoLat, lng = cachedUser.addressGeoLng),
                    suite = cachedUser.addressSuite,
                    city = cachedUser.addressCity,
                    street = cachedUser.addressStreet
                ),
                company = User.Company(
                    catchPhrase = cachedUser.companyCatchPhrase,
                    name = cachedUser.companyName,
                    bs = cachedUser.companyBs
                ),
                )
        }
    }
}
