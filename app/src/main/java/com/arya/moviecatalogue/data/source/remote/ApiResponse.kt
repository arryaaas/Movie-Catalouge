package com.arya.moviecatalogue.data.source.remote

import com.arya.moviecatalogue.data.source.remote.StatusResponse.SUCCESS
import com.arya.moviecatalogue.data.source.remote.StatusResponse.EMPTY
import com.arya.moviecatalogue.data.source.remote.StatusResponse.ERROR

class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(SUCCESS, body, null)

        fun <T> empty(body: T): ApiResponse<T> = ApiResponse(EMPTY, body, null)

        fun <T> error(msg: String): ApiResponse<T> = ApiResponse(ERROR, null, msg)
    }
}