package cz.zcu.students.lostandfound.lost_items.core

class Constants {
    companion object {
        //Room - future app - caching
        //const val POST_TABLE = "post_table"

        //Firestore
        const val LOST_ITEM_COLLECTION_KEY = "lostItems"
        const val LOST_ITEM_TITLE_KEY = "title"
        const val LOST_ITEM_DESCRIPTION_KEY = "description"
        const val LOST_ITEM_LOCATION_KEY = "location"
        const val LOST_ITEM_ID_UNINITIALIZED_KEY = "uninitialized"

        //Screens
        const val AUTH_SCREEN = "Auth"
        const val POSTS_SCREEN = "Posts"
        const val UPDATE_POST_SCREEN = "Update post"

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