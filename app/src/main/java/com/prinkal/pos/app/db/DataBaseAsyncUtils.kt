package com.prinkal.pos.app.db

import android.util.Log
import com.prinkal.pos.app.activity.BaseActivity
import com.prinkal.pos.app.connections.ApiUtils
import com.prinkal.pos.app.constants.ApplicationConstants
import com.prinkal.pos.app.db.converters.DataConverter
import com.prinkal.pos.app.db.entity.*
import com.prinkal.pos.app.interfaces.DataBaseCallBack

class DataBaseAsyncUtils {


    companion object {
        private var dataBaseAsyncUtils: DataBaseAsyncUtils? = null

        @JvmStatic
        val instance: DataBaseAsyncUtils
            get() {
                if (dataBaseAsyncUtils == null) dataBaseAsyncUtils = DataBaseAsyncUtils()
                return dataBaseAsyncUtils!!
            }
    }

    internal inner class GetAdminByEmailAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Administrator, Void?, Administrator?>() {
        override suspend fun doInBackground(vararg params: Administrator): Administrator? {
            val administrator: Administrator
            administrator = try {
                db.administratorDao().findByEmail(params[0].email, params[0].password)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return administrator
        }

        override fun onPostExecute(result: Administrator?) {
            super.onPostExecute(result)
            if (result != null) dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_2_SIGN_IN) else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG_2)
        }

    }

    internal inner class GetAllAdminAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, Administrator?>() {
        override suspend fun doInBackground(vararg params: Void?): Administrator? {
            val administrator: Administrator
            administrator = try {
                db.administratorDao().all
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return administrator
        }

        override fun onPostExecute(result: Administrator?) {
            super.onPostExecute(result)
            if (result != null) dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_2_SIGN_IN) else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG_2)
        }
    }

    inner class AddAdminAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Administrator, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Administrator): Boolean {
            try {
                db.administratorDao().insertAll(*params)
                val response = ApiUtils.aPIService.createUser(params[0])
                if (null != response) {
                    Log.d("APIResponse", response.toString())
                }
                /*object : Callback<ApiResponse?> {
                    override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {
                        if (null != response.body()) {
                            Log.d("APIResponse", response.body().toString())
                        } else {
                            Log.d("APIResponse", "null rseponse")
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                        Log.d("APIResponse", "onFailure")
                        t.printStackTrace()
                    }
                })*/
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(true, ApplicationConstants.SUCCESS_MSG_1_SIGN_UP)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateAdmin(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Administrator, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Administrator): Boolean {
            try {
                db.administratorDao().updateAdminById(params[0].firstName, params[0].lastName, params[0].username, params[0].uid)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(true, ApplicationConstants.SUCCESS_MSG_1_UPDATE_ADMIN_DETAILS)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class AddCategoryAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Category, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Category): Boolean {
            try {
                db.categoryDao().insertAll(*params)
            } catch (e: Exception) {
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(true, ApplicationConstants.SUCCESS_MSG_3_ADD_CATEGORY)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetCategoryAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Category?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Category> {
            return db.categoryDao().all
        }

        override fun onPostExecute(result: List<Category?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetDrawerIncludedCategories(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Category?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Category> {
            return db.categoryDao().getCategoryIncludedInDrawerMenu(true, true)
        }

        override fun onPostExecute(result: List<Category?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateCategoryById(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Category, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Category): Boolean {
            try {
                db.categoryDao().updateCategoryById(params[0].categoryName, params[0].isActive, params[0].isIncludeInDrawerMenu, params[0].cId)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_4_UPDATE_CATEGORY)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class DeleteCategoryById(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Category, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Category): Boolean {
            try {
                db.categoryDao().delete(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_5_DELETE_CATEGORY)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class AddProductAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Product, Void?, Long?>() {

        override suspend fun doInBackground(vararg params: Product): Long {
            val id: LongArray
            id = try {
                db.productDao().insertAll(*params)
            } catch (e: Exception) {
                e.printStackTrace()
                return java.lang.Long.valueOf(0)
            }
            return id[0]
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            if (result != 0L) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_6_ADD_PRODUCT)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateProductImages(private val db: AppDatabase, private val imagePath: String, private val pId: Long, private val callBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Void?): Boolean {
            try {
                db.productDao().updateProductImages(imagePath, pId)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) callBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_6_ADD_PRODUCT) else callBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetAllProducts(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Product?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Product> {
            return db.productDao().all
        }

        override fun onPostExecute(result: List<Product?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetAllEnabledProducts(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Product?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Product> {
            return db.productDao().getEnabledProduct(true)
        }

        override fun onPostExecute(result: List<Product?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetAllLowStockProducts(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Int?, Void?, List<Product?>>() {

        override fun onPostExecute(result: List<Product?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }

        /*override suspend fun doInBackground(vararg params: Int): List<Product?>? {
            return db.productDao().getLowStockProducts(params[0])
        }*/

        override suspend fun doInBackground(vararg params: Int?): List<Product> {
            return db.productDao().getLowStockProducts(params[0]!!)
        }
    }

    inner class UpdateProduct(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Product, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Product): Boolean {
            try {
                db.productDao().updateProduct(params[0].image, params[0].isEnabled, params[0].productName, params[0].sku, params[0].price, params[0].specialPrice, params[0].isTaxableGoodsApplied, params[0].isTrackInventory, params[0].quantity, params[0].isStock, params[0].weight, DataConverter().fromProductCategoriesList(params[0].productCategories), DataConverter().fromOptionList(params[0].options), DataConverter().fromTaxModelToString(params[0].productTax), params[0].pId)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_8_UPDATE_PRODUCT)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class UpdateProductQty(private val db: AppDatabase) : CoroutinesAsyncTask<Product, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Product): Boolean {
            try {
//                Log.d(TAG, "doInBackground: qty" + Integer.parseInt(products[0].getQuantity()) + "---" + Integer.parseInt(products[0].getCartQty()));
//                if (!AppSharedPref.isReturnCart(context))
                db.productDao().updateProductQty((params[0].quantity.toInt() - params[0].cartQty.toInt()).toString() + "", params[0].pId)
                //                else
//                    db.productDao().updateProductQty(Integer.parseInt(products[0].getQuantity()) + Integer.parseInt(products[0].getCartQty()) + ""
//                            , products[0].getPId());
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }
    }

    inner class DeleteProduct(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Product, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Product): Boolean {
            try {
                db.productDao().delete(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_7_DELETE_PRODUCT)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class CheckSkuExist(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, Product?>() {
        override suspend fun doInBackground(vararg params: String): Product? {
            var product: Product? = null
            try {
                product = db.productDao().checkSkuExist(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return product
        }

        override fun onPostExecute(result: Product?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_10_SKU_ALLREADY_EXIST)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class GetAllCustomers(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Customer?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Customer>? {
            return db.customerDao().all
        }

        override fun onPostExecute(result: List<Customer?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class AddCustomerAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Customer, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Customer): Boolean {
            try {
                db.customerDao().insertAll(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(true, ApplicationConstants.SUCCESS_MSG_6_ADD_CUSTOMER)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateCustomerAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Customer, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Customer): Boolean {
            try {
                db.customerDao().updateCustomerById(params[0].firstName, params[0].lastName, params[0].email, params[0].contactNumber, params[0].addressLine, params[0].city, params[0].postalCode, params[0].state, params[0].country, params[0].customerId)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(true, ApplicationConstants.SUCCESS_MSG_8_UPDATE_CUSTOMER)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class DeleteCustomer(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Customer, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Customer): Boolean {
            try {
                db.customerDao().delete(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_7_DELETE_CUSTOMER)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class CheckEmailExist(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, Customer?>() {
        override suspend fun doInBackground(vararg params: String): Customer? {
            val customer: Customer
            customer = try {
                db.customerDao().checkEmailExist(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return customer
        }

        override fun onPostExecute(result: Customer?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_9_CUSTOMER_ALL_READY_EXIST)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class CheckNumberExist(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, Customer?>() {
        override suspend fun doInBackground(vararg params: String): Customer? {
            var customer: Customer? = null
            try {
                customer = db.customerDao().checkNumberExist(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return customer
        }

        override fun onPostExecute(result: Customer?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_9_CUSTOMER_ALL_READY_EXIST)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class GenerateOrderAsyncTask(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<OrderEntity, Void?, Long?>() {
        override suspend fun doInBackground(vararg params: OrderEntity): Long {
            val id: LongArray
            try {
                id = db.orderDao().insertAll(params[0])
                Log.d(BaseActivity.TAG, "doInBackground: " + id[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return java.lang.Long.valueOf(0)
            }
            return id[0]
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            if (result != 0L) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_9_ORDER_PLACED)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateRefundedOrderId(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack, private val returnedOrderId: String, private val currentOrderId: String) : CoroutinesAsyncTask<Void?, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Void?): Boolean {
            try {
                db.orderDao().updateRefundedOrderId(currentOrderId, returnedOrderId.toInt() - 10000)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_9_ORDER_PLACED)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetOrders(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<OrderEntity?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<OrderEntity>? {
            return db.orderDao().all
        }

        override fun onPostExecute(result: List<OrderEntity?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetOrdersById(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, OrderEntity?>() {
        override suspend fun doInBackground(vararg params: String): OrderEntity? {
            return db.orderDao().loadByIds(params[0].toInt())
        }

        override fun onPostExecute(result: OrderEntity?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetSearchData(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, List<Product?>?>() {
        override suspend fun doInBackground(vararg params: String): List<Product>? {
            return db.productDao().getSearchData(params[0])
        }

        override fun onPostExecute(result: List<Product?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetSearchOrders(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, List<OrderEntity?>?>() {
        override suspend fun doInBackground(vararg params: String): List<OrderEntity>? {
            return db.orderDao().getSearchOrders(params[0])
        }

        override fun onPostExecute(result: List<OrderEntity?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class DeleteAllTables(private val db: AppDatabase) : CoroutinesAsyncTask<Void?, Void?, Void?>() {
        override suspend fun doInBackground(vararg params: Void?): Void? {
            db.orderDao().delete()
            db.productDao().delete()
            db.categoryDao().delete()
            db.customerDao().delete()
            db.holdCartDao().delete()
            db.optionDao().delete()
            db.cashDrawerDao().delete()
            db.taxDao().delete()
            return null
        }
    }

    inner class AddCartDataToHoldCart(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<HoldCart, Void?, Long?>() {
        override suspend fun doInBackground(vararg params: HoldCart): Long {
            val id: LongArray
            try {
                id = db.holdCartDao().insertAll(params[0])
                Log.d(BaseActivity.TAG, "doInBackground: " + id[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return java.lang.Long.valueOf(0)
            }
            return id[0]
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            if (result != 0L) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_1_ADD_HOLD_CART)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetHoldCartData(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<HoldCart?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<HoldCart>? {
            return db.holdCartDao().all
        }

        override fun onPostExecute(result: List<HoldCart?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class DeleteHoldCartById(private val db: AppDatabase) : CoroutinesAsyncTask<HoldCart, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: HoldCart): Boolean {
            try {
                db.holdCartDao().delete(params[0].holdCartId - 12000)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

    }

    inner class GetProductByBarcode(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, Product?>() {
        override suspend fun doInBackground(vararg params: String): Product? {
            return db.productDao().getProductByBarcode(params[0])
        }

        override fun onPostExecute(result: Product?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class AddCashDrawerData(private val db: AppDatabase) : CoroutinesAsyncTask<CashDrawerModel, Void?, Void?>() {
        override suspend fun doInBackground(vararg params: CashDrawerModel): Void? {
            try {
                db.cashDrawerDao().insertAll(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    inner class UpdateCashData(private val db: AppDatabase, private val callBack: DataBaseCallBack) : CoroutinesAsyncTask<CashDrawerModel, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: CashDrawerModel): Boolean {
            try {
                val converter = DataConverter()
                db.cashDrawerDao().updateCashDrawerModelByDate(params[0].closingBalance, params[0].netRevenue, params[0].inAmount, params[0].outAmount, converter.fromCashDrawerItemToString(params[0].cashDrawerItems), params[0].formattedClosingBalance, params[0].formattedNetRevenue, params[0].formattedInAmount, params[0].formattedOutAmount, params[0].date)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                callBack.onSuccess(result, "Update!")
            } else {
                callBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class GetCashDrawerDataByDate(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<String, Void?, CashDrawerModel?>() {
        override suspend fun doInBackground(vararg params: String): CashDrawerModel? {
            return db.cashDrawerDao().loadAllByDate(params[0])
        }

        override fun onPostExecute(result: CashDrawerModel?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetAllCashDrawerData(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<CashDrawerModel?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<CashDrawerModel>? {
            return db.cashDrawerDao().all
        }

        override fun onPostExecute(result: List<CashDrawerModel?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class AddOptions(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Options, Void?, Long?>() {
        override suspend fun doInBackground(vararg params: Options): Long {
            val id = db.optionDao().insertAll(*params)
            return id[0]
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_1_ADD_OPTION)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetOptions(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Options?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Options>? {
            return db.optionDao().all
        }

        override fun onPostExecute(result: List<Options?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateOptions(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Options, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Options): Boolean {
            try {
                db.optionDao().updateOptionsById(params[0].optionName, params[0].type, DataConverter().fromOptionValuesList(params[0].optionValues), params[0].optionId)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_3_UPDATE_OPTION)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class DeleteOption(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Options, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Options): Boolean {
            try {
                db.optionDao().delete(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_2_DELETE_OPTION)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }

    inner class AddTaxRate(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Tax, Void?, Long?>() {
        override suspend fun doInBackground(vararg params: Tax): Long {
            val id = db.taxDao().insertAll(*params)
            return id[0]
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_1_ADD_TAX_RATE)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetAllTaxes(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Tax?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Tax>? {
            return db.taxDao().all
        }

        override fun onPostExecute(result: List<Tax?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class UpdateTaxRate(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Tax, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Tax): Boolean {
            try {
                db.taxDao().updateTaxById(params[0].taxName, params[0].isEnabled, params[0].taxRate, params[0].taxId)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(true, ApplicationConstants.SUCCESS_MSG_3_UPDATE_TAX)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class GetAllEnabledTaxes(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Void?, Void?, List<Tax?>?>() {
        override suspend fun doInBackground(vararg params: Void?): List<Tax> {
            return db.taxDao().getEnabledTax(true)
        }

        override fun onPostExecute(result: List<Tax?>?) {
            super.onPostExecute(result)
            if (result != null) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG)
            } else dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
        }
    }

    inner class DeleteTax(private val db: AppDatabase, private val dataBaseCallBack: DataBaseCallBack) : CoroutinesAsyncTask<Tax, Void?, Boolean?>() {
        override suspend fun doInBackground(vararg params: Tax): Boolean {
            try {
                db.taxDao().delete(params[0])
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            if (result!!) {
                dataBaseCallBack.onSuccess(result, ApplicationConstants.SUCCESS_MSG_2_DELETE_TAX)
            } else {
                dataBaseCallBack.onFailure(ApplicationConstants.ERROR_CODE, ApplicationConstants.ERROR_MSG)
            }
        }
    }
}