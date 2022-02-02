[![](https://jitpack.io/v/u-barnwal/ConnectionLibrary.svg)](https://jitpack.io/#u-barnwal/ConnectionLibrary)
# ConnectionLibrary
HTTP connection library to consume REST APIs in structured way with automatic support for offline data.

## Implementation
**Step 1:** Add to project level build.gradle

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

**Step 2:** Add to app level build.gradle

	dependencies {
	    implementation 'com.github.u-barnwal:ConnectionLibrary:VERSION'
	}

## How to use


See [sample app]("./app/src/main")

## Features

 - Easy to use
 - Easy to customize
 - Simple file structure: Create *models* & *structures*
 - Syntax similar to modern programming notions