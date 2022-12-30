package cz.zcu.students.lostandfound.common

class Constants {
    companion object {
        // Splash screen
        const val SPLASHSCREEN_DURATION = 2500L // Follow 3 second rule

        //Firestore
        const val LOST_ITEM_COLLECTION_KEY = "lostItems"
        const val LOST_ITEM_TITLE_KEY = "title"
        const val LOST_ITEM_DESCRIPTION_KEY = "description"
        const val LOST_ITEM_LOCATION_KEY = "location"

        //Screens
        const val AUTH_SCREEN = "auth"
        const val FIND_ITEM_SCREEN = "findItem"
        const val UPDATE_LOST_ITEM_SCREEN = "update"
        const val INBOX_SCREEN = "inbox"
        const val MY_POSTS_SCREEN = "myPosts"
        const val FAVORITES_SCREEN = "favorites"
        const val NOTIFICATIONS_SCREEN = "notifications"
        const val SETTINGS_SCREEN = "settings"
        const val PROFILE_SCREEN = "profile"
        const val HELP_SCREEN = "help"

        //Arguments
        const val LOST_ITEM_ID = "id"

        //Actions
        const val ADD_LOST_ITEM = "Add a lost item."
        const val DELETE_LOST_ITEM = "Delete a lost item."

        //Buttons
        const val ADD = "Add"
        const val UPDATE = "Update"
        const val DISMISS = "Dismiss"

        //Placeholders
        const val LOST_ITEM_TITLE = "Type a lost item title..."
        const val LOST_ITEM_DESCRIPTION = "Type the lost item description..."
        const val NO_VALUE = ""
    }
}