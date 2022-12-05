package com.sangmeebee.searchmovieproject.remote.util

// search movie exception
open class IllegalSearchMovieException(message: String? = null) : IllegalStateException(message)
class EmptyQueryException : IllegalSearchMovieException()

class HttpConnectionException : IllegalStateException()
