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
import com.mashup.dorabangs.feature.navigation.navigateToHome
import com.mashup.dorabangs.feature.navigation.navigateToHomeCrateFolder
import com.mashup.dorabangs.feature.navigation.navigateToStorageDetail
import com.mashup.dorabangs.feature.navigation.navigateToStorageFolderManage
import com.mashup.dorabangs.feature.navigation.onBoardingNavigation
import com.mashup.dorabangs.feature.navigation.storageDetailNavigation
import com.mashup.dorabangs.feature.navigation.storageFolderManageNavigation
import com.mashup.dorabangs.feature.navigation.storageNavigation
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
        onBoardingNavigation { appState.navController.navigateToHome() }
        homeNavigation(
            navigateToClassification = { appState.navController.navigateToClassification() },
            navigateToSaveScreenWithLink = { copiedUrl ->
                appState.navController.navigateToSaveLinkSelectFolder(copiedUrl = copiedUrl)
            },
            navigateToSaveScreenWithoutLink = {
                appState.navController.navigateToSaveLink()
            },
            navigateToCreateFolder = { appState.navController.navigateToHomeCrateFolder() },
        )
        homeCreateFolderNavigation(
            navController = appState.navController,
            onClickBackIcon = { appState.navController.popBackStack() },
            navigateToHome = { appState.navController.popBackStack() },
            navigateToHomeAfterSaveLink = {
                appState.navController.navigateToHome(
                    isVisibleMovingBottomSheet = false,
                )
            },
        )
        storageNavigation(
            navigateToStorageDetail = { folder ->
                appState.navController.navigateToStorageDetail(folder = folder)
            },
            navigateToFolderManage = { folderManageType, folderId ->
                appState.navController.navigateToStorageFolderManage(
                    folderManageType = folderManageType,
                    folderId = folderId,
                )
            },
        )

        storageFolderManageNavigation(
            onClickBackIcon = {
                appState.navController.previousBackStackEntry?.savedStateHandle?.set("isVisibleBottomSheet", true)
                appState.navController.popBackStack()
            },
            onClickSaveButton = { folderName ->
                appState.navController.popBackStack()
            },
        )
        storageDetailNavigation(
            onClickBackIcon = { appState.navController.popBackStack() },
            navigateToFolderManager = { folderId ->
                appState.navController.navigateToStorageFolderManage(folderManageType = FolderManageType.CHANGE, folderId = folderId)
            },
            navigateToCreateFolder = {
                appState.navController.navigateToStorageFolderManage(folderManageType = FolderManageType.CREATE)
            },
            navigateToHome = {
                appState.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(appState.navController.graph.id) {
                            inclusive = true
                        }
                    },
                )
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
            onClickSaveButton = {
                // TODO 클릭 때 데이터 스토어에 저장해서 다시 클립보드 안뜨게 하기?
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
