plugins {
    id("com.android.library")
}

android {
    namespace = "com.example.newbinding"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
// Create configuration for copyDependencies. Uncomment line below.
configurations {
    create("copyDependencies")
}

dependencies {
    implementation("com.stripe:stripe-android:21.3.1")
    implementation("androidx.activity:activity:1.9.3")
    implementation ("androidx.fragment:fragment:1.8.5")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    // Add package dependency for binding library
    // Uncomment line below and replace {dependency.name.goes.here} with your dependency
    // implementation("{dependency.name.goes.here}")
    "copyDependencies"("com.stripe:stripe-android:21.3.1")
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "org.jetbrains.skiko" && requested.name == "skiko") {
                useTarget("org.jetbrains.skiko:skiko-android:0.7.7")
            }
        }
    }
}

// Copy dependencies for binding library. Uncomment code blocks below.
project.afterEvaluate {
    tasks.register<Copy>("copyDeps") {
        from(configurations["copyDependencies"])
        into("${projectDir}/build/outputs/deps")
    }
    tasks.named("preBuild") { finalizedBy("copyDeps") }
}