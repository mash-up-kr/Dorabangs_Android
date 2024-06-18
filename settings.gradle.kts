pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Dorabangs"
include(":app")
include(":data")
include(":domain")
include(":feature")
include(":core")
include(":feature:home")
include(":core:mylibrary")
include(":core:coroutine")
include(":core:designsystem")
