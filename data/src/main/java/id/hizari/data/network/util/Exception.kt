package id.hizari.data.network.util

import java.io.IOException

class ApiException(val code: String?, message: String?): IOException(message)
class ConnectionException(message: String): IOException(message)