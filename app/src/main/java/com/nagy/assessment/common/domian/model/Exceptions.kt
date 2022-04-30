package com.nagy.assessment.common.domian.model


import java.io.IOException


class NoAlbumsException(message: String): Exception(message)

class NoPhotosException(message: String): Exception(message)

class NoUsersException(message: String): Exception(message)


class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

class NetworkException(message: String): Exception(message)