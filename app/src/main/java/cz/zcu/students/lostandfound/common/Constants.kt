package cz.zcu.students.lostandfound.common

class Constants {
    companion object {
        // Datastore
        const val DATASTORE_FILE_NAME = "app-settings.json"

        // Splash screen
        const val SPLASHSCREEN_DURATION = 2000L // follow 3 second rule

        // Firebase storage
        const val IMAGES_KEY = "images"
        const val URL = "url"
        const val CREATED_AT = "createdAt"

        //File types
        const val ALL_IMAGES = "image/*"

        // Firestore
        const val LOST_ITEM_COLLECTION_KEY = "lostItems"
        const val LOST_ITEM_TITLE_KEY = "title"
        const val LOST_ITEM_DESCRIPTION_KEY = "description"
        const val LOST_ITEM_URL_KEY = "imageUrl"
        const val LOST_ITEM_LOCATION_KEY = "location"
        const val LOST_ITEM_CREATED_AT_KEY = "createdAt"


        // Screens
        const val AUTH_SCREEN = "auth"
        const val FIND_ITEM_SCREEN = "findItem"
        const val UPDATE_LOST_ITEM_SCREEN = "updateLostItem"
        const val ADD_LOST_ITEM_SCREEN = "addLostItem"
        const val INBOX_SCREEN = "inbox"
        const val MY_POSTS_SCREEN = "myPosts"
        const val FAVORITES_SCREEN = "favorites"
        const val NOTIFICATIONS_SCREEN = "notifications"
        const val SETTINGS_SCREEN = "settings"
        const val PROFILE_SCREEN = "profile"
        const val ABOUT_APP_SCREEN = "aboutApp"

        // Arguments
        const val LOST_ITEM_ID = "id"

        // Actions
        const val ADD_LOST_ITEM = "Add a lost item."
        const val DELETE_LOST_ITEM = "Delete a lost item."

        // Buttons
        const val ADD = "Add"
        const val UPDATE = "Update"
        const val DISMISS = "Dismiss"

        // Placeholders
        const val LOST_ITEM_TITLE = "Type a lost item title..."
        const val LOST_ITEM_DESCRIPTION = "Type the lost item description..."
        const val NO_VALUE = ""

        // Defaults
        const val ITEM_DESCRIPTION_DEFAULT = "no description"

        // Errors
        const val NO_ITEM_FOUND_ERR = "No such item found"
        const val UNKNOWN_ERR = "An unknown error occurred"
    }
}