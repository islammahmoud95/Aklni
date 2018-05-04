package com.aklni.islammahoud.aklnii.Database;

import android.provider.BaseColumns;

/**
 * Created by islam mahoud on 1/31/2018.
 */

public class Contract {
    private Contract() {}

    /* Inner class that defines the table contents */

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "cart";
        public static final String COLUMN_CAT="foodcategory";
        public static final String COLUMN_ITEMNAME = "itemname";
        public static final String COLUMN_CHEQUE = "cheque";
        public static final String COLUMN_NUM = "num";
        public static final String COLUMN_EXTRA = "extra";
        public static final String COLUMN_STATUES="statues";


        public static final String FAV_TABLE="fav";
        public static final String FAV_NAME="itemname";
        public static final String FAV_DESC="desc";
        public static final String FAV_PRICE="price";
    }
}
