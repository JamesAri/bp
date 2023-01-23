package cz.zcu.students.lostandfound.common.constants

class Firebase {
    companion object {
        //========================================================================================//
        //                                      FIRESTORE                                         //
        //========================================================================================//

        // Lost items
        const val LOST_ITEM_COLLECTION_KEY = "lostItems"
        const val LOST_ITEM_TITLE_KEY = "title"
        const val LOST_ITEM_DESCRIPTION_KEY = "description"
        const val LOST_ITEM_URI_KEY = "imageUri"
        const val LOST_ITEM_LOCATION_KEY = "location"
        const val LOST_ITEM_CREATED_AT_KEY = "createdAt"
        const val LOST_ITEM_IS_FOUND_KEY = "isFound"
        const val LOST_ITEM_IS_DELETED_KEY = "isDeleted"
        const val LOST_ITEM_POST_OWNER_KEY = "postOwnerId"
        const val LOST_ITEM_OWNER_KEY = "itemOwnerId"

        // Users
        const val USER_COLLECTION_KEY = "users"
        const val USER_PHONE_NUMBER = "phoneNumber"
        const val USER_CREATED_AT_KEY = "createdAt"
        const val USER_EMAIL_KEY = "email"
        const val USER_NAME_KEY = "name"
        const val USER_PHOTO_URI_KEY = "photoUri"

        //========================================================================================//
        //                                      STORAGE                                           //
        //========================================================================================//

        // Lost items storage
        const val IMAGES_KEY = "images"

        // Lost items path
        const val ALL_IMAGES = "image/*"
    }
}