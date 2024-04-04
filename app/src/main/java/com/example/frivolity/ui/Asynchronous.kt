package com.example.frivolity.ui

sealed class Asynchronous<PossibleResultType> {
    class Loading<ResultType> : Asynchronous<ResultType>()

    data class Error<WeNeedThisButWeDontUseIt>(
        val errorMessage: String
    ) : Asynchronous<WeNeedThisButWeDontUseIt>()

    data class Success<ResultType>(
        val resultData: ResultType
    ) : Asynchronous<ResultType>()

    class Uninitialized<ResultType> : Asynchronous<ResultType>()

}