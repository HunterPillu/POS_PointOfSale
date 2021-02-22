package com.prinkal.pos.app.db;

import android.content.Context;

import com.prinkal.pos.app.db.converters.DataConverter;
import com.prinkal.pos.app.db.dao.AdministratorDao;
import com.prinkal.pos.app.db.dao.CashDrawerDao;
import com.prinkal.pos.app.db.dao.CategoryDao;
import com.prinkal.pos.app.db.dao.CustomerDao;
import com.prinkal.pos.app.db.dao.HoldCartDao;
import com.prinkal.pos.app.db.dao.OptionDao;
import com.prinkal.pos.app.db.dao.OptionValuesDao;
import com.prinkal.pos.app.db.dao.OrderDao;
import com.prinkal.pos.app.db.dao.ProductDao;
import com.prinkal.pos.app.db.dao.TaxDao;
import com.prinkal.pos.app.db.entity.Administrator;
import com.prinkal.pos.app.db.entity.CashDrawerModel;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.db.entity.HoldCart;
import com.prinkal.pos.app.db.entity.OptionValues;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.db.entity.OrderEntity;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.db.entity.Tax;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Administrator.class, Category.class, Product.class, Customer.class, OrderEntity.class
        , HoldCart.class, CashDrawerModel.class, Options.class, OptionValues.class, Tax.class/*, Currency.class*/}, version = 18, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mINSTANCE;

    public abstract AdministratorDao administratorDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract CustomerDao customerDao();

    public abstract OrderDao orderDao();

    public abstract HoldCartDao holdCartDao();

    public abstract CashDrawerDao cashDrawerDao();

    public abstract OptionDao optionDao();

//    public abstract CurrencyDao currencyDao();

    public abstract TaxDao taxDao();

    public abstract OptionValuesDao optionValuesDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (mINSTANCE == null) {
            mINSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db_pos.db")
                            //.addMigrations(MIGRATION_17_18)
                            .build();
        }
        return mINSTANCE;
    }

    public static void destroyDbInstance() {
        mINSTANCE = null;
    }

}
