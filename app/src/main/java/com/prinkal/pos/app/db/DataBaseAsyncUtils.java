package com.prinkal.pos.app.db;

import android.content.Context;
import android.util.Log;

import com.prinkal.pos.app.connections.ApiUtils;
import com.prinkal.pos.app.db.converters.DataConverter;
import com.prinkal.pos.app.db.entity.Administrator;
import com.prinkal.pos.app.db.entity.CashDrawerModel;
import com.prinkal.pos.app.db.entity.Category;
import com.prinkal.pos.app.db.entity.Customer;
import com.prinkal.pos.app.db.entity.HoldCart;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.db.entity.OrderEntity;
import com.prinkal.pos.app.db.entity.Product;
import com.prinkal.pos.app.db.entity.Tax;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;
import com.prinkal.pos.app.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prinkal.pos.app.activity.BaseActivity.TAG;
import static com.prinkal.pos.app.constants.ApplicationConstants.ERROR_CODE;
import static com.prinkal.pos.app.constants.ApplicationConstants.ERROR_MSG;
import static com.prinkal.pos.app.constants.ApplicationConstants.ERROR_MSG_2;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_10_SKU_ALLREADY_EXIST;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_1_ADD_HOLD_CART;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_1_ADD_OPTION;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_1_ADD_TAX_RATE;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_1_SIGN_UP;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_1_UPDATE_ADMIN_DETAILS;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_2_DELETE_OPTION;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_2_DELETE_TAX;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_2_SIGN_IN;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_3_ADD_CATEGORY;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_3_UPDATE_OPTION;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_3_UPDATE_TAX;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_4_UPDATE_CATEGORY;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_5_DELETE_CATEGORY;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_6_ADD_CUSTOMER;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_6_ADD_PRODUCT;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_7_DELETE_CUSTOMER;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_7_DELETE_PRODUCT;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_8_UPDATE_CUSTOMER;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_8_UPDATE_PRODUCT;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_9_CUSTOMER_ALL_READY_EXIST;
import static com.prinkal.pos.app.constants.ApplicationConstants.SUCCESS_MSG_9_ORDER_PLACED;


public class DataBaseAsyncUtils {
    private static DataBaseAsyncUtils dataBaseAsyncUtils;

    public static synchronized DataBaseAsyncUtils getInstanse() {
        if (dataBaseAsyncUtils == null)
            dataBaseAsyncUtils = new DataBaseAsyncUtils();
        return dataBaseAsyncUtils;
    }

    class GetAdminByEmailAsyncTask extends CoroutinesAsyncTask<Administrator, Void,
            Administrator> {

        private final DataBaseCallBack dataBaseCallBack;
        private AppDatabase db;

        GetAdminByEmailAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Administrator doInBackground(Administrator... administrators) {
            Administrator administrator;
            try {
                administrator = db.administratorDao().findByEmail(administrators[0].getEmail(), administrators[0].getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return administrator;
        }

        @Override
        public void onPostExecute(Administrator administrator) {
            super.onPostExecute(administrator);
            if (administrator != null)
                dataBaseCallBack.onSuccess(administrator, SUCCESS_MSG_2_SIGN_IN);
            else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG_2);
        }
    }

    class GetAllAdminAsyncTask extends CoroutinesAsyncTask<Void, Void,
            Administrator> {

        private final DataBaseCallBack dataBaseCallBack;
        private AppDatabase db;

        GetAllAdminAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Administrator doInBackground(Void... voids) {
            Administrator administrator;
            try {
                administrator = db.administratorDao().getAll();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return administrator;
        }

        @Override
        public void onPostExecute(Administrator administrator) {
            super.onPostExecute(administrator);
            if (administrator != null)
                dataBaseCallBack.onSuccess(administrator, SUCCESS_MSG_2_SIGN_IN);
            else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG_2);
        }
    }

    public class AddAdminAsyncTask extends CoroutinesAsyncTask<Administrator, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public AddAdminAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Administrator... administrators) {
            try {
                db.administratorDao().insertAll(administrators);
                ApiUtils.getAPIService().createUser(administrators[0]).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (null != response.body()) {
                            Log.d("APIResponse", response.body().toString());
                        } else {
                            Log.d("APIResponse", "null rseponse");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.d("APIResponse", "onFailure");
                        t.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(true, SUCCESS_MSG_1_SIGN_UP);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class UpdateAdmin extends CoroutinesAsyncTask<Administrator, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public UpdateAdmin(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Administrator... administrator) {
            try {
                db.administratorDao().updateAdminById(administrator[0].getFirstName(), administrator[0].getLastName(), administrator[0].getUsername(), administrator[0].getUid());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(true, SUCCESS_MSG_1_UPDATE_ADMIN_DETAILS);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class AddCategoryAsyncTask extends CoroutinesAsyncTask<Category, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public AddCategoryAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Category... categories) {
            try {
                db.categoryDao().insertAll(categories);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(true, SUCCESS_MSG_3_ADD_CATEGORY);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetCategoryAsyncTask extends CoroutinesAsyncTask<Void, Void,
            List<Category>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetCategoryAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Category> doInBackground(Void... voids) {
            List<Category> categories = db.categoryDao().getAll();
            return categories;
        }

        @Override
        public void onPostExecute(List<Category> categoryList) {
            super.onPostExecute(categoryList);
            if (categoryList != null) {
                dataBaseCallBack.onSuccess(categoryList, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }

    }


    public class GetDrawerIncludedCategories extends CoroutinesAsyncTask<Void, Void,
            List<Category>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetDrawerIncludedCategories(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Category> doInBackground(Void... voids) {
            List<Category> categories = db.categoryDao().getCategoryIncludedInDrawerMenu(true, true);
            return categories;
        }

        @Override
        public void onPostExecute(List<Category> categoryList) {
            super.onPostExecute(categoryList);
            if (categoryList != null) {
                dataBaseCallBack.onSuccess(categoryList, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }

    }

    public class UpdateCategoryById extends CoroutinesAsyncTask<Category, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public UpdateCategoryById(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Category... categories) {
            try {
                db.categoryDao().updateCategoryById(categories[0].getCategoryName(), categories[0].isActive(), categories[0].isIncludeInDrawerMenu(), categories[0].getCId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_4_UPDATE_CATEGORY);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class DeleteCategoryById extends CoroutinesAsyncTask<Category, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public DeleteCategoryById(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Category... categories) {
            try {
                db.categoryDao().delete(categories[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_5_DELETE_CATEGORY);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class AddProductAsyncTask extends CoroutinesAsyncTask<Product, Void,
            Long> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        public AddProductAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Long doInBackground(Product... products) {
            long[] id;
            try {
                id = db.productDao().insertAll(products);
            } catch (Exception e) {
                e.printStackTrace();
                return Long.valueOf(0);
            }
            return id[0];
        }

        @Override
        public void onPostExecute(Long id) {
            super.onPostExecute(id);
            if (id != 0) {
                dataBaseCallBack.onSuccess(id, SUCCESS_MSG_6_ADD_PRODUCT);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class UpdateProductImages extends CoroutinesAsyncTask<Void, Void,
            Boolean> {

        private AppDatabase db;
        private String imagePath;
        private Long pId;
        private DataBaseCallBack callBack;

        public UpdateProductImages(AppDatabase userDatabase, String imagePath, Long pId, DataBaseCallBack callBack) {
            db = userDatabase;
            this.imagePath = imagePath;
            this.pId = pId;
            this.callBack = callBack;
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            long[] id;
            try {
                db.productDao().updateProductImages(imagePath, pId);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success)
                callBack.onSuccess(success, SUCCESS_MSG_6_ADD_PRODUCT);
            else
                callBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetAllProducts extends CoroutinesAsyncTask<Void, Void,
            List<Product>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllProducts(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Product> doInBackground(Void... voids) {
            List<Product> products = db.productDao().getAll();
            return products;
        }

        @Override
        public void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            if (products != null) {
                dataBaseCallBack.onSuccess(products, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetAllEnabledProducts extends CoroutinesAsyncTask<Void, Void,
            List<Product>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllEnabledProducts(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Product> doInBackground(Void... voids) {
            List<Product> products = db.productDao().getEnabledProduct(true);
            return products;
        }

        @Override
        public void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            if (products != null) {
                dataBaseCallBack.onSuccess(products, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetAllLowStockProducts extends CoroutinesAsyncTask<Integer, Void,
            List<Product>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllLowStockProducts(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Product> doInBackground(Integer... qty) {
            List<Product> products = db.productDao().getLowStockProducts(qty[0]);
            return products;
        }

        @Override
        public void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            if (products != null) {
                dataBaseCallBack.onSuccess(products, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class UpdateProduct extends CoroutinesAsyncTask<Product, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public UpdateProduct(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Product... products) {
            try {
                db.productDao().updateProduct(products[0].getImage()
                        , products[0].isEnabled()
                        , products[0].getProductName()
                        , products[0].getSku()
                        , products[0].getPrice()
                        , products[0].getSpecialPrice()
                        , products[0].isTaxableGoodsApplied()
                        , products[0].isTrackInventory()
                        , products[0].getQuantity()
                        , products[0].isStock()
                        , products[0].getWeight()
                        , (new DataConverter()).fromProductCategoriesList(products[0].getProductCategories())
                        , (new DataConverter()).fromOptionList(products[0].getOptions())
                        , (new DataConverter()).fromTaxModelToString(products[0].getProductTax())
                        , products[0].getPId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_8_UPDATE_PRODUCT);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class UpdateProductQty extends CoroutinesAsyncTask<Product, Void,
            Boolean> {

        private Context context;
        private AppDatabase db;

        public UpdateProductQty(Context context, AppDatabase userDatabase) {
            this.context = context;
            db = userDatabase;
        }

        @Override
        public Boolean doInBackground(Product... products) {
            try {
//                Log.d(TAG, "doInBackground: qty" + Integer.parseInt(products[0].getQuantity()) + "---" + Integer.parseInt(products[0].getCartQty()));
//                if (!AppSharedPref.isReturnCart(context))
                db.productDao().updateProductQty(Integer.parseInt(products[0].getQuantity()) - Integer.parseInt(products[0].getCartQty()) + ""
                        , products[0].getPId());
//                else
//                    db.productDao().updateProductQty(Integer.parseInt(products[0].getQuantity()) + Integer.parseInt(products[0].getCartQty()) + ""
//                            , products[0].getPId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    public class DeleteProduct extends CoroutinesAsyncTask<Product, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public DeleteProduct(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Product... products) {
            try {
                db.productDao().delete(products[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_7_DELETE_PRODUCT);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class CheckSkuExist extends CoroutinesAsyncTask<String, Void,
            Product> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public CheckSkuExist(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Product doInBackground(String... sku) {
            Product product = null;
            try {
                product = db.productDao().checkSkuExist(sku[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return product;
        }

        @Override
        public void onPostExecute(Product product) {
            super.onPostExecute(product);
            if (product != null) {
                dataBaseCallBack.onSuccess(product, SUCCESS_MSG_10_SKU_ALLREADY_EXIST);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class GetAllCustomers extends CoroutinesAsyncTask<Void, Void,
            List<Customer>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllCustomers(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Customer> doInBackground(Void... voids) {
            List<Customer> customers = db.customerDao().getAll();
            return customers;
        }

        @Override
        public void onPostExecute(List<Customer> customers) {
            super.onPostExecute(customers);
            if (customers != null) {
                dataBaseCallBack.onSuccess(customers, SUCCESS_MSG);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }


    public class AddCustomerAsyncTask extends CoroutinesAsyncTask<Customer, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public AddCustomerAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Customer... customers) {
            try {
                db.customerDao().insertAll(customers[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(true, SUCCESS_MSG_6_ADD_CUSTOMER);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }


    public class UpdateCustomerAsyncTask extends CoroutinesAsyncTask<Customer, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public UpdateCustomerAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Customer... customers) {
            try {
                db.customerDao().updateCustomerById(customers[0].getFirstName()
                        , customers[0].getLastName()
                        , customers[0].getEmail()
                        , customers[0].getContactNumber()
                        , customers[0].getAddressLine()
                        , customers[0].getCity()
                        , customers[0].getPostalCode()
                        , customers[0].getState()
                        , customers[0].getCountry()
                        , customers[0].getCustomerId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(true, SUCCESS_MSG_8_UPDATE_CUSTOMER);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class DeleteCustomer extends CoroutinesAsyncTask<Customer, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public DeleteCustomer(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Customer... customers) {
            try {
                db.customerDao().delete(customers[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_7_DELETE_CUSTOMER);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class CheckEmailExist extends CoroutinesAsyncTask<String, Void,
            Customer> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public CheckEmailExist(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Customer doInBackground(String... email) {
            Customer customer;
            try {
                customer = db.customerDao().checkEmailExist(email[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return customer;
        }

        @Override
        public void onPostExecute(Customer customer) {
            super.onPostExecute(customer);
            if (customer != null) {
                dataBaseCallBack.onSuccess(customer, SUCCESS_MSG_9_CUSTOMER_ALL_READY_EXIST);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class CheckNumberExist extends CoroutinesAsyncTask<String, Void,
            Customer> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public CheckNumberExist(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Customer doInBackground(String... number) {
            Customer customer = null;
            try {
                customer = db.customerDao().checkNumberExist(number[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customer;
        }

        @Override
        public void onPostExecute(Customer customer) {
            super.onPostExecute(customer);
            if (customer != null) {
                dataBaseCallBack.onSuccess(customer, SUCCESS_MSG_9_CUSTOMER_ALL_READY_EXIST);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class GenerateOrderAsyncTask extends CoroutinesAsyncTask<OrderEntity, Void,
            Long> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GenerateOrderAsyncTask(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Long doInBackground(OrderEntity... orders) {
            long[] id;
            try {
                id = db.orderDao().insertAll(orders[0]);
                Log.d(TAG, "doInBackground: " + id[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return Long.valueOf(0);
            }
            return id[0];
        }

        @Override
        public void onPostExecute(Long orderId) {
            super.onPostExecute(orderId);
            if (orderId != 0) {
                dataBaseCallBack.onSuccess(orderId, SUCCESS_MSG_9_ORDER_PLACED);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class UpdateRefundedOrderId extends CoroutinesAsyncTask<Void, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;
        private String returnedOrderId;
        private String currentOrderId;

        public UpdateRefundedOrderId(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack, String returnedOrderId, String currentOrderId) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
            this.returnedOrderId = returnedOrderId;
            this.currentOrderId = currentOrderId;
            this.returnedOrderId = returnedOrderId;
            this.currentOrderId = currentOrderId;
        }

        @Override
        public Boolean doInBackground(Void... voids) {
            long[] id;
            try {
                db.orderDao().updateRefundedOrderId(currentOrderId, Integer.parseInt(returnedOrderId) - 10000);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                dataBaseCallBack.onSuccess(success, SUCCESS_MSG_9_ORDER_PLACED);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetOrders extends CoroutinesAsyncTask<Void, Void,
            List<OrderEntity>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetOrders(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<OrderEntity> doInBackground(Void... voids) {
            List<OrderEntity> orders = db.orderDao().getAll();
            return orders;
        }

        @Override
        public void onPostExecute(List<OrderEntity> orders) {
            super.onPostExecute(orders);
            if (orders != null) {
                dataBaseCallBack.onSuccess(orders, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetOrdersById extends CoroutinesAsyncTask<String, Void,
            OrderEntity> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetOrdersById(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public OrderEntity doInBackground(String... orderIds) {
            OrderEntity orders = db.orderDao().loadByIds(Integer.parseInt(orderIds[0]));
            return orders;
        }

        @Override
        public void onPostExecute(OrderEntity order) {
            super.onPostExecute(order);
            if (order != null) {
                dataBaseCallBack.onSuccess(order, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetSearchData extends CoroutinesAsyncTask<String, Void,
            List<Product>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetSearchData(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Product> doInBackground(String... texts) {
            return db.productDao().getSearchData(texts[0]);
        }

        @Override
        public void onPostExecute(List<Product> searchData) {
            super.onPostExecute(searchData);
            if (searchData != null) {
                dataBaseCallBack.onSuccess(searchData, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetSearchOrders extends CoroutinesAsyncTask<String, Void,
            List<OrderEntity>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetSearchOrders(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<OrderEntity> doInBackground(String... texts) {
            return db.orderDao().getSearchOrders(texts[0]);
        }

        @Override
        public void onPostExecute(List<OrderEntity> searchData) {
            super.onPostExecute(searchData);
            if (searchData != null) {
                dataBaseCallBack.onSuccess(searchData, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class DeleteAllTables extends CoroutinesAsyncTask<Void, Void,
            Void> {

        private AppDatabase db;

        public DeleteAllTables(AppDatabase userDatabase) {
            db = userDatabase;
        }

        @Override
        public Void doInBackground(Void... texts) {
            db.orderDao().delete();
            db.productDao().delete();
            db.categoryDao().delete();
            db.customerDao().delete();
            db.holdCartDao().delete();
            db.optionDao().delete();
            db.cashDrawerDao().delete();
            db.taxDao().delete();
            return null;
        }
    }

    public class AddCartDataToHoldCart extends CoroutinesAsyncTask<HoldCart, Void,
            Long> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public AddCartDataToHoldCart(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Long doInBackground(HoldCart... holdCarts) {
            long[] id;
            try {
                id = db.holdCartDao().insertAll(holdCarts[0]);
                Log.d(TAG, "doInBackground: " + id[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return Long.valueOf(0);
            }
            return id[0];
        }

        @Override
        public void onPostExecute(Long orderId) {
            super.onPostExecute(orderId);
            if (orderId != 0) {
                dataBaseCallBack.onSuccess(orderId, SUCCESS_MSG_1_ADD_HOLD_CART);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetHoldCartData extends CoroutinesAsyncTask<Void, Void,
            List<HoldCart>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetHoldCartData(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<HoldCart> doInBackground(Void... voids) {
            List<HoldCart> holdCarts = db.holdCartDao().getAll();
            return holdCarts;
        }

        @Override
        public void onPostExecute(List<HoldCart> holdCarts) {
            super.onPostExecute(holdCarts);
            if (holdCarts != null) {
                dataBaseCallBack.onSuccess(holdCarts, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class DeleteHoldCartById extends CoroutinesAsyncTask<HoldCart, Void,
            Boolean> {

        private AppDatabase db;

        public DeleteHoldCartById(AppDatabase userDatabase) {
            db = userDatabase;
        }

        @Override
        public Boolean doInBackground(HoldCart... holdCarts) {
            try {
                db.holdCartDao().delete(holdCarts[0].getHoldCartId() - 12000);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    public class GetProductByBarcode extends CoroutinesAsyncTask<String, Void,
            Product> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetProductByBarcode(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Product doInBackground(String... texts) {
            return db.productDao().getProductByBarcode(texts[0]);
        }

        @Override
        public void onPostExecute(Product searchData) {
            super.onPostExecute(searchData);
            if (searchData != null) {
                dataBaseCallBack.onSuccess(searchData, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class AddCashDrawerData extends CoroutinesAsyncTask<CashDrawerModel, Void,
            Void> {

        private AppDatabase db;

        public AddCashDrawerData(AppDatabase userDatabase) {
            db = userDatabase;
        }

        @Override
        public Void doInBackground(CashDrawerModel... cashDrawerModels) {
            try {
                db.cashDrawerDao().insertAll(cashDrawerModels[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class UpdateCashData extends CoroutinesAsyncTask<CashDrawerModel, Void,
            Boolean> {

        private AppDatabase db;
        private DataBaseCallBack callBack;

        public UpdateCashData(AppDatabase userDatabase, DataBaseCallBack callBack) {
            db = userDatabase;
            this.callBack = callBack;
        }

        @Override
        public Boolean doInBackground(CashDrawerModel... cashDrawerModel) {
            try {
                DataConverter converter = new DataConverter();
                db.cashDrawerDao().updateCashDrawerModelByDate(cashDrawerModel[0].getClosingBalance()
                        , cashDrawerModel[0].getNetRevenue()
                        , cashDrawerModel[0].getInAmount()
                        , cashDrawerModel[0].getOutAmount()
                        , converter.fromCashDrawerItemToString(cashDrawerModel[0].getCashDrawerItems())
                        , cashDrawerModel[0].getFormattedClosingBalance()
                        , cashDrawerModel[0].getFormattedNetRevenue()
                        , cashDrawerModel[0].getFormattedInAmount()
                        , cashDrawerModel[0].getFormattedOutAmount()
                        , cashDrawerModel[0].getDate());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                callBack.onSuccess(success, "Update!");
            } else {
                callBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class GetCashDrawerDataByDate extends CoroutinesAsyncTask<String, Void,
            CashDrawerModel> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetCashDrawerDataByDate(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public CashDrawerModel doInBackground(String... date) {
            return db.cashDrawerDao().loadAllByDate(date[0]);
        }

        @Override
        public void onPostExecute(CashDrawerModel cashDrawerModel) {
            super.onPostExecute(cashDrawerModel);
            if (cashDrawerModel != null) {
                dataBaseCallBack.onSuccess(cashDrawerModel, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetAllCashDrawerData extends CoroutinesAsyncTask<Void, Void,
            List<CashDrawerModel>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllCashDrawerData(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<CashDrawerModel> doInBackground(Void... voids) {
            return db.cashDrawerDao().getAll();
        }

        @Override
        public void onPostExecute(List<CashDrawerModel> cashDrawerModelList) {
            super.onPostExecute(cashDrawerModelList);
            if (cashDrawerModelList != null) {
                dataBaseCallBack.onSuccess(cashDrawerModelList, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class AddOptions extends CoroutinesAsyncTask<Options, Void,
            Long> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public AddOptions(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Long doInBackground(Options... options) {
            Long[] id = db.optionDao().insertAll(options);
            return id[0];
        }

        @Override
        public void onPostExecute(Long id) {
            super.onPostExecute(id);
            if (id != null) {
                dataBaseCallBack.onSuccess(id, SUCCESS_MSG_1_ADD_OPTION);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetOptions extends CoroutinesAsyncTask<Void, Void,
            List<Options>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetOptions(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Options> doInBackground(Void... voids) {
            return db.optionDao().getAll();
        }

        @Override
        public void onPostExecute(List<Options> options) {
            super.onPostExecute(options);
            if (options != null) {
                dataBaseCallBack.onSuccess(options, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class UpdateOptions extends CoroutinesAsyncTask<Options, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public UpdateOptions(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Options... options) {
            try {
                db.optionDao().updateOptionsById(options[0].getOptionName()
                        , options[0].getType()
                        , (new DataConverter()).fromOptionValuesList(options[0].getOptionValues())
                        , options[0].getOptionId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_3_UPDATE_OPTION);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }

    public class DeleteOption extends CoroutinesAsyncTask<Options, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public DeleteOption(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Options... options) {
            try {
                db.optionDao().delete(options[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_2_DELETE_OPTION);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }


    public class AddTaxRate extends CoroutinesAsyncTask<Tax, Void,
            Long> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public AddTaxRate(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Long doInBackground(Tax... taxes) {
            Long[] id = db.taxDao().insertAll(taxes);
            return id[0];
        }

        @Override
        public void onPostExecute(Long id) {
            super.onPostExecute(id);
            if (id != null) {
                dataBaseCallBack.onSuccess(id, SUCCESS_MSG_1_ADD_TAX_RATE);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class GetAllTaxes extends CoroutinesAsyncTask<Void, Void,
            List<Tax>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllTaxes(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Tax> doInBackground(Void... voids) {
            List<Tax> taxes = db.taxDao().getAll();
            return taxes;
        }

        @Override
        public void onPostExecute(List<Tax> taxes) {
            super.onPostExecute(taxes);
            if (taxes != null) {
                dataBaseCallBack.onSuccess(taxes, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }

    public class UpdateTaxRate extends CoroutinesAsyncTask<Tax, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public UpdateTaxRate(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Tax... taxes) {
            try {
                db.taxDao().updateTaxById(taxes[0].getTaxName(), taxes[0].isEnabled(), taxes[0].getTaxRate(), taxes[0].getTaxId());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(true, SUCCESS_MSG_3_UPDATE_TAX);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }


    public class GetAllEnabledTaxes extends CoroutinesAsyncTask<Void, Void,
            List<Tax>> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public GetAllEnabledTaxes(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public List<Tax> doInBackground(Void... voids) {
            List<Tax> tax = db.taxDao().getEnabledTax(true);
            return tax;
        }

        @Override
        public void onPostExecute(List<Tax> taxList) {
            super.onPostExecute(taxList);
            if (taxList != null) {
                dataBaseCallBack.onSuccess(taxList, SUCCESS_MSG);
            } else
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
        }
    }


    public class DeleteTax extends CoroutinesAsyncTask<Tax, Void,
            Boolean> {

        private AppDatabase db;
        private final DataBaseCallBack dataBaseCallBack;

        public DeleteTax(AppDatabase userDatabase, DataBaseCallBack dataBaseCallBack) {
            db = userDatabase;
            this.dataBaseCallBack = dataBaseCallBack;
        }

        @Override
        public Boolean doInBackground(Tax... taxes) {
            try {
                db.taxDao().delete(taxes[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                dataBaseCallBack.onSuccess(aBoolean, SUCCESS_MSG_2_DELETE_TAX);
            } else {
                dataBaseCallBack.onFailure(ERROR_CODE, ERROR_MSG);
            }
        }
    }
}