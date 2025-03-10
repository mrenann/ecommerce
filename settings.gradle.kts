pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "QuickMart"
include(":app")
include(":core")
include(":navigation")
include(":feature-onboarding")
include(":feature-onboarding:domain")
include(":feature-onboarding:presentation")
include(":feature-auth")
//MAIN TABS
include(":feature-main")
include(":feature-main:presentation")
include(":feature-home")
include(":feature-home:presentation")
include(":feature-home:domain")
include(":feature-home:data")

include(":feature-favorites")
include(":feature-profile")
//--
include(":feature-cart")


include(":feature-favorites:presentation")
include(":feature-cart:presentation")
include(":feature-profile:presentation")
include(":feature-auth:presentation")
include(":feature-productDetails")
include(":feature-productDetails:presentation")

include(":feature-productDetails:data")
include(":feature-productDetails:domain")
include(":feature-cart:domain")
include(":feature-cart:data")
include(":feature-favorites:data")
include(":feature-favorites:domain")
