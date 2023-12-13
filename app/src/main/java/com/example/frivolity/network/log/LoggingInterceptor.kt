package com.example.frivolity.network.log

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.i(
            "NetworkInterceptor",
            "Sending request ${request.url} on ${chain.connection()}, headers ${request.headers}"
        )

        val response = chain.proceed(request)
        Log.i(
            "NetworkInterceptor",
            "Received response for ${response.request.url}, headers ${response.headers}"
        )

        return response
    }
}