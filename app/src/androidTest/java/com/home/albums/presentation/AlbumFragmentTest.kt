package com.home.albums.presentation

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.home.albums.R
import com.home.albums.app.TestApplication
import com.home.albums.framework.UseCases
import com.home.albums.framework.viewmodel.AlbumListViewModel
import com.home.albums.util.CoroutineContextProvider
import com.home.core.data.Album
import com.home.core.repository.AlbumRepository
import com.home.core.usecase.GetAllAlbums
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


@RunWith(AndroidJUnit4::class)
class AlbumFragmentTest {
    private var mockAlbumRepository: AlbumRepository = mockk()
    private var mockUseCases: UseCases = UseCases(GetAllAlbums(mockAlbumRepository))

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var albumListViewModel: AlbumListViewModel
    private lateinit var albumLiveData: MutableLiveData<List<Album>>
    private lateinit var module: Module

    @Before
    fun before() {
        module = module(createdAtStart = true, override = true) {
            viewModel { mockk<AlbumListViewModel>(relaxed = true) }
        }

        albumListViewModel =
            AlbumListViewModel(TestApplication(), mockUseCases, CoroutineContextProvider())
        albumLiveData = MutableLiveData()

        loadKoinModules(module)
        scenario = launchActivity()
    }

    @Test
    fun listLoadingAndShown() {
        val albumsList =
            listOf(Album(1, 100, "Album 1"), Album(1, 101, "Album 2"), Album(2, 102, "Album 3"))
        albumLiveData.postValue(null)
        onView(withId(R.id.progressBar))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        albumLiveData.postValue(albumsList)
        onView(withId(R.id.progressBar))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        val albumsList =
            listOf(Album(1, 100, "Album 1"), Album(1, 101, "Album 2"), Album(2, 102, "Album 3"))
        albumLiveData.postValue(albumsList)
        onView(withId(R.id.albumsListView))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("not in the list"))
                )
            )
    }

    @Test
    fun itemAtPosition_hasExpectedText() {
        val albumsList =
            listOf(Album(1, 100, "Album 1"), Album(1, 101, "Album 2"), Album(2, 102, "Album 3"))
        albumLiveData.postValue(albumsList)

        onView(withId(R.id.albumsListView)).check { view, noViewFoundException ->
            val recyclerView = view as RecyclerView
            val adapter: AlbumsListAdapter = recyclerView.adapter as AlbumsListAdapter
            adapter.updateAlbums(albumsList.sortedBy { it.title })
            assertEquals(adapter.itemCount, albumsList.size)
        }

        onView(withId(R.id.albumsListView)).check(matches(hasDescendant(withText("Album 1"))))
        onView(withId(R.id.albumsListView)).check(matches(hasDescendant(withText("Album 2"))))
    }

    @After
    fun after() {
        scenario.close()
        unloadKoinModules(module)
    }
}