package cz.zcu.students.lostandfound.common.constants

/** Firebase specific constants. */
class Firebase {
    companion object {
        //========================================================================================//
        //                                      FIRESTORE                                         //
        //========================================================================================//

        // LOST ITEMS

        /** Firestore lost items collection key. */
        const val LOST_ITEM_COLLECTION_KEY = "lostItems"

        /** Firestore lost items **title** field key. */
        const val LOST_ITEM_TITLE_KEY = "title"

        /** Firestore lost items **description** field key. */
        const val LOST_ITEM_DESCRIPTION_KEY = "description"

        /** Firestore lost items **uri** field key. */
        const val LOST_ITEM_URI_KEY = "imageUri"

        /** Firestore lost items **location** field key. */
        const val LOST_ITEM_LOCATION_KEY = "location"

        /** Firestore lost items **created_at** field key. */
        const val LOST_ITEM_CREATED_AT_KEY = "createdAt"

        /** Firestore lost items **is_found** field key. */
        const val LOST_ITEM_IS_FOUND_KEY = "isFound"

        /** Firestore lost items **is_deleted** field key. */
        const val LOST_ITEM_IS_DELETED_KEY = "isDeleted"

        /** Firestore lost items **post_owner_id** field key. */
        const val LOST_ITEM_POST_OWNER_KEY = "postOwnerId"

        /** Firestore lost items **item_owner_id** field key. */
        const val LOST_ITEM_OWNER_KEY = "itemOwnerId"


        // USERS

        /** Firestore users collection key. */
        const val USER_COLLECTION_KEY = "users"
        /** Firestore users **phone_number** field key. */
        const val USER_PHONE_NUMBER = "phoneNumber"
        /** Firestore users **created_at** field key. */
        const val USER_CREATED_AT_KEY = "createdAt"
        /** Firestore users **email** field key. */
        const val USER_EMAIL_KEY = "email"
        /** Firestore users **name** field key. */
        const val USER_NAME_KEY = "name"
        /** Firestore users **photo_uri** field key. */
        const val USER_PHOTO_URI_KEY = "photoUri"

        //========================================================================================//
        //                                      STORAGE                                           //
        //========================================================================================//

        // LOST ITEMS

        /** Storage lost items **images** key (directory name). */
        const val IMAGES_KEY = "images"

        /** Storage lost items **images** path. */
        const val ALL_IMAGES = "image/*"
    }
}