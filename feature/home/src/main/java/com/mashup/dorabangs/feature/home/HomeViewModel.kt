package com.mashup.dorabangs.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.model.Sort
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationCountUseCase
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetPostsFromFolderUseCase
import com.mashup.dorabangs.domain.usecase.posts.ChangePostFolder
import com.mashup.dorabangs.domain.usecase.posts.DeletePostUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetPostUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetPostsPageUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetUnReadPostsCountUseCase
import com.mashup.dorabangs.domain.usecase.posts.PatchPostInfoUseCase
import com.mashup.dorabangs.domain.usecase.posts.SaveLinkUseCase
import com.mashup.dorabangs.domain.usecase.user.GetIdFromLinkToReadLaterUseCase
import com.mashup.dorabangs.domain.usecase.user.GetLastCopiedUrlUseCase
import com.mashup.dorabangs.domain.usecase.user.SetIdLinkToReadLaterUseCase
import com.mashup.dorabangs.domain.usecase.user.SetLastCopiedUrlUseCase
import com.mashup.dorabangs.feature.model.PagingInfo
import com.mashup.dorabangs.feature.model.toUiModel
import com.mashup.dorabangs.feature.utils.getCacheKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val getLastCopiedUrlUseCase: GetLastCopiedUrlUseCase,
    private val getFolderList: GetFolderListUseCase,
    private val setLastCopiedUrlUseCase: SetLastCopiedUrlUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val saveLinkUseCase: SaveLinkUseCase,
    private val getUnReadPostsCountUseCase: GetUnReadPostsCountUseCase,
    private val getAIClassificationCount: GetAIClassificationCountUseCase,
    private val getIdFromLinkToReadLaterUseCase: GetIdFromLinkToReadLaterUseCase,
    private val setIdFromLinkToReadLaterUseCase: SetIdLinkToReadLaterUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val changePostFolderUseCase: ChangePostFolder,
    private val patchPostInfoUseCase: PatchPostInfoUseCase,
    private val getPostsPageUseCase: GetPostsPageUseCase,
    private val getPostsFromFolderUseCase: GetPostsFromFolderUseCase,
    private val getPostUseCase: GetPostUseCase,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    val postList = mutableStateListOf<FeedUiModel.FeedCardUiModel>()

    private val pagingInfoCache = HashMap<String, PagingInfo>()

    private val postIdCache = HashMap<String, List<String>>()
    private val postDataCache = HashMap<String, FeedUiModel.FeedCardUiModel>()

    val scrollCache = HashMap<Int, Int>()
    var isScrollLoading: Boolean = false

    init {
        viewModelScope.doraLaunch {
            launch {
                savedStateHandle.getStateFlow(
                    "isVisibleMovingBottomSheet",
                    initialValue = false,
                ).collect { isVisible -> if (isVisible) getCustomFolderList() }
            }
            launch {
                savedStateHandle.getStateFlow(
                    "urlLink",
                    initialValue = "",
                ).collect { urlLink -> setStateUrlLink(urlLink) }
            }
        }

        updateFolderList()
        setAIClassificationCount()
        setPostsCount()
    }

    fun changeSelectedTabIdx(index: Int, firstVisibleItemPosition: Int) = intent {
        scrollCache[state.selectedIndex] = firstVisibleItemPosition

        reduce {
            state.copy(selectedIndex = index)
        }

        initPostList(getCacheKey(state, index))
    }

    fun hideSnackBar() = intent {
        postSideEffect(HomeSideEffect.HideSnackBar)
    }

    fun showSnackBar(clipboardText: String) = intent {
        state.copy(
            clipBoardState = state.clipBoardState.copy(
                copiedText = clipboardText,
            ),
        ).also { newState ->
            if (newState.clipBoardState.isValidUrl) {
                reduce { newState }
                postSideEffect(
                    HomeSideEffect.ShowSnackBar(
                        copiedText = newState.clipBoardState.copiedText,
                    ),
                )
            }
        }
    }

    fun setLocalCopiedUrl(url: String) = viewModelScope.doraLaunch {
        setLastCopiedUrlUseCase.invoke(url = url)
    }

    suspend fun getLocalCopiedUrl() = getLastCopiedUrlUseCase.invoke().firstOrNull()

    fun setVisibleMoreButtonBottomSheet(visible: Boolean) = intent {
        reduce {
            state.copy(isShowMoreButtonSheet = visible)
        }
    }

    fun setVisibleDialog(visible: Boolean) = intent {
        reduce {
            state.copy(isShowDialog = visible)
        }
    }

    fun setVisibleMovingFolderBottomSheet(visible: Boolean, isNavigate: Boolean = false) = intent {
        reduce {
            state.copy(isShowMovingFolderSheet = visible)
        }
        if (isNavigate) postSideEffect(HomeSideEffect.NavigateToCreateFolder)
    }

    private fun setStateUrlLink(urlLink: String) = intent {
        reduce {
            state.copy(homeCreateFolder = state.homeCreateFolder.copy(urlLink = urlLink))
        }
    }

    private fun updateFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderList().toList()
        if (getIdFromLinkToReadLaterUseCase.invoke().isBlank()) {
            folderList
                .firstOrNull { it.folderType == FolderType.DEFAULT }
                ?.let { folder ->
                    setIdFromLinkToReadLaterUseCase.invoke(
                        id = folder.id ?: error("서버가 또 잘못함 : 나중에 읽을 링크의 id가 null"),
                    )
                }
        }
        intent {
            reduce {
                state.copy(
                    tapElements = folderList.mapIndexed { index, folder ->
                        FeedUiModel.DoraChipUiModel(
                            id = folder.id.orEmpty(),
                            title = folder.name,
                            icon = setDefaultFolderIcon(index),
                        )
                    },
                )
            }
        }
    }

    private fun setDefaultFolderIcon(index: Int) = when (index) {
        0 -> R.drawable.ic_3d_all_small
        1 -> R.drawable.ic_3d_bookmark_small
        2 -> R.drawable.ic_3d_pin_small
        else -> null
    }

    private fun setTextHelperEnable(
        isEnable: Boolean,
        helperMsg: String,
        lastCheckedFolderName: String,
    ) = intent {
        reduce {
            state.copy(
                homeCreateFolder = state.homeCreateFolder.copy(
                    helperEnable = isEnable,
                    helperMessage = helperMsg,
                    lastCheckedFolderName = lastCheckedFolderName,
                ),
            )
        }
    }

    fun createFolder(folderName: String, urlLink: String) {
        viewModelScope.doraLaunch {
            val folderData = NewFolderNameList(names = listOf(folderName))
            val afterCreateFolder = createFolderUseCase(folderData)
            if (afterCreateFolder.isSuccess) {
                intent {
                    if (urlLink.isNotBlank()) {
                        postSideEffect(
                            HomeSideEffect.SaveLink(
                                folderId = afterCreateFolder.result.firstOrNull()?.id
                                    ?: error("정확한 id가 안내려 왔어요"),
                                urlLink = urlLink,
                            ),
                        )
                    } else {
                        postSideEffect(HomeSideEffect.NavigateToHome)
                    }
                }
            } else {
                setTextHelperEnable(
                    isEnable = true,
                    helperMsg = afterCreateFolder.errorMsg,
                    lastCheckedFolderName = folderName,
                )
            }
        }
    }

    fun saveLink(folderId: String, urlLink: String) = viewModelScope.doraLaunch {
        saveLinkUseCase.invoke(Link(folderId = folderId, url = urlLink))

        intent {
            postSideEffect(
                HomeSideEffect.NavigateHomeAfterSaveLink,
            )
        }
    }

    fun setFolderName(folderName: String) = intent {
        reduce {
            state.copy(
                homeCreateFolder = state.homeCreateFolder.copy(
                    folderName = folderName,
                    helperEnable = folderName == state.homeCreateFolder.lastCheckedFolderName,
                ),
            )
        }
    }

    private fun setAIClassificationCount() = viewModelScope.launch {
        val count = getAIClassificationCount()
        intent {
            reduce {
                state.copy(aiClassificationCount = count)
            }
        }
    }

    private fun setPostsCount() = viewModelScope.launch {
        val count = getUnReadPostsCountUseCase()
        intent {
            reduce {
                state.copy(unReadPostCount = count)
            }
        }
    }

    fun initPostList(
        cacheKey: String? = null,
    ) = viewModelScope.doraLaunch {
        intent {
            reduce { state.copy(isLoading = true) }

            postList.clear()
            val cacheKey = cacheKey ?: getCacheKey(state)

            if (pagingInfoCache.containsKey(cacheKey).not()) {
                pagingInfoCache[cacheKey] = PagingInfo.getDefault(cacheKey)
            }

            if (postIdCache[cacheKey].isNullOrEmpty()) {
                loadPostList(
                    state = state,
                    cacheKey = cacheKey,
                    pagingInfo = pagingInfoCache[cacheKey] ?: PagingInfo.getDefault(cacheKey),
                )
            } else {
                val cachedList = postIdCache[cacheKey]
                    ?.mapNotNull { postId -> postDataCache[postId] }
                    .orEmpty()
                postList.addAll(cachedList)
            }
        }.invokeOnCompletion {
            intent {
                reduce { state.copy(isLoading = false, isNeedToRefresh = false) }
            }
        }
    }

    fun loadMore(state: HomeState) = viewModelScope.doraLaunch {
        if (isScrollLoading) {
            return@doraLaunch
        }
        isScrollLoading = true

        val cacheKey = getCacheKey(state)
        val pagingInfo = pagingInfoCache[cacheKey] ?: return@doraLaunch

        if (pagingInfo.hasNext.not()) return@doraLaunch
        loadPostList(state, cacheKey, pagingInfo)
    }

    private suspend fun loadPostList(
        state: HomeState,
        cacheKey: String,
        pagingInfo: PagingInfo,
    ) {
        if (pagingInfo.hasNext.not()) return

        val newPosts = if (cacheKey == "all" || cacheKey == "favorite") {
            getPostsPageUseCase(
                page = pagingInfo.nextPage,
                order = pagingInfo.order,
                favorite = pagingInfo.favorite,
            )
        } else {
            getPostsFromFolderUseCase(
                folderId = cacheKey,
                page = pagingInfo.nextPage,
                order = pagingInfo.order ?: Sort.DESC.query,
                limit = 10,
                isRead = null,
            )
        }

        pagingInfoCache[cacheKey] = pagingInfo.copy(
            nextPage = pagingInfo.nextPage + 1,
            hasNext = newPosts.metaData.hasNext,
        )

        var folderList = state.folderList
        if (folderList.isEmpty()) {
            folderList = getFolderList().toList()
        }

        val newPostList = newPosts.items
            .map { post ->
                val category =
                    folderList.firstOrNull { folder -> folder.id == post.folderId }?.name.orEmpty()
                post.toUiModel(category)
            }

        newPostList.forEach { post ->
            postDataCache[post.postId] = post
        }

        postIdCache[cacheKey] =
            (postIdCache[cacheKey] ?: emptyList()) + newPostList.map { it.postId }

        postList.addAll(newPostList)
        isScrollLoading = false
    }

    fun updateFavoriteItem(postId: String, isFavorite: Boolean) = viewModelScope.doraLaunch {
        val post = postDataCache[postId] ?: return@doraLaunch
        if (post.isFavorite == isFavorite) return@doraLaunch

        updatePost(post.copy(isFavorite = isFavorite))

        val postInfo = PostInfo(isFavorite = isFavorite)

        val response = patchPostInfoUseCase(
            postId = postId,
            postInfo = postInfo,
        )

        if (response.isSuccess.not()) {
            updatePost(post.copy(isFavorite = isFavorite.not()))
        }
    }

    /**
     * 현재 폴더 리스트 가져오기
     */
    fun getCustomFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderList()
        intent {
            reduce {
                state.copy(folderList = filterDefaultList(folderList) + folderList.customFolders)
            }
            setVisibleMovingFolderBottomSheet(true)
        }
    }

    private fun filterDefaultList(list: FolderList) =
        listOf(
            list.defaultFolders.firstOrNull { it.folderType == FolderType.DEFAULT }
                ?: error("여기는 서버 잘못임 우리 탓 아님 ㄹㅇ"),
        )

    /**
     * 링크 삭제
     */
    fun deletePost(postId: String) = viewModelScope.doraLaunch {
        val response = deletePostUseCase(postId)
        setVisibleDialog(false)
        if (response.isSuccess) {
            intent {
                postList.removeIf { it.postId == postId }
                listOf(getCacheKey(state), "all", "favorite").forEach { key ->
                    postIdCache[key] = postIdCache[key]?.filterNot { it == postId } ?: emptyList()
                }
            }
        }
    }

    /**
     * 변환하고 싶은 링크의 postId와 folderId 임시 저장
     */
    fun updateSelectedPostItem(postId: String, folderId: String) = intent {
        reduce { state.copy(selectedPostId = postId, selectedFolderId = folderId) }
    }

    fun updateSelectFolderId(folderId: String) = intent {
        reduce { state.copy(changeFolderId = folderId) }
    }

    /**
     * 링크 폴더 이동
     */
    fun moveFolder(postId: String, folderId: String) = viewModelScope.doraLaunch {
        val isSuccess = changePostFolderUseCase(postId = postId, folderId = folderId).isSuccess
        setVisibleMovingFolderBottomSheet(false)
        if (isSuccess) {
            intent {
                val beforeFolderId = postList.find { it.postId == postId }?.folderId.orEmpty()
                val category = state.folderList.find { it.id == folderId }?.name.orEmpty()
                val changedPost = getPostUseCase(postId).toUiModel(category)
                postDataCache[postId] = changedPost
                postIdCache[beforeFolderId] = postIdCache[beforeFolderId]?.toMutableList()?.filterNot { it == postId } ?: emptyList()
                if (postIdCache[folderId].isNullOrEmpty().not()) {
                    postIdCache[folderId] = postIdCache[folderId]?.plus(postId) ?: emptyList()
                }
                updatePost(changedPost)
            }
        }
    }

    /**
     * 링크 수정 - 새 폴더 추가 후 폴더 이동
     */
    fun createFolderWithMoveLink(folderName: String, postId: String) = viewModelScope.doraLaunch {
        val newFolder =
            createFolderUseCase.invoke(folderList = NewFolderNameList(names = listOf(folderName)))
        if (newFolder.isSuccess) {
            val newFolderId = newFolder.result.firstOrNull()?.id
            newFolderId?.let { folderId ->
                val moveFolder =
                    changePostFolderUseCase.invoke(postId = postId, folderId = folderId)
                if (moveFolder.isSuccess) {
                    intent {
                        postSideEffect(HomeSideEffect.NavigateToCompleteMovingFolder)
                        updateEditType(isEditPostFolder = false)
                    }
                } else {
                    setTextHelperEnable(
                        isEnable = true,
                        helperMsg = newFolder.errorMsg,
                        lastCheckedFolderName = folderName,
                    )
                }
            }
        }
    }

    fun updateEditType(isEditPostFolder: Boolean) = intent {
        reduce { state.copy(isEditPostFolder = isEditPostFolder) }
    }

    /**
     * 웹뷰 이동 시 읽음 처리
     */
    fun updateReadAt(cardInfo: FeedUiModel.FeedCardUiModel) = viewModelScope.doraLaunch {
        intent {
            if (cardInfo.readAt.isNullOrEmpty()) {
                patchPostInfoUseCase.invoke(
                    postId = cardInfo.postId,
                    PostInfo(readAt = FeedUiModel.FeedCardUiModel.createCurrentTime()),
                )
            }
        }
    }

    fun updatePost(postId: String) = viewModelScope.doraLaunch {
        intent {
            val postIndex = postList.indexOfFirst { it.postId == postId }
            if (postIndex == -1) return@intent

            val post = getPostUseCase(postId)
            var folderList = state.folderList
            if (folderList.isEmpty()) {
                folderList = getFolderList().toList()
            }

            val category = folderList.firstOrNull { it.id == post.folderId }?.name.orEmpty()
            val newPost = post.toUiModel(category)

            postDataCache[postId] = newPost
            postList[postIndex] = newPost
        }
    }

    private fun updatePost(post: FeedUiModel.FeedCardUiModel) {
        val postIndex = postList.indexOfFirst { it.postId == post.postId }
        if (postIndex == -1) return

        postList[postIndex] = post
        postDataCache[post.postId] = post
    }

    fun setIsNeedToRefresh(isNeedToRefresh: Boolean) {
        intent {
            reduce { state.copy(isNeedToRefresh = isNeedToRefresh) }
        }
    }
}
