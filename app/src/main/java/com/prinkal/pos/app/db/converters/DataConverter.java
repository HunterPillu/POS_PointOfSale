package com.prinkal.pos.app.db.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prinkal.pos.app.db.entity.OptionValues;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.model.CartModel;
import com.prinkal.pos.app.model.CashDrawerItems;
import com.prinkal.pos.app.model.CashModel;
import com.prinkal.pos.app.model.ProductCategoryModel;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class DataConverter implements Serializable {

    @TypeConverter
    public String fromProductCategoriesList(List<ProductCategoryModel> productCategories) {
        if (productCategories == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductCategoryModel>>() {
        }.getType();
        String json = gson.toJson(productCategories, type);
        return json;
    }

    @TypeConverter
    public List<ProductCategoryModel> toProductCategoriesList(String productCategoriesString) {
        if (productCategoriesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductCategoryModel>>() {
        }.getType();
        List<ProductCategoryModel> productCategoriesList = gson.fromJson(productCategoriesString, type);
        return productCategoriesList;
    }

    @TypeConverter
    public String fromOptionValuesList(List<OptionValues> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionValues>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<OptionValues> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionValues>>() {
        }.getType();
        List<OptionValues> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    @TypeConverter
    public String fromOptionList(List<Options> options) {
        if (options == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Options>>() {
        }.getType();
        String json = gson.toJson(options, type);
        return json;
    }

    @TypeConverter
    public List<Options> toOptionsList(String optionString) {
        if (optionString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Options>>() {
        }.getType();
        List<Options> productOptionList = gson.fromJson(optionString, type);
        return productOptionList;
    }

    @TypeConverter
    public String fromCartModelToString(CartModel cartData) {
        if (cartData == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<CartModel>() {
        }.getType();
        String json = gson.toJson(cartData, type);
        return json;
    }

    @TypeConverter
    public CartModel fromStringToCartModel(String cartDataString) {
        if (cartDataString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<CartModel>() {
        }.getType();
        CartModel cartData = gson.fromJson(cartDataString, type);
        return cartData;
    }

    @TypeConverter
    public String fromCashModelToString(CashModel cashData) {
        if (cashData == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<CashModel>() {
        }.getType();
        String json = gson.toJson(cashData, type);
        return json;
    }

    @TypeConverter
    public Tax fromStringToTaxModel(String taxDataString) {
        if (taxDataString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Tax>() {
        }.getType();
        Tax taxData = gson.fromJson(taxDataString, type);
        return taxData;
    }

    @TypeConverter
    public String fromTaxModelToString(Tax taxData) {
        if (taxData == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Tax>() {
        }.getType();
        String json = gson.toJson(taxData, type);
        return json;
    }

    @TypeConverter
    public CashModel fromStringToCashModel(String cashDataString) {
        if (cashDataString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<CashModel>() {
        }.getType();
        CashModel cashData = gson.fromJson(cashDataString, type);
        return cashData;
    }

    @TypeConverter
    public String fromCashDrawerItemToString(List<CashDrawerItems> cashDrawerItems) {
        if (cashDrawerItems == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CashDrawerItems>>() {
        }.getType();
        String json = gson.toJson(cashDrawerItems, type);
        return json;
    }

    @TypeConverter
    public List<CashDrawerItems> fromStringToCashDrawerItem(String cashDrawerItemsString) {
        if (cashDrawerItemsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CashDrawerItems>>() {
        }.getType();
        List<CashDrawerItems> cashDrawerItems = gson.fromJson(cashDrawerItemsString, type);
        return cashDrawerItems;
    }
}
