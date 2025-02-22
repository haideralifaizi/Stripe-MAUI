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

        // Add repository here, e.g.
        // maven {
        //    url = uri("{urlGoesHere}")
        // }
        maven {
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "NewBinding"
include(":newbinding")
