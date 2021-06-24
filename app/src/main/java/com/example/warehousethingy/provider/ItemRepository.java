package com.example.warehousethingy.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<ArrayForListItem>> mAllItems;

    ItemRepository(Application application) {
        ItemDatabase db = ItemDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItem();
    }

    LiveData<List<ArrayForListItem>> getAllItems() {
        return mAllItems;
    }

    void insert(ArrayForListItem item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item));
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItem();
        });
    }
}
