package id.ac.itera.pesanantar.data

import kotlinx.coroutines.flow.Flow

interface Sumber<T> {
    fun pantauStatus(id: Int): Flow<T>
}