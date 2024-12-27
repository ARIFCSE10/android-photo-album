package com.example.photos.common

/**
 * Represents the result of an API call or any operation that can have different outcomes.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * Represents a successful operation with data.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents an error operation with a message and optional data.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state with optional data.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
