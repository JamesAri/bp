package cz.zcu.students.lostandfound.lost_items.core

import cz.zcu.students.lostandfound.lost_items.presentation.MainActivity

class Constants {
    companion object {
        //Room
//        const val POST_TABLE = "post_table" // future app - caching

        //Firestore
        const val LOST_ITEM_COLLECTION = "lostItems"
        const val LOST_ITEM_TITLE = "title"
        const val LOST_ITEM_DESCRIPTION = "description"

        //Screens
        const val POSTS_SCREEN = "Posts"
        const val UPDATE_POST_SCREEN = "Update post"

        //Arguments
        const val POST_ID = "postId"

        //Actions
        const val ADD_POST = "Add a post."
        const val DELETE_POST = "Delete a post."

        //Buttons
        const val ADD = "Add"
        const val UPDATE = "Update"
        const val DISMISS = "Dismiss"

        //Placeholders
        const val POST_TITLE = "Type a post title..."
        const val POST_DESCRIPTION = "Type the post description..."
        const val NO_VALUE = ""
    }
}