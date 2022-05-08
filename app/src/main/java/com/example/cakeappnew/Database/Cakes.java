package com.example.cakeappnew.Database;

import android.provider.BaseColumns;

public final class Cakes {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Cakes() {}

    /* Inner class that defines the table contents */
    public static class CakeItem implements BaseColumns {
        public static final String TABLE_NAME = "cakeInfo";
        public static final String COLUMN_1= "itemCode";
        public static final String COLUMN_2= "itemName";
        public static final String COLUMN_3 = "itemType";
        public static final String COLUMN_4 = "itemPrice";
    }
}
