package com.nagy.assessment.common.data.api.model.mappers

import com.nagy.assessment.common.data.api.model.ApiUser
import com.nagy.assessment.common.domian.model.User
import javax.inject.Inject

class ApiUserMapper @Inject constructor(
    private val apiCompanyMapper: ApiCompanyMapper,
    private val apiAddressMapper: ApiAddressMapper
) : ApiMapper<ApiUser, User> {

    override fun mapToDomain(apiEntity: ApiUser): User {
        return User(
            id = apiEntity.id ?: throw MappingException("user Id cannot be null"),
            website = apiEntity.website.orEmpty(),
            email = apiEntity.email.orEmpty(),
            name = apiEntity.name.orEmpty(),
            phone = apiEntity.phone.orEmpty(),
            userName = apiEntity.username.orEmpty(),
            address = apiAddressMapper.mapToDomain(apiEntity.address),
            company = apiCompanyMapper.mapToDomain(apiEntity.company)
        )
    }
}
