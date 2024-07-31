package com.mashup.dorabangs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.dorabangs.feature.navigation.navigateToSaveLink
import com.dorabangs.feature.navigation.navigateToSaveLinkSelectFolder
import com.dorabangs.feature.navigation.saveLinkNavigation
import com.dorabangs.feature.navigation.saveLinkSelectFolder
import com.mashup.core.navigation.NavigationRoute
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import com.mashup.dorabangs.feature.navigation.homeCreateFolderNavigation
import com.mashup.dorabangs.feature.navigation.homeNavigation
import com.mashup.dorabangs.feature.navigation.homeTutorialNavigation
import com.mashup.dorabangs.feature.navigation.navigateToHome
import com.mashup.dorabangs.feature.navigation.navigateToHomeCrateFolder
import com.mashup.dorabangs.feature.navigation.navigateToHomeTutorial
import com.mashup.dorabangs.feature.navigation.navigateToStorageDetail
import com.mashup.dorabangs.feature.navigation.navigateToStorageFolderManage
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
    startDestination: String = NavigationRoute.OnBoardingScreen.route,
) {
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
        )
        homeCreateFolderNavigation(
            navController = appState.navController,
            onClickBackIcon = {
                appState.navController.navigateToHome(
                    isVisibleMovingBottomSheet = true,
                )
            },
            navigateToHome = { appState.navController.popBackStack() },
            navigateToHomeAfterSaveLink = {
                appState.navController.navigateToHome(
                    isVisibleMovingBottomSheet = false,
                )
            },
        )
        homeTutorialNavigation(
            navigateToHome = { appState.navController.popBackStack() },
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
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isVisibleBottomSheet", isVisibleBottomSheet)
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isChanged", false)
                appState.navController.popBackStack()
            },
            navigateToComplete = {
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isChanged", true)
                appState.navController.popBackStack()
            },
        )
        storageDetailNavigation(
            onClickBackIcon = { isChanged ->
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isRemoveSuccess", false)
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isChanged", isChanged)
                appState.navController.popBackStack()
            },
            navigateToFolderManager = { itemId, type ->
                val folderManageType = if (type == EditActionType.FolderEdit) FolderManageType.CHANGE else FolderManageType.CREATE
                appState.navController.navigateToStorageFolderManage(folderManageType = folderManageType, actionType = type, itemId = itemId)
            },
            navigateToStorage = { isRemoveSuccess ->
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isChanged", isRemoveSuccess)
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isRemoveSuccess", isRemoveSuccess)
                appState.navController.popBackStack()
            },
        )
        classificationNavigation(
            onClickBackIcon = { appState.navController.popBackStack() },
            navigateToHome = { appState.navController.popBackStack() },
        )
        saveLinkNavigation(
            onClickBackIcon = { appState.navController.popBackStack() },
            onClickSaveButton = { appState.navController.navigateToSaveLinkSelectFolder(copiedUrl = it) },
        )
        saveLinkSelectFolder(
            onClickBackButton = {
                appState.navController.popBackStack()
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
    }
}
