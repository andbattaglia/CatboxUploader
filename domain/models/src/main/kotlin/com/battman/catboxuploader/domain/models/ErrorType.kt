package com.battman.catboxuploader.domain.models

/**
 * Sealed class representing different types of errors that can occur in the application.
 */
sealed class ErrorType {

    /**
     * Sealed class representing API-related errors.
     */
    sealed class Api : ErrorType() {

        /**
         * Represents a network-related error.
         */
        data object Network : Api()

        /**
         * Represents a service being unavailable error.
         */
        data object ServiceUnavailable : Api()

        /**
         * Represents a resource not found error.
         */
        data object NotFound : Api()

        /**
         * Represents a server-side error.
         */
        data object Server : Api()
    }

    /**
     * Represents an unknown or generic error.
     */
    data class Unknown(val cause: String? = null) : ErrorType()
}
