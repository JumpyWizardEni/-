package com.jumpywiz.tinkofftest.model

import com.google.gson.annotations.SerializedName

   
data class RequestAnswer (

   @SerializedName("result") var result : List<Result>,
   @SerializedName("totalCount") var totalCount : Int

)