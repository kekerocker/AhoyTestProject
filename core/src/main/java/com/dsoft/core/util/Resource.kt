package com.dsoft.core.util

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Failure<T>(
        val e: Throwable,
        val message: String? = null,
        val errorCode: Int? = null
    ) : Resource<T>()

    class Loading<T> : Resource<T>()

    companion object {

        inline fun <T> on(function: () -> T): Resource<T> = try {
            Success(function())
        } catch (e: Throwable) {
            Log.w("Resource Exception", e)

            when (e) {
                is HttpException -> {
                    var code: Int? = null
                    var message: String? = null

                    e.response()?.let {
                        code = it.code()
                        message = parseResponseMessage(it)
                    }

                    if (message == null) {
                        message = e.localizedMessage
                    }

                    Log.w(
                        "Resource HttpException",
                        "Message: $message Code: $code"
                    )

                    when (code) {
                        in 401..403 -> Failure(UnauthorizedException(), message, code)
                        else -> Failure(e, message, code)
                    }
                }
                is UnknownHostException -> {
                    Failure(e, e.localizedMessage)
                }
                else -> {
                    Failure(e, e.localizedMessage)
                }
            }

        }

        fun parseResponseMessage(response: Response<*>): String? {
            return response.errorBody()?.string()?.let {
                JSONObject(it)
                    .getString("message")
            }
        }
    }
}

class UnauthorizedException : Throwable()