package com.nagy.assessment.search.domain.usecases

import com.nagy.assessment.common.domian.model.Photo
import com.nagy.assessment.common.domian.repository.Repository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchPhotos @Inject constructor(private val repository: Repository) {

    operator fun invoke(
        querySubject: BehaviorSubject<String>,
        albumId : Long
    ) : Flowable<List<Photo>> {

        val query = querySubject
            .debounce(500L, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length >= 2 || it.isEmpty() }
            .distinctUntilChanged()

        return query
            .toFlowable(BackpressureStrategy.LATEST)
            .filter { it.isEmpty() }
            .switchMap { parameter: String ->
                repository.searchPhotoBy(albumId,parameter)
            }
    }
}