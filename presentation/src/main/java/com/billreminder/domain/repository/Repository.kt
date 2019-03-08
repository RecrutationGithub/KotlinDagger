package com.billreminder.domain.repository

import io.reactivex.Observable

interface Repository<E, C : Repository.Criteria, D : Repository.CreationData> {
    /**
     * Marker interface for query criteria.
     */
    interface Criteria

    /**
     * Marker interface for creation data.
     */
    interface CreationData

    /**
     * Fetches all instances and saves them in a local cache if necessary. The first source to be
     * checked for presence of items is local cache, then other sources like for instance local
     * database or web api.

     * @param criteria     query specification
     *
     * @return             an observable that contains fetched items
     */
    fun fetch(criteria: C): Observable<List<E>>

    fun add(data: D): Observable<E>

    fun hasCachedData(): Boolean

    fun clearCache()
}