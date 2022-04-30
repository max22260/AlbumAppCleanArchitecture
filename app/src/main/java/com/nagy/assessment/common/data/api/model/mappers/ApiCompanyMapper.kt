package com.nagy.assessment.common.data.api.model.mappers

import com.nagy.assessment.common.data.api.model.ApiCompany
import com.nagy.assessment.common.domian.model.User
import javax.inject.Inject

class ApiCompanyMapper @Inject constructor() : ApiMapper< ApiCompany?,User.Company> {

    override fun mapToDomain(apiEntity: ApiCompany?): User.Company {

        return User.Company(
            name = apiEntity?.name.orEmpty(),
            catchPhrase = apiEntity?.catchPhrase.orEmpty(),
            bs = apiEntity?.bs.orEmpty()

        )
    }
}