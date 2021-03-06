package com.dicoding.capspro.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.capspro.data.remote.cluster.Cluster
import com.dicoding.capspro.data.remote.cluster.ClusterResponse
import com.dicoding.capspro.data.remote.forum.comment.CommentList
import com.dicoding.capspro.data.remote.forum.comment.CommentResponse
import com.dicoding.capspro.data.remote.forum.thread.Thread
import com.dicoding.capspro.data.remote.forum.thread.ThreadList
import com.dicoding.capspro.data.remote.forum.thread.ThreadResponse
import com.dicoding.capspro.data.remote.report.Report
import com.dicoding.capspro.data.remote.report.ReportResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteSource(private val apiService: ApiService) {

    fun addReport(
        email: String,
        name: String,
        age: Int,
        gender: String,
        location: String,
        content: String
    ) {
        val client = apiService.addReport(email, name, age, gender, location, content)
        client.enqueue(object : Callback<ReportResponse> {
            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {

            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }

        })
    }

    fun getUserReport(email: String): MutableLiveData<ArrayList<Report>> {
        val report = MutableLiveData<ArrayList<Report>>()
        val client = apiService.getUserReport(email)
        client.enqueue(object : Callback<ReportResponse> {
            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                val obj = response.body() as ReportResponse
                report.postValue(obj.data)
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }
        })
        return report
    }

    fun addThread(email: String, threadTitle: String, content: String): LiveData<Boolean> {
        val threadSentiment = MutableLiveData<Boolean>()
        val client = apiService.addThread(email, threadTitle, content)
        client.enqueue(object : Callback<ThreadResponse> {
            override fun onResponse(
                call: Call<ThreadResponse>,
                response: Response<ThreadResponse>
            ) {
                val res = response.body() as ThreadResponse
                if (res.status == "error") {
                    threadSentiment.postValue(true)
                } else {
                    threadSentiment.postValue(false)
                }
            }

            override fun onFailure(call: Call<ThreadResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }
        })
        return threadSentiment
    }

    fun getThread(): LiveData<ArrayList<ThreadList>> {
        val thread = MutableLiveData<ArrayList<ThreadList>>()
        val client = apiService.getThread()
        client.enqueue(object : Callback<ThreadResponse> {
            override fun onResponse(
                call: Call<ThreadResponse>,
                response: Response<ThreadResponse>
            ) {
                val obj = response.body() as ThreadResponse
                thread.postValue(obj.data)
            }

            override fun onFailure(call: Call<ThreadResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }
        })
        return thread
    }

    fun upvoteThread(threadId: String) {
        val client = apiService.upvoteThread(threadId)
        client.enqueue(object : Callback<Thread> {
            override fun onResponse(call: Call<Thread>, response: Response<Thread>) {

            }

            override fun onFailure(call: Call<Thread>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }

        })
    }

    fun addComment(threadId: String, email: String, comment: String): LiveData<Boolean> {
        val commentSentiment = MutableLiveData<Boolean>()
        val client = apiService.addComment(threadId, email, comment)
        client.enqueue(object : Callback<CommentResponse> {
            override fun onResponse(
                call: Call<CommentResponse>,
                response: Response<CommentResponse>
            ) {
                val res = response.body() as CommentResponse
                if (res.status == "error") {
                    commentSentiment.postValue(true)
                } else {
                    commentSentiment.postValue(false)
                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }

        })
        return commentSentiment
    }

    fun getComment(threadId: String): LiveData<ArrayList<CommentList>> {
        val comment = MutableLiveData<ArrayList<CommentList>>()
        val client = apiService.getComment(threadId)
        client.enqueue(object : Callback<CommentResponse> {
            override fun onResponse(
                call: Call<CommentResponse>,
                response: Response<CommentResponse>
            ) {
                val obj = response.body() as CommentResponse
                comment.postValue(obj.data)
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }
        })
        return comment
    }

    fun getClusterData(): LiveData<Cluster> {
        val clusterData = MutableLiveData<Cluster>()
        val client = apiService.getClusterData()
        client.enqueue(object : Callback<ClusterResponse> {
            override fun onResponse(
                call: Call<ClusterResponse>,
                response: Response<ClusterResponse>
            ) {
                val obj = response.body() as ClusterResponse
                clusterData.postValue(obj.data)
            }

            override fun onFailure(call: Call<ClusterResponse>, t: Throwable) {
                Log.e("API_FAILURE", t.toString())
            }

        })
        return clusterData
    }
}