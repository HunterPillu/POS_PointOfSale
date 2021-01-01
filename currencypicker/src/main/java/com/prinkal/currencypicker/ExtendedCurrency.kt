package com.prinkal.currencypicker

import android.content.Context
import java.util.*

class ExtendedCurrency(var code: String?, var name: String, var symbol: String?, var flag: Int) {

    fun loadFlagByCode(context: Context) {
        if (flag != -1) return
        try {
            flag = context.resources
                    .getIdentifier("flag_" + code!!.toLowerCase(), "drawable",
                            context.packageName)
        } catch (e: Exception) {
            e.printStackTrace()
            flag = -1
        }
    }

    /*
     * COMPARATORS
     */
    class ISOCodeComparator : Comparator<ExtendedCurrency> {
        override fun compare(currency: ExtendedCurrency, t1: ExtendedCurrency): Int {
            return currency.code!!.compareTo(t1.code!!)
        }
    }

    class NameComparator : Comparator<ExtendedCurrency> {
        override fun compare(currency: ExtendedCurrency, t1: ExtendedCurrency): Int {
            return currency.name!!.compareTo(t1.name!!)
        }
    }

    companion object {
        val CURRENCIES = arrayOf(
                ExtendedCurrency("EUR", "Euro", "€", R.drawable.flag_eur),
                ExtendedCurrency("USD", "United States Dollar", "$", R.drawable.flag_usd),
                ExtendedCurrency("GBP", "British Pound", "£", R.drawable.flag_gbp),
                ExtendedCurrency("CZK", "Czech Koruna", "Kč", R.drawable.flag_czk),
                ExtendedCurrency("TRY", "Turkish Lira", "₺", R.drawable.flag_try),
                ExtendedCurrency("AED", "Emirati Dirham", "د.إ", R.drawable.flag_aed),
                ExtendedCurrency("AFN", "Afghanistan Afghani", "؋", R.drawable.flag_afn),
                ExtendedCurrency("ARS", "Argentine Peso", "$", R.drawable.flag_ars),
                ExtendedCurrency("AUD", "Australian Dollar", "$", R.drawable.flag_aud),
                ExtendedCurrency("BBD", "Barbados Dollar", "$", R.drawable.flag_bbd),
                ExtendedCurrency("BDT", "Bangladeshi Taka", " Tk", R.drawable.flag_bdt),
                ExtendedCurrency("BGN", "Bulgarian Lev", "лв", R.drawable.flag_bgn),
                ExtendedCurrency("BHD", "Bahraini Dinar", "BD", R.drawable.flag_bhd),
                ExtendedCurrency("BMD", "Bermuda Dollar", "$", R.drawable.flag_bmd),
                ExtendedCurrency("BND", "Brunei Darussalam Dollar", "$", R.drawable.flag_bnd),
                ExtendedCurrency("BOB", "Bolivia Bolíviano", "\$b", R.drawable.flag_bob),
                ExtendedCurrency("BRL", "Brazil Real", "R$", R.drawable.flag_brl),
                ExtendedCurrency("BTN", "Bhutanese Ngultrum", "Nu.", R.drawable.flag_btn),
                ExtendedCurrency("BZD", "Belize Dollar", "BZ$", R.drawable.flag_bzd),
                ExtendedCurrency("CAD", "Canada Dollar", "$", R.drawable.flag_cad),
                ExtendedCurrency("CHF", "Switzerland Franc", "CHF", R.drawable.flag_chf),
                ExtendedCurrency("CLP", "Chile Peso", "$", R.drawable.flag_clp),
                ExtendedCurrency("CNY", "China Yuan Renminbi", "¥", R.drawable.flag_cny),
                ExtendedCurrency("COP", "Colombia Peso", "$", R.drawable.flag_cop),
                ExtendedCurrency("CRC", "Costa Rica Colon", "₡", R.drawable.flag_crc),
                ExtendedCurrency("DKK", "Denmark Krone", "kr", R.drawable.flag_dkk),
                ExtendedCurrency("DOP", "Dominican Republic Peso", "RD$", R.drawable.flag_dop),
                ExtendedCurrency("EGP", "Egypt Pound", "£", R.drawable.flag_egp),
                ExtendedCurrency("ETB", "Ethiopian Birr", "Br", R.drawable.flag_etb),
                ExtendedCurrency("GEL", "Georgian Lari", "₾", R.drawable.flag_gel),
                ExtendedCurrency("GHS", "Ghana Cedi", "¢", R.drawable.flag_ghs),
                ExtendedCurrency("GMD", "Gambian dalasi", "D", R.drawable.flag_gmd),
                ExtendedCurrency("GYD", "Guyana Dollar", "$", R.drawable.flag_gyd),
                ExtendedCurrency("HKD", "Hong Kong Dollar", "$", R.drawable.flag_hkd),
                ExtendedCurrency("HRK", "Croatia Kuna", "kn", R.drawable.flag_hrk),
                ExtendedCurrency("HUF", "Hungary Forint", "Ft", R.drawable.flag_huf),
                ExtendedCurrency("IDR", "Indonesia Rupiah", "Rp", R.drawable.flag_idr),
                ExtendedCurrency("ILS", "Israel Shekel", "₪", R.drawable.flag_ils),
                ExtendedCurrency("INR", "Indian Rupee", "₹", R.drawable.flag_inr),
                ExtendedCurrency("ISK", "Iceland Krona", "kr", R.drawable.flag_isk),
                ExtendedCurrency("JMD", "Jamaica Dollar", "J$", R.drawable.flag_jmd),
                ExtendedCurrency("JPY", "Japanese Yen", "¥", R.drawable.flag_jpy),
                ExtendedCurrency("KES", "Kenyan Shilling", "KSh", R.drawable.flag_kes),
                ExtendedCurrency("KRW", "Korea (South) Won", "₩", R.drawable.flag_krw),
                ExtendedCurrency("KWD", "Kuwaiti Dinar", "د.ك", R.drawable.flag_kwd),
                ExtendedCurrency("KYD", "Cayman Islands Dollar", "$", R.drawable.flag_kyd),
                ExtendedCurrency("KZT", "Kazakhstan Tenge", "лв", R.drawable.flag_kzt),
                ExtendedCurrency("LAK", "Laos Kip", "₭", R.drawable.flag_lak),
                ExtendedCurrency("LKR", "Sri Lanka Rupee", "₨", R.drawable.flag_lkr),
                ExtendedCurrency("LRD", "Liberia Dollar", "$", R.drawable.flag_lrd),
                ExtendedCurrency("LTL", "Lithuanian Litas", "Lt", R.drawable.flag_ltl),
                ExtendedCurrency("MAD", "Moroccan Dirham", "MAD", R.drawable.flag_mad),
                ExtendedCurrency("MDL", "Moldovan Leu", "MDL", R.drawable.flag_mdl),
                ExtendedCurrency("MKD", "Macedonia Denar", "ден", R.drawable.flag_mkd),
                ExtendedCurrency("MNT", "Mongolia Tughrik", "₮", R.drawable.flag_mnt),
                ExtendedCurrency("MUR", "Mauritius Rupee", "₨", R.drawable.flag_mur),
                ExtendedCurrency("MWK", "Malawian Kwacha", "MK", R.drawable.flag_mwk),
                ExtendedCurrency("MXN", "Mexico Peso", "$", R.drawable.flag_mxn),
                ExtendedCurrency("MYR", "Malaysia Ringgit", "RM", R.drawable.flag_myr),
                ExtendedCurrency("MZN", "Mozambique Metical", "MT", R.drawable.flag_mzn),
                ExtendedCurrency("NAD", "Namibia Dollar", "$", R.drawable.flag_nad),
                ExtendedCurrency("NGN", "Nigeria Naira", "₦", R.drawable.flag_ngn),
                ExtendedCurrency("NIO", "Nicaragua Cordoba", "C$", R.drawable.flag_nio),
                ExtendedCurrency("NOK", "Norway Krone", "kr", R.drawable.flag_nok),
                ExtendedCurrency("NPR", "Nepal Rupee", "₨", R.drawable.flag_npr),
                ExtendedCurrency("NZD", "New Zealand Dollar", "$", R.drawable.flag_nzd),
                ExtendedCurrency("OMR", "Oman Rial", "﷼", R.drawable.flag_omr),
                ExtendedCurrency("PEN", "Peru Sol", "S/.", R.drawable.flag_pen),
                ExtendedCurrency("PGK", "Papua New Guinean Kina", "K", R.drawable.flag_pgk),
                ExtendedCurrency("PHP", "Philippines Peso", "₱", R.drawable.flag_php),
                ExtendedCurrency("PKR", "Pakistan Rupee", "₨", R.drawable.flag_pkr),
                ExtendedCurrency("PLN", "Poland Zloty", "zł", R.drawable.flag_pln),
                ExtendedCurrency("PYG", "Paraguay Guarani", "Gs", R.drawable.flag_pyg),
                ExtendedCurrency("QAR", "Qatar Riyal", "﷼", R.drawable.flag_qar),
                ExtendedCurrency("RON", "Romania Leu", "lei", R.drawable.flag_ron),
                ExtendedCurrency("RSD", "Serbia Dinar", "Дин.", R.drawable.flag_rsd),
                ExtendedCurrency("RUB", "Russia Ruble", "₽", R.drawable.flag_rub),
                ExtendedCurrency("SAR", "Saudi Arabia Riyal", "﷼", R.drawable.flag_sar),
                ExtendedCurrency("SEK", "Sweden Krona", "kr", R.drawable.flag_sek),
                ExtendedCurrency("SGD", "Singapore Dollar", "$", R.drawable.flag_sgd),
                ExtendedCurrency("SOS", "Somalia Shilling", "S", R.drawable.flag_sos),
                ExtendedCurrency("SRD", "Suriname Dollar", "$", R.drawable.flag_srd),
                ExtendedCurrency("THB", "Thailand Baht", "฿", R.drawable.flag_thb),
                ExtendedCurrency("TTD", "Trinidad and Tobago Dollar", "TT$", R.drawable.flag_ttd),
                ExtendedCurrency("TWD", "Taiwan New Dollar", "NT$", R.drawable.flag_twd),
                ExtendedCurrency("TZS", "Tanzanian Shilling", "TSh", R.drawable.flag_tzs),
                ExtendedCurrency("UAH", "Ukraine Hryvnia", "₴", R.drawable.flag_uah),
                ExtendedCurrency("UGX", "Ugandan Shilling", "USh", R.drawable.flag_ugx),
                ExtendedCurrency("UYU", "Uruguay Peso", "\$U", R.drawable.flag_uyu),
                ExtendedCurrency("VEF", "Venezuela Bolívar", "Bs", R.drawable.flag_vef),
                ExtendedCurrency("VND", "Viet Nam Dong", "₫", R.drawable.flag_vnd),
                ExtendedCurrency("YER", "Yemen Rial", "﷼", R.drawable.flag_yer),
                ExtendedCurrency("ZAR", "South Africa Rand", "R", R.drawable.flag_zar)
        )

        /*
     *      GENERIC STATIC FUNCTIONS
     */
        private var allCurrenciesList: List<ExtendedCurrency>? = null
        @JvmStatic
        val allCurrencies: List<ExtendedCurrency>?
            get() {
                if (allCurrenciesList == null) {
                    allCurrenciesList = Arrays.asList(*CURRENCIES)
                }
                return allCurrenciesList
            }

        @JvmStatic
        fun getCurrencyByISO(currencyIsoCode: String?): ExtendedCurrency? {
            // Because the data we have is sorted by ISO codes and not by names, we must check all
            // currencies one by one
            for (c in CURRENCIES) {
                if (currencyIsoCode == c.code) {
                    return c
                }
            }
            return null
        }

        fun getCurrencyByName(currencyName: String): ExtendedCurrency? {
            // Because the data we have is sorted by ISO codes and not by names, we must check all
            // currencies one by one
            for (c in CURRENCIES) {
                if (currencyName == c.name) {
                    return c
                }
            }
            return null
        }
    }
}