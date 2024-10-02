package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.dorabangs.feature.navigation.navigateToSaveLink
import com.dorabangs.feature.navigation.navigateToSaveLinkSelectFolder
import com.dorabangs.feature.navigation.saveLinkNavigation
import com.dorabangs.feature.navigation.saveLinkSelectFolder
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.core.model.AISummaryUiModel
import com.mashup.dorabangs.core.summary.aiSummaryNavigation
import com.mashup.dorabangs.core.summary.navigateToAISummary
import com.mashup.dorabangs.core.webview.navigateToWebView
import com.mashup.dorabangs.core.webview.webViewNavigation
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import com.mashup.dorabangs.feature.navigation.homeCreateFolderNavigation
import com.mashup.dorabangs.feature.navigation.homeNavigation
import com.mashup.dorabangs.feature.navigation.homeTutorialNavigation
import com.mashup.dorabangs.feature.navigation.navigateToHome
import com.mashup.dorabangs.feature.navigation.navigateToHomeCrateFolder
import com.mashup.dorabangs.feature.navigation.navigateToHomeTutorial
import com.mashup.dorabangs.feature.navigation.navigateToStorageDetail
import com.mashup.dorabangs.feature.navigation.navigateToStorageFolderManage
import com.mashup.dorabangs.feature.navigation.navigateToUnreadStorageDetail
import com.mashup.dorabangs.feature.navigation.onBoardingNavigation
import com.mashup.dorabangs.feature.navigation.storageDetailNavigation
import com.mashup.dorabangs.feature.navigation.storageFolderManageNavigation
import com.mashup.dorabangs.feature.navigation.storageNavigation
import com.mashup.dorabangs.feature.storage.storagedetail.model.EditActionType
import com.mashup.feature.classification.navigation.classificationNavigation
import com.mashup.feature.classification.navigation.navigateToClassification

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: DoraAppState,
    hideKeyboardAction: () -> Unit,
    startDestination: String = NavigationRoute.OnBoardingScreen.route,
) {
    fun NavController.popBackStackWithClearFocus() {
        hideKeyboardAction()
        popBackStack()
    }
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        onBoardingNavigation {
            appState.navController.navigateToHome(
                navOptions {
                    popUpTo(NavigationRoute.OnBoardingScreen.route) { inclusive = true }
                },
            )
        }
        homeNavigation(
            navigateToClassification = { appState.navController.navigateToClassification() },
            navigateToSaveScreenWithLink = { copiedUrl ->
                appState.navController.navigateToSaveLinkSelectFolder(copiedUrl = copiedUrl)
            },
            navigateToSaveScreenWithoutLink = {
                appState.navController.navigateToSaveLink()
            },
            navigateToCreateFolder = { appState.navController.navigateToHomeCrateFolder() },
            navigateToHomeTutorial = { appState.navController.navigateToHomeTutorial() },
            navigateToWebView = { cardInfo ->
                val summaryUiModel = AISummaryUiModel(
                    description = cardInfo.content,
                    url = cardInfo.url,
                    keywords = cardInfo.keywordList,
                )
                appState.navController.navigateToWebView(summaryUiModel = summaryUiModel)
            },
            navigateToUnreadStorageDetail = { folder -> appState.navController.navigateToUnreadStorageDetail(folder) },
        )
        homeCreateFolderNavigation(
            navController = appState.navController,
            onClickBackIcon = {
                val backStackEntry =
                    kotlin.runCatching { appState.navController.getBackStackEntry("save/folder/{copiedUrl}") }
                        .getOrNull()
                if (backStackEntry == null) {
                    appState.navController.navigateToHome(
                        isVisibleMovingBottomSheet = true,
                        navOptions = navOptions {
                            popUpTo(appState.navController.graph.id) {
                                inclusive = true
                            }
                        },
                    )
                } else {
                    appState.navController.popBackStackWithClearFocus()
                }
            },
            navigateToHome = { appState.navController.popBackStackWithClearFocus() },
            navigateToHomeAfterSaveLink = {
                appState.navController.navigateToHome(
                    isVisibleMovingBottomSheet = false,
                )
            },
            navigateToHomeAfterMovingFolder = {
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isShowToast", true)
                appState.navController.popBackStackWithClearFocus()
            },
        )
        homeTutorialNavigation(
            navigateToHome = { appState.navController.popBackStackWithClearFocus() },
        )
        storageNavigation(
            navigateToStorageDetail = { folder ->
                appState.navController.navigateToStorageDetail(folder = folder)
            },
            navigateToFolderManage = { folderManageType, folderId ->
                appState.navController.navigateToStorageFolderManage(
                    folderManageType = folderManageType,
                    actionType = EditActionType.FolderEdit,
                    itemId = folderId,
                )
            },
        )

        storageFolderManageNavigation(
            onClickBackIcon = { folderType ->
                val isVisibleBottomSheet = folderType == FolderManageType.CREATE
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                    "isVisibleBottomSheet",
                    isVisibleBottomSheet,
                )
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                    "isChanged",
                    false,
                )
                appState.navController.popBackStackWithClearFocus()
            },
            navigateToComplete = { folderName ->
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isChanged", true)
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("folderName", folderName)
                appState.navController.popBackStackWithClearFocus()
            },
        )
        storageDetailNavigation(
            onClickBackIcon = { isChanged ->
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                    "isRemoveSuccess",
                    false,
                )
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                    "isChanged",
                    isChanged,
                )
                appState.navController.popBackStackWithClearFocus()
            },
            navigateToFolderManager = { itemId, type ->
                val folderManageType =
                    if (type == EditActionType.FolderEdit) FolderManageType.CHANGE else FolderManageType.CREATE
                appState.navController.navigateToStorageFolderManage(
                    folderManageType = folderManageType,
                    actionType = type,
                    itemId = itemId,
                )
            },
            navigateToStorage = { isRemoveSuccess ->
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                    "isChanged",
                    isRemoveSuccess,
                )
                appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                    "isRemoveSuccess",
                    isRemoveSuccess,
                )
                appState.navController.popBackStackWithClearFocus()
            },
            navigateToWebView = { cardInfo ->
                val summaryUiModel = AISummaryUiModel(
                    description = cardInfo.content,
                    url = cardInfo.url,
                    keywords = cardInfo.keywordList,
                )
                appState.navController.navigateToWebView(summaryUiModel = summaryUiModel)
            },
        )
        classificationNavigation(
            navigateToWebView = { cardInfo ->
                val summaryUiModel = AISummaryUiModel(
                    description = cardInfo.content,
                    url = cardInfo.url,
                    keywords = cardInfo.keywordList,
                )
                appState.navController.navigateToWebView(summaryUiModel = summaryUiModel)
            },
            onClickBackIcon = { appState.navController.popBackStackWithClearFocus() },
            navigateToHome = { appState.navController.popBackStackWithClearFocus() },
        )
        saveLinkNavigation(
            onClickBackIcon = { appState.navController.popBackStackWithClearFocus() },
            onClickSaveButton = { appState.navController.navigateToSaveLinkSelectFolder(copiedUrl = it) },
        )
        saveLinkSelectFolder(
            onClickBackButton = {
                appState.navController.popBackStackWithClearFocus()
            },
            finishSaveLink = {
                appState.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(appState.navController.graph.id) {
                            inclusive = true
                        }
                    },
                )
            },
            onClickAddNewFolder = { url ->
                appState.navController.navigateToHomeCrateFolder(urlLink = url)
            },
        )
        webViewNavigation(
            navigateToPopBackStack = { appState.navController.popBackStackWithClearFocus() },
            navigateToAISummary = { summary -> appState.navController.navigateToAISummary(summaryUiModel = summary) },
        )

        aiSummaryNavigation(
            navigateToPopBackStack = { appState.navController.popBackStackWithClearFocus() },
        )
    }
}
