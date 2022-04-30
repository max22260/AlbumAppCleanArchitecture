package com.nagy.assessment.common.data.api.model.mappers

interface ApiMapper< in E, out D> {

    fun mapToDomain(apiEntity: E): D
}