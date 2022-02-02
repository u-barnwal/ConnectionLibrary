[![](https://jitpack.io/v/u-barnwal/ConnectionLibrary.svg)](https://jitpack.io/#u-barnwal/ConnectionLibrary)
# ConnectionLibrary
HTTP connection library to consume REST APIs in structured way with automatic support for offline data.

## Implementation
**Step 1:** Add to project level build.gradle
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2:** Add to app level build.gradle

```gradle
dependencies {
	implementation 'com.github.u-barnwal:ConnectionLibrary:VERSION'
}
```

## How to use?

### Create Helper Class
You need to create a helper class that configures the **Connection** and provides callbacks for all events.
<details>
  <summary>Expand: <kbd>ConnectionHelper.kt</kbd></summary>
  
```kotlin
import android.content.Context
import com.isolpro.library.connection.Connection

class ConnectionHelper<T>(private val ctx: Context, private val classType: Class<T>) : Connection<T>() {
	override var config: Config = Config("API_BASE_ENDPOINT")

	override fun getContext(): Context {
		return ctx;
	}

	override fun showLoader() {
		TODO("Write your function for showing loader")
	}

	override fun hideLoader() {
		TODO("Write your function for hiding loader")
	}

	override fun handleOnRequestCreated(endpoint: String, data: Any?) {
		TODO("Access the request endpoint and data")
	}

	override fun handleOnResponseReceived(data: String?) {
		TODO("This is triggered everytime your receive a response, implement your logger")
	}

	override fun handleOnNoResponseError() {
		TODO("Handle when nothing is received as response")
	}

	override fun handleOnOfflineDataUnsupported() {
		TODO("Handle when the request made, doesn't store offline data")
	}

	override fun handleOnOfflineDataUnavailable() {
		TODO("Handle when the request made, store offline data but doesn't have anything cache yet")
	}

	override fun handleOnError(e: Exception) {
		TODO("Handle all other errors")
	}

	override fun getClassType(): Class<T> {
		return classType;
	}
}
```
</details>

### Create Models
While you are free to structure your requests in any way you like, we suggest to create **model* classes* for all your data.

<details>
  <summary>Expand Example Model</summary>
  
```kotlin
class Post {
	val userId: Number = 0;
	val id: Number = 0;
	val title: String = "";
	val body: String = "";
}
```
</details>

### Create Services
We also suggest to create service class with all request functions required for the data model.

<details>
  <summary>Expand Example Service</summary>

```kotlin
object PostService {

	fun getPosts(ctx: Context): Connection<Post> {
		return ConnectionHelper(ctx, Post::class.java)
			.endpoint("/posts")
			.loader(false)
	}

	fun createPost(ctx: Context, post: Post): Connection<Post> {
		return ConnectionHelper(ctx, Post::class.java)
			.payload(post)
			.endpoint("/posts/insert")
			.loader(false)
	}

}
```
</details>

### Making the Request
Once you have created your models and services, making a request is a piece of cake

- Simple Request
	```kotlin
	PostService.getPosts(this)
		.post()
	```

- Request with Callbacks
	```kotlin
	PostService.getPosts(this)
		.success {
			TODO("Use your data from $it")
			// use $it.userId to get userId from Post 
		}
		.failure {
			TODO("Let user know that the request has failed")
		}
		.post()
		}
	```

And you're done ✅


See [sample app]("./app/src/main")

## Features

 - Easy to use
 - Easy to customize & configure
 - Automated offline mode
 - Simple file structure: Create *models* & *services*
 - Syntax similar to modern programming notions