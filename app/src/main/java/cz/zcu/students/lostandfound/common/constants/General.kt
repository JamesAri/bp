package cz.zcu.students.lostandfound.common.constants

import com.google.android.gms.maps.model.LatLng

class General {
    companion object {
        // App version
        const val APP_VERSION = "1.0.0"
        // Datastore
        const val DATASTORE_FILE_NAME = "app-settings.json"

        // Splash screen
        const val SPLASHSCREEN_DURATION = 2000L // follow 3 second rule

        // Screens
        const val AUTH_SCREEN = "auth"
        const val FIND_ITEM_SCREEN = "findItem"
        const val UPDATE_LOST_ITEM_SCREEN = "updateLostItem"
        const val ADD_LOST_ITEM_SCREEN = "addLostItem"
        const val LOST_ITEM_DETAIL_SCREEN = "lostItemDetail"
        const val INBOX_SCREEN = "inbox"
        const val MY_POSTS_SCREEN = "myPosts"
        const val FAVORITES_SCREEN = "favorites"
        const val NOTIFICATIONS_SCREEN = "notifications"
        const val SETTINGS_SCREEN = "settings"
        const val PROFILE_SCREEN = "profile"
        const val ABOUT_APP_SCREEN = "aboutApp"
        const val CHANGE_PHONE_NUMBER_SCREEN = "editPhoneNumber"
        const val CHANGE_PROFILE_PICTURE_SCREEN = "changeProfilePicture"
        const val MAP_SCREEN = "mapWithLostItems"
        const val MARK_LOST_ITEM_SCREEN = "markLostItem"

        val FAV_LOCATION = LatLng(49.72673748597182, 13.352430827016386)
        const val NAVIGATION_LOCATION_KEY = "location"

        // Arguments
        const val LOST_ITEM_ID = "id"

        // Errors
        const val NO_ITEM_FOUND_ERR = "No such item found"

        // Validation
        const val MAX_PHONE_NUMBER_LENGTH = 13
        const val MAX_SEARCH_TERM_LENGTH = 25

    }
}