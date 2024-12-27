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

/**
 * Use case for getting users from the remote repository.
 *
 * @property repository The remote repository to fetch user data from.
 */
class GetUsersUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    /**
     * Invokes the use case to fetch users.
     *
     * @return A flow of Resource<List<User>> representing the result of the operation.
     */
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            // Emit loading state
            emit(Resource.Loading())

            // Fetch users from the remote repository
            val users = repository.getUsers().map {
                it.toUser()
            }.toList()

            // Emit success state with the fetched users
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            // Emit error state with the HTTP exception message
            emit(Resource.Error(message = e.localizedMessage ?: "Error getting data"))
        } catch (e: IOException) {
            // Emit error state with the IO exception message
            emit(Resource.Error(message = e.localizedMessage ?: "Internet connection error"))
        }
    }
}
