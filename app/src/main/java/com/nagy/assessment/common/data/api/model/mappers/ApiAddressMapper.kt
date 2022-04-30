package com.nagy.assessment.common.data.api.model.mappers

import com.nagy.assessment.common.data.api.model.ApiAddress
import com.nagy.assessment.common.domian.model.User
import javax.inject.Inject

class ApiAddressMapper @Inject constructor(
    private val apiGeoMapper: ApiGeoMapper
) : ApiMapper<ApiAddress?, User.Address> {

    override fun mapToDomain(apiEntity: ApiAddress?)
            : User.Address {

        return User.Address(
            zipcode = apiEntity?.zipcode.orEmpty(),
            geo = apiGeoMapper.mapToDomain(apiEntity?.geo),
            suite = apiEntity?.suite.orEmpty(),
            city = apiEntity?.city.orEmpty(),
            street = apiEntity?.street.orEmpty()
        )
    }
}