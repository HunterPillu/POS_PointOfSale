package com.prinkal.pos.app.db

import android.content.Context
import com.prinkal.pos.app.activity.BaseActivity
import com.prinkal.pos.app.db.entity.*
import com.prinkal.pos.app.interfaces.DataBaseCallBack

class DataBaseController {
    fun getAdminDataByEmail(context: Context, data: Administrator, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAdminByEmailAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun getAdminData(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllAdminAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun updateAdmin(context: Context, administrator: Administrator, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateAdmin((context as BaseActivity).getDb(), dataBaseCallBack).execute(administrator)
    }

    fun insertAdministorDetails(context: Context, data: Administrator, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddAdminAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun addCategoryDetails(context: Context, data: Category, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddCategoryAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun getCategory(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetCategoryAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun getIncludedCategoryForDrawer(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetDrawerIncludedCategories((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun updateCategory(context: Context, data: Category, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateCategoryById((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun deleteCategory(context: Context, data: Category, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.DeleteCategoryById((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun addProduct(context: Context, data: Product, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddProductAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun updateProductImages(context: Context, imagePath: String, pId: Long, callBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateProductImages((context as BaseActivity).getDb(), imagePath, pId, callBack).execute()
    }

    fun getProducts(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllProducts((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun getAllEnabledProducts(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllEnabledProducts((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun getAllLowStockProducts(context: Context, minQty: Int, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllLowStockProducts((context as BaseActivity).getDb(), dataBaseCallBack).execute(minQty)
    }

    fun updateProduct(context: Context, data: Product, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateProduct((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun updateProductQty(context: Context, data: Product) {
        DataBaseAsyncUtils.instance.UpdateProductQty((context as BaseActivity).getDb()).execute(data)
    }

    fun deleteProduct(context: Context, data: Product, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.DeleteProduct((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun getCustomer(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllCustomers((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun addCustomer(context: Context, data: Customer, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddCustomerAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun updateCustomer(context: Context, data: Customer, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateCustomerAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun deleteCustomer(context: Context, customer: Customer, callBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.DeleteCustomer((context as BaseActivity).getDb(), callBack).execute(customer)
    }

    fun checkEmailExist(context: Context, email: String, callBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.CheckEmailExist((context as BaseActivity).getDb(), callBack).execute(email)
    }

    fun checkNumberExist(context: Context, email: String, callBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.CheckNumberExist((context as BaseActivity).getDb(), callBack).execute(email)
    }

    fun checkSkuExist(context: Context, sku: String, callBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.CheckSkuExist((context as BaseActivity).getDb(), callBack).execute(sku)
    }

    fun generateOrder(context: Context, data: OrderEntity, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GenerateOrderAsyncTask((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun updateRefundedOrderId(context: Context, returnOrderId: String, currentOrderId: String, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateRefundedOrderId((context as BaseActivity).getDb(), dataBaseCallBack, returnOrderId, currentOrderId).execute()
    }

    fun getOrders(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetOrders((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun getOrderById(context: Context, orderId: String, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetOrdersById((context as BaseActivity).getDb(), dataBaseCallBack).execute(orderId)
    }

    fun getSearchData(context: Context, searchText: String, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetSearchData((context as BaseActivity).getDb(), dataBaseCallBack).execute(searchText)
    }

    fun getSearchOrders(context: Context, searchText: String, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetSearchOrders((context as BaseActivity).getDb(), dataBaseCallBack).execute(searchText)
    }

    // -------------------- delete all tables -------------------------
    fun deleteAllTables(context: Context) {
        DataBaseAsyncUtils.instance.DeleteAllTables((context as BaseActivity).getDb()).execute()
    }

    fun addHoldCart(context: Context, data: HoldCart, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddCartDataToHoldCart((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun getHoldCart(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetHoldCartData((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun deleteHoldCart(context: Context, data: HoldCart) {
        DataBaseAsyncUtils.instance.DeleteHoldCartById((context as BaseActivity).getDb()).execute(data)
    }

    fun getProductByBarcode(context: Context, data: String, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetProductByBarcode((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun addOpeningBalance(context: Context, data: CashDrawerModel) {
        DataBaseAsyncUtils.instance.AddCashDrawerData((context as BaseActivity).getDb()).execute(data)
    }

    fun updateCashDrawer(context: Context, cashDrawerModel: CashDrawerModel, callBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateCashData((context as BaseActivity).getDb(), callBack).execute(cashDrawerModel)
    }

    fun getCashHistoryByDate(context: Context, date: String, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetCashDrawerDataByDate((context as BaseActivity).getDb(), dataBaseCallBack).execute(date)
    }

    fun getAllCashHistory(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllCashDrawerData((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun addOption(context: Context, options: Options, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddOptions((context as BaseActivity).getDb(), dataBaseCallBack).execute(options)
    }

    fun getOptions(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetOptions((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun updateOptions(context: Context, data: Options, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateOptions((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun deleteOption(context: Context, data: Options, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.DeleteOption((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    fun addTaxRate(context: Context, options: Tax, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.AddTaxRate((context as BaseActivity).getDb(), dataBaseCallBack).execute(options)
    }

    fun getAllTaxes(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllTaxes((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun getAllEnabledTaxes(context: Context, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.GetAllEnabledTaxes((context as BaseActivity).getDb(), dataBaseCallBack).execute()
    }

    fun updateTax(context: Context, tax: Tax, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.UpdateTaxRate((context as BaseActivity).getDb(), dataBaseCallBack).execute(tax)
    }

    fun deleteTax(context: Context, data: Tax, dataBaseCallBack: DataBaseCallBack) {
        DataBaseAsyncUtils.instance.DeleteTax((context as BaseActivity).getDb(), dataBaseCallBack).execute(data)
    }

    companion object {
        private var dataBaseController: DataBaseController? = null

        @JvmStatic
        val instance: DataBaseController
            get() {
                if (dataBaseController == null) dataBaseController = DataBaseController()
                return dataBaseController!!
            }
    }
}