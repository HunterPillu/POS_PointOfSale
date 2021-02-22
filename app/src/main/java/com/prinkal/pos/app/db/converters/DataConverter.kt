package com.prinkal.pos.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prinkal.pos.app.db.entity.OptionValues
import com.prinkal.pos.app.db.entity.Options
import com.prinkal.pos.app.db.entity.Tax
import com.prinkal.pos.app.model.CartModel
import com.prinkal.pos.app.model.CashDrawerItems
import com.prinkal.pos.app.model.CashModel
import com.prinkal.pos.app.model.ProductCategoryModel
import java.io.Serializable

class DataConverter : Serializable {
    @TypeConverter
    fun fromProductCategoriesList(productCategories: List<ProductCategoryModel?>?): String? {
        if (productCategories == null) {
            return null
        }
        val type = object : TypeToken<List<ProductCategoryModel?>?>() {}.type
        return Gson().toJson(productCategories, type)
    }

    @TypeConverter
    fun toProductCategoriesList(productCategoriesString: String?): List<ProductCategoryModel>? {
        if (productCategoriesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ProductCategoryModel?>?>() {}.type
        return gson.fromJson(productCategoriesString, type)
    }

    @TypeConverter
    fun fromOptionValuesList(optionValues: List<OptionValues?>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<OptionValues?>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toOptionValuesList(optionValuesString: String?): List<OptionValues>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<OptionValues?>?>() {}.type
        return gson.fromJson(optionValuesString, type)
    }

    @TypeConverter
    fun fromOptionList(options: List<Options?>?): String? {
        if (options == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Options?>?>() {}.type
        return gson.toJson(options, type)
    }

    @TypeConverter
    fun toOptionsList(optionString: String?): List<Options>? {
        if (optionString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Options?>?>() {}.type
        return gson.fromJson(optionString, type)
    }

    @TypeConverter
    fun fromCartModelToString(cartData: CartModel?): String? {
        if (cartData == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CartModel?>() {}.type
        return gson.toJson(cartData, type)
    }

    @TypeConverter
    fun fromStringToCartModel(cartDataString: String?): CartModel? {
        if (cartDataString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CartModel?>() {}.type
        return gson.fromJson(cartDataString, type)
    }

    @TypeConverter
    fun fromCashModelToString(cashData: CashModel?): String? {
        if (cashData == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CashModel?>() {}.type
        return gson.toJson(cashData, type)
    }

    @TypeConverter
    fun fromStringToTaxModel(taxDataString: String?): Tax? {
        if (taxDataString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Tax?>() {}.type
        return gson.fromJson(taxDataString, type)
    }

    @TypeConverter
    fun fromTaxModelToString(taxData: Tax?): String? {
        if (taxData == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Tax?>() {}.type
        return gson.toJson(taxData, type)
    }

    @TypeConverter
    fun fromStringToCashModel(cashDataString: String?): CashModel? {
        if (cashDataString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CashModel?>() {}.type
        return gson.fromJson(cashDataString, type)
    }

    @TypeConverter
    fun fromCashDrawerItemToString(cashDrawerItems: List<CashDrawerItems?>?): String? {
        if (cashDrawerItems == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<CashDrawerItems?>?>() {}.type
        return gson.toJson(cashDrawerItems, type)
    }

    @TypeConverter
    fun fromStringToCashDrawerItem(cashDrawerItemsString: String?): List<CashDrawerItems>? {
        if (cashDrawerItemsString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<CashDrawerItems?>?>() {}.type
        return gson.fromJson(cashDrawerItemsString, type)
    }
}