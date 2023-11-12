apply {
    // inherit from a base gradle file
    from("$rootDir/compose-module.gradle")
}

// add other dependencies which are not in the base module
dependencies {
    // if we do this we can use the "core" classes
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.onboardingDomain))
}