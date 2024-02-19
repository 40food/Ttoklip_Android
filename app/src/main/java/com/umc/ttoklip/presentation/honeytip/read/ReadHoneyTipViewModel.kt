package com.umc.ttoklip.presentation.honeytip.read

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.honeytip.CommentResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.HoneyTipCommentRequest
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadHoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
) : ViewModel() {
    private val _honeyTip = MutableStateFlow<InquireHoneyTipResponse>(
        InquireHoneyTipResponse(
            0,
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            listOf(),
            listOf(),
            false,
            false,
            listOf()
        )
    )
    val honeyTip = _honeyTip.asStateFlow()

    private val _readEvent = MutableSharedFlow<ReadEvent>()
    val readEvent = _readEvent.asSharedFlow()

    private val _menuEvent = MutableSharedFlow<MenuEvent>()
    val menuEvent = _menuEvent.asSharedFlow()

    private val _comments = MutableStateFlow(listOf<CommentResponse>())
    val comments = _comments.asStateFlow()

    val replyCommentParentId = MutableStateFlow(0)
    val honeyTipCommentContent = MutableStateFlow("")
    val questionCommentContent = MutableStateFlow("")

    private val _isLike = MutableStateFlow(false)
    val isLike = _isLike.asStateFlow()

    private val _isCommentLike = MutableStateFlow(false)
    val isCommentLike = _isCommentLike.asStateFlow()

    private val _isScrap = MutableStateFlow(false)
    val isScrap = _isScrap.asStateFlow()


    sealed class ReadEvent {
        data class ReadHoneyTipEvent(val inquireHoneyTipResponse: InquireHoneyTipResponse) :
            ReadEvent()

        data class ReadQuestionEvent(val inquireQuestionResponse: InquireQuestionResponse) :
            ReadEvent()
    }

    sealed class MenuEvent {
        object DeleteHoneyTip : MenuEvent()
        object ReportHoneyTip : MenuEvent()

        object DeleteHoneyTipComment: MenuEvent()

        object ReportHoneyTipComment : MenuEvent()
        object ReportQuestion : MenuEvent()

        object ReportQuestionComment : MenuEvent()

        object DeleteQuestionComment: MenuEvent()

        object PostScrap : MenuEvent()
        object DeleteScrap : MenuEvent()

        object PostLike : MenuEvent()
        object DeleteLike : MenuEvent()
    }

    private fun eventRead(event: ReadEvent) {
        viewModelScope.launch {
            _readEvent.emit(event)
        }
    }

    private fun eventMenu(event: MenuEvent) {
        viewModelScope.launch {
            _menuEvent.emit(event)
        }
    }

    fun inquireHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.inquireHoneyTip(honeyTipId).onSuccess {
                    _honeyTip.emit(it)
                    eventRead(ReadEvent.ReadHoneyTipEvent(_honeyTip.value))
                    _isScrap.emit(it.scrapedByCurrentUser)
                    _isLike.emit(it.likedByCurrentUser)
                    _comments.emit(it.commentResponses.sortedBy { comment ->
                        comment.parentId ?: comment.commentId
                    })
                    Log.d("inquirehoneytip", it.toString())
                }.onError { Log.d("error", it.stackTraceToString()) }
                    .onFail {

                    }.onException { throw it }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    fun reportHoneyTip(honeyTipId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportHoneyTip(honeyTipId, request).onSuccess {
                Log.d("report HoneyTip", it.toString())
                eventMenu(MenuEvent.ReportHoneyTip)
            }
        }
    }

    fun deleteHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHoneyTip(honeyTipId).onSuccess {
                Log.d("delete honeyTip", it.toString())
                eventMenu(MenuEvent.DeleteHoneyTip)
            }
        }
    }

    fun postScrap() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.scrapHoneyTip(_honeyTip.value.honeyTipId).onSuccess {
                _isScrap.emit(true)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.scrapCount += 1
                })
                eventMenu(MenuEvent.PostScrap)
            }
        }
    }

    fun deleteScrap() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteScrapHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isScrap.emit(false)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.scrapCount -= 1
                })
                eventMenu(MenuEvent.DeleteScrap)
            }
        }
    }

    fun postLike() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isLike.emit(true)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.likeCount += 1
                })
                eventMenu(MenuEvent.PostLike)
            }
        }
    }

    fun deleteLike() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isLike.emit(false)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.likeCount -= 1
                })
                eventMenu(MenuEvent.DeleteLike)
            }
        }
    }

    fun postHoneyTipComment(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postCommentHoneyTip(
                postId,
                HoneyTipCommentRequest(honeyTipCommentContent.value, replyCommentParentId.value)
            ).onSuccess {
                inquireHoneyTip(postId)
            }.onError {
                it.printStackTrace()
            }
        }
    }

    fun deleteHoneyTipComment(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCommentHoneyTip(commentId).onSuccess {
                inquireHoneyTip(postId)
                eventMenu(MenuEvent.DeleteHoneyTipComment)
            }
        }
    }

    fun postReportHoneyTipComment(commentId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postReportCommentHoneyTip(commentId, request).onSuccess {
                eventMenu(MenuEvent.ReportHoneyTipComment)
            }
        }
    }

    fun postQuestionComment(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postCommentQuestion(
                postId,
                HoneyTipCommentRequest(questionCommentContent.value, replyCommentParentId.value)
            ).onSuccess {
                inquireQuestion(postId)
            }
        }
    }

    fun deleteQuestionComment(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCommentQuestion(commentId).onSuccess {
                inquireQuestion(postId)
                eventMenu(MenuEvent.DeleteQuestionComment)
            }
        }
    }

    fun postReportQuestionComment(commentId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postReportCommentQuestion(commentId, request).onSuccess {
                eventMenu(MenuEvent.ReportQuestionComment)
            }
        }
    }

    fun inquireQuestion(questionId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.inquireQuestion(questionId).onSuccess {
                    eventRead(ReadEvent.ReadQuestionEvent(it))
                    _comments.emit(it.questionCommentResponses.sortedBy { comment ->
                        comment.parentId ?: comment.commentId
                    })
                    _isCommentLike.emit(it.likedByCurrentUser)
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
            Log.d("예외", "$e")
        }
    }

    fun reportQuestion(questionId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportQuestion(questionId, request).onSuccess {
                Log.d("report Question", it.toString())
                eventMenu(MenuEvent.ReportQuestion)
            }
        }
    }

    fun likeQuestionComment(commentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.postLikeAtQuestionComment(commentId).onSuccess {
                Log.d("it", it.toString())
                _isCommentLike.emit(true)
            }
        }
    }
    fun disLikeQuestionComment(commentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.postLikeAtQuestionComment(commentId).onSuccess {
                _isCommentLike.emit(false)
            }
        }
    }
}