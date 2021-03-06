package com.habitbread.main.api

import android.util.Log
import com.habitbread.main.data.UserInfoRequest
import com.habitbread.main.data.UserInfoResponse
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirebaseAPI : FirebaseMessagingService() {
    lateinit var fireBaseToken : String
    private val TAG = "HabitBread"

    // 새 토큰이 생성될 때마다 onNewToken 콜백이 호출됨
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        super.onNewToken(token)
        fireBaseToken = token
        sendRegistrationToServer(token)
        // If you want to send messages to this application instance or
        // manage this apps subscrip
        // tions on the server side, send the
        // Instance ID token to your app server.
    }

    fun sendRegistrationToServer(token: String) {
        //TODO: 패치 보내기
        val HabitBreadAPI = ServerImpl.APIService
        val call: Call<UserInfoResponse> = HabitBreadAPI.patchFcmToken(UserInfoRequest.FcmTokenRequest(token))
        call.enqueue(
            object : Callback<UserInfoResponse>{
                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Log.d(TAG, "Fail patch new token")
                }

                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    Log.d(TAG, "Success patch new token")
                }
            }
        )
    }
}