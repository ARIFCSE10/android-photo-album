package com.example.photos.domain.use_case

import com.example.photos.common.Resource
import com.example.photos.data.remote.dto.toUser
import com.example.photos.domain.model.User
import com.example.photos.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetUsersUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val users = repository.getUsers().map {
                it.toUser()
            }.toList()
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error getting data"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Internet connection error"))
        }
    }
}