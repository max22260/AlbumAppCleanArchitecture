package com.nagy.assessment.common.data.api.model.mappers

import com.nagy.assessment.common.data.api.model.ApiGeo
import com.nagy.assessment.common.domian.model.User
import javax.inject.Inject

class ApiGeoMapper @Inject constructor() : ApiMapper<ApiGeo?, User.Geo> {

    override fun mapToDomain(apiEntity: ApiGeo?): User.Geo {
        return User.Geo(
            lng = apiEntity?.lng.orEmpty(),
            lat = apiEntity?.lat.orEmpty()
        )
    }
}