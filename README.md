Paging Supporter
============
Android library to simplify implementation of pagination functionality (load more) on `RecyclerView`

## Integration

### Gradle:

This library is available on [jitpack.io](https://jitpack.io/#I3eyonder/paging-supporter).

###### Step 1. Add the JitPack repository to your build file

Add this in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

###### Step 2. Add the dependency

[![](https://jitpack.io/v/I3eyonder/paging-supporter.svg)](https://jitpack.io/#I3eyonder/paging-supporter)

```gradle
implementation 'com.github.I3eyonder:paging-supporter:x.y.z'
```

## Usage

```kotlin
val pagingSupporter = PagingSupporter.setupFor(recyclerView) {
    setTriggerThreshold(5)
    setReverseScroll(true) // reverse scroll direction to load more. Default is false (scroll from top to bottom)
    setLoadMoreCallback { currentItemCount, state ->
        state.isLoading = true // notify new items are loading...
        // load more data
        state.nextToken = Any // saving next token for next load more request if needed
        state.isLoading = false // notify loading done
        state.hasMoreData =
            false // mark that no more data available, so do not trigger loading any more.
    }
    setOnLoadingChangedListener {
        // show or hide some UI element based on loading state
    }
}.install()
```

There is also have `RecyclerView` extensions

```kotlin
recyclerView.setupPagingSupporter {
    setTriggerThreshold(5)
}.install()
```

`LoadMoreState` is also available via `PagingSupporter#loadMoreState`

`PagingSupporter` provide `uninstall()` method to remove load more functionality from `RecyclerView`
.

## Customization

`PagingSupporter.Builder#setLoadMoreScrollHandler()` allow you to supply custom implementation
of `LoadMoreScrollHandler` to support different `LayoutManager` than `LinearLayoutManager`
,`GridLayoutManager` and `StaggeredGridLayoutManager`

```kotlin
PagingSupporter.setupFor(recyclerView) {
    setLoadMoreScrollHandler(object : LoadMoreScrollHandler<FlexboxLayoutManager> {

        override fun firstVisiblePosition(layoutManager: FlexboxLayoutManager): Int {
            TODO("Not yet implemented")
        }

        override fun lastVisiblePosition(layoutManager: FlexboxLayoutManager): Int {
            TODO("Not yet implemented")
        }

        override fun isScrollingCorrectDirection(
            layoutManager: FlexboxLayoutManager,
            dx: Int,
            dy: Int,
            isReverseScroll: Boolean
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun shouldTriggerLoadMore(
            layoutManager: FlexboxLayoutManager,
            firstVisiblePosition: Int,
            lastVisiblePosition: Int,
            totalItem: Int,
            triggerThreshold: Int,
            isReverseScroll: Boolean
        ): Boolean {
            TODO("Not yet implemented")
        }
    })
}.install()
```

Refer [`LinearLoadMoreScrollHandler`](https://github.com/I3eyonder/paging-supporter/blob/master/paging-supporter/src/main/java/com/hieupt/supporter/paging/scrollhandler/LinearLoadMoreScrollHandler.kt)
for implementation
