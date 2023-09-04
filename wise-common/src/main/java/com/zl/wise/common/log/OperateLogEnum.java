package com.zl.wise.common.log;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 业务操作日志枚举
 *
 * @author huanglinwei
 * @date 2020/8/14 9:36 PM
 * @version 1.0.0
 */
@Getter
public enum OperateLogEnum {

    DEFAULT("/secure", "Default", null, null),
    // Home
    WITHDRAW_ADD("/secure/trade/withdraw/add", "Settlement", null, null),
    // Secondary Merchants
    SECONDARY_MERCHANT_OPEN("/secure/merchant/secondary/open", "Activate Secondary Merchant", "", new String[]{"secondaryMerchantNo"}),
    SECONDARY_MERCHANT_CLOSE("/secure/merchant/secondary/close", "Inactivate Secondary Merchant", "", new String[]{"secondaryMerchantNo"}),
    SECONDARY_MERCHANT_MRD_BATCHADD("/secure/merchant/secondary/mrd/batchadd", "Batch Edit MDR", null, null),
    SECONDARY_MERCHANT_MRD_EDIT("/secure/merchant/secondary/mrd/edit", "Edit MDR", "", new String[]{"secondaryMerchantNo"}),
    // Terminals
    SECONDARY_DEVICE_OPEN("/secure/device/secondary/open", "Activate Terminal", "", new String[]{"secondaryTerminalNo"}),
    SECONDARY_DEVICE_CLOSE("/secure/device/secondary/close", "Inactivate Terminal", "", new String[]{"secondaryTerminalNo"}),
    SECONDARY_DEVICE_BATCHADD("/secure/device/secondary/batchadd", "Batch Add Terminal", null, null),
    SECONDARY_DEVICE_ADD("/secure/device/secondary/add", "Add Terminal", "", new String[]{"terminalId"}),

    // Transaction-Records
    TRANSACTION_RECORDS_ADD("/secure/trade/refund/add", "Refund", "Order ID ", new String[]{"globalId"}),
    // Transaction-Statements
    TRANSACTION_STATEMENTS_SUBSCRIBE_ADD("/secure/statement/subscribe/add", "Add Subscription Email", "", new String[]{"receiveAddress"}),
    TRANSACTION_STATEMENTS_SUBSCRIBE_DELETE("/secure/statement/subscribe/cancel/{id}", "Delete Subscription Email", null, null),
    TRANSACTION_STATEMENTS_CHANGESTARTTTIME("/secure/statement/beginTime/modify", "Change Daily StartTime", null, null),
    TRANSACTION_STATEMENTS_TRANSACTION_DOWNLOAD("/secure/statement/transaction/download", "Download Transaction Statement", null, null),
    TRANSACTION_STATEMENTS_SETTLEMENT_DOWNLOAD("/secure/statement/settlement/download", "Download Settlement Statement", null, null),
    TRANSACTION_STATEMENT_BALANCE_DOWNLOAD("/secure/statement/balance/download", "Download Balance Statement", null, null),
    TRANSACTION_STATEMENTS_TRANSACTION_RENEW_DOWNLOAD("/secure/statement/transaction/renew/download", "Renew Download Transaction Statement", null, null),
    TRANSACTION_STATEMENTS_SETTLEMENT_RENEW_DOWNLOAD("/secure/statement/settlement/renew/download", "Renew Download Settlement Statement", null, null),
    TRANSACTION_STATEMENT_BALANCE_RENEW_DOWNLOAD("/secure/statement/balance/renew/download", "Renew Download Balance Statement", null, null),
    TRANSACTION_STATEMENT_SETTLEMENT_CONFIG("/secure/statement/settlement/config/edit", "Statement Setting", null, null),

    // Balance-Records，其中 Settlement 同Home
    // Balance-Statements，其中第1，2，3条 同Transaction-Statements
//    STATEMENT_BALANCE_DOWNLOAD("/secure/statement/balance/download", "Download Balance Daily", null, null),

    // Products-Product list
    PRODUCT_GATEWAY_APPLY("/secure/product/gateway/apply", "Apply Basic Payment Gateway", null, null),
    PRODUCT_QRPAY_APPLY("/secure/product/qrpay/apply", "Apply QRPay", null, null),
    PRODUCT_TRANSFER_APPLY("/secure/product/transfer/apply", "Apply Transfer", null, null),
    PRODUCT_TRANSFERTOBANK_APPLY("/secure/product/transfertobank/apply", "Apply Transfer To Bank", null, null),
    PRODUCT_INAPP_APPLY("/secure/product/inapp/apply", "Apply In App", null, null),
    PRODUCT_SMARTCODE_APPLY("/secure/product/smartcode/apply", "Apply Smart Code", null, null),
    PRODUCT_SMART_APPLY("/secure/product/smart/apply", "Apply Smart Purchase", null, null),
    PRODUCT_AUTODEBIT_APPLY("/secure/product/autodebit/apply", "Apply Auto Debit", null, null),
    PRODUCT_INVOICE_APPLY("/secure/product/invoice/apply", "Apply invoice", null, null),
    PRODUCT_TAX_APPLY("/secure/product/tax/apply", "Apply invoice", null, null),
    PRODUCT_CASHTOPUP_APPLY("/secure/product/cashtopup/apply", "Cash Top Up", null, null),
    PRODUCT_PAYLINK_APPLY("/secure/product/paylink/apply", "Pay Link", null, null),
    PRODUCT_DIRECTPAY_APPLY("/secure/product/directpay/apply", "Direct Pay", null, null),
    // Products-Agreements
    CONTRACT_PRODUCT_DOWNLOAD("/secure/contract/product/download/{contractNo}", "Download Contract", null, null),
    CONTRACT_PRODUCT_GENERATE("/secure/contract/product/generate", "Generate Contract", null, null),
    CONTRACT_PRODUCT_REGENERATE("/secure/contract/product/regenerate", "Regenerate Contract", null, null),
    // Products-Device
    DEVICE_ADD("/secure/device/apply/add", "Apply Device", "Quantity ", new String[]{"number"}),
    DEVICE_DELETE("/secure/device/delete", "Return Device", "Device ID ", new String[]{"deviceId"}),
    DEVICE_ENABLE("/secure/device/enable", "Activate Device", "Device ID ", new String[]{"value"}),
    DEVICE_DISABLE("/secure/device/disable", "Inactivate Device", "Device ID ", new String[]{"value"}),
    // Products-Transfer
    TRANSFER_BATCH_ADD("/secure/trade/transfer/batch/add", "Batch Transfer", null, null),
    TRANSFER_BATCH_SUBMIT("/secure/trade/transfer/batch/submit", "Batch Transfer", null, null),
    // Products-My APPs
    INAPP_ADD("/secure/inapp/add", "Add App", "App Name ", new String[]{"name"}),
    INAPP_DELETE("/secure/inapp/delete", "Delete App", "App ID ", new String[]{"orderId"}),
    INAPP_EDIT("/secure/inapp/modify", "Edit APP", "App Name ", new String[]{"name"}),
    INAPP_OAUTH_SECRET("/secure/inapp/oauth/secret", "OAuth Regenrate Secret", "Client ID", new String[]{"clientId"}),
    INAPP_URL_EDIT("/secure/inapp/url/modify", "Edit APP URL", "Client ID", new String[]{"clientId"}),
    INAPP_OAUTH_ADD("/secure/inapp/oauth/add", "Add APP URL", "Client ID", new String[]{"clientId"}),
    // Products-PayLink
    PAYLINK_ADD("/secure/paylink/add", "Add Pay Link", "Pay Link Name ", new String[]{"productTitle"}),
    PAYLINK_DELETE("/secure/paylink/delete", "Delete Pay Link", "Pay Link ID ", new String[]{"id"}),
    PAYLINK_EDIT("/secure/paylink/modify", "Edit Pay Link", "Pay Link Name ", new String[]{"productTitle"}),

    // Promotion-Agent
    STAFF_AGENT_ENABLE("/secure/merchant/staff/agent/enable", "Activate Agent", "", new String[]{"value"}),
    STAFF_AGENT_DISABLE("/secure/merchant/staff/agent/disable", "Inactivate Agent", "", new String[]{"value"}),
    STAFF_AGENT_EDIT("/secure/merchant/staff/agent/edit", "Edit Agent", "", new String[]{"staffName"}),
    STAFF_AGENT_ADD("/secure/merchant/staff/agent/add", "Add Agent", "", new String[]{"staffName"}),
    // Promotion-Merchant
    MERCHANT_AGENT_EDIT("/secure/merchant/agent/edit", "Edit Merchant", "", new String[]{"merchantName"}),
    MERCHANT_AGENT_ADD("/secure/merchant/agent/add", "Add Merchant", "", new String[]{"merchantName"}),
    MERCHANT_AGENT_ACCESS("/preAuth/staff/chooseByMerchantMid", "Access Merchant", "", new String[]{"value"}),
    // Promotion-Agreement Template

    // Management-Agreement Template
    BANKCARD_BIND("/secure/bankcard/bind", "Add Bank Account", null, null),
    BANKCARD_UNBIND("/secure/bankcard/unbind", "Delete Bank Account", null, null),
    // Management-Merchant Information
    MERCHANT_EDIT("/secure/merchant/edit", "Edit Merchant", null, null),
    TAX_ADD("/secure/merchant/tax/add", "Add TRN", null, null),
    TAX_EDIT("/secure/merchant/tax/edit", "Edit TRN", null, null),
    // Management-Store Management
    STORE_CREATE("/secure/store/create", "Add Store", "", new String[]{"name"}),
    // Management-Staff Management
    STAFF_ADD("/secure/merchant/staff/add", "Add Staff", "Name ", new String[]{"staffName"}),
    STAFF_EDIT("/secure/merchant/staff/edit", "Edit Staff", "Name ", new String[]{"staffName"}),
    STAFF_ENABLE("/secure/merchant/staff/enable", "Activate Staff", "Mid ", new String[]{"value"}),
    STAFF_DISABLE("/secure/merchant/staff/disable", "Inactvate Staff", "Mid ", new String[]{"value"}),
    STAFF_DELETE("/secure/merchant/staff/delete", "Delete Staff", "Mid ", new String[]{"value"}),
    // Management-API Management
    SECRETKEY_ADDPUBLICKEY("/secure/secretkey/addpublickey", "Upload Public Key", null, null),
    SECRETKEY_DOWNLOADPUBLICKEY("/secure/secretkey/downloadpublickey", "Download PayBy Public Key", null, null),
    SECRETKEY_BIND("/secure/secretkey/bind", "Bind IP Address", null, null),
    // Management-Authorization history(审核订单二者合一)
    COMMON_AUDIT("/secure/common/audit/audit", "Audit Request", "", new String[]{"productOrderNo"}),
    // Settings-Time Zone Setting
    MEMBER_SETTIMEZONE("/secure/member/setTimeZone", "Time Zone Setting", null, null),
    // Settings-Change Password
    UPDATEPASSWORD_SUBMIT("/secure/updatePassword/submit", "Change Password", null, null),
    // Settings-Sign Off
    LOGOUT("/logout", "Sign Off", null, null),

    // Management-Tax Invoice
    TAX_INVOICE_EDIT("/secure/tax/invoice/edit", "Edit Tax Invoice", null, null),
    TAX_INVOICE_DOWNLOAD("/secure/tax/invoice/download", "Download Tax Invoice", null, null),
    TAX_INVOICE_RENEW_DOWNLOAD("/secure/tax/invoice/renew/download", "Regenerate Download Tax Invoice", null, null),

    // Management-Staff Management
    STAFF_EMAIL_ADD("/secure/merchant/staff/email/add", "Add Staff", "Name ", new String[]{"staffName"}),
    STAFF_EMAIL_EDIT("/secure/merchant/staff/email/edit", "Edit Staff", "Name ", new String[]{"staffName"}),
    STAFF_EMAIL_ENABLE("/secure/merchant/staff/email/enable", "Activate Staff", "Mid ", new String[]{"value"}),
    STAFF_EMAIL_DISABLE("/secure/merchant/staff/email/disable", "Inactvate Staff", "Mid ", new String[]{"value"}),
    STAFF_EMAIL_DELETE("/secure/merchant/staff/email/delete", "Delete Staff", "Mid ", new String[]{"value"}),

    PAY_PAGE_EDIT("/secure/trade/paypage/edit", "Edit Pay Page Config", "Id ", new String[]{"id"}),
    PAY_TYPE_SORT("/secure/trade/pay/type/sort", "Edit Pay Type Sort", null, null),
    PAY_TYPE_EDIT("/secure/trade/pay/type/status", "Edit Pay Type Status", null, null),
    ;

    private String url;
    private String operation;
    private String remark;
    private String[] fields;

    OperateLogEnum(String url, String operation, String remark, String[] fields) {
        this.url = url;
        this.operation = operation;
        this.remark = remark;
        this.fields = fields;
    }

    public static OperateLogEnum getOperateLogEnum(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        OperateLogEnum[] operateLogEnums = values();
        for (OperateLogEnum operateLogEnum : operateLogEnums) {
            if (url.contains(operateLogEnum.getUrl())) {
                return operateLogEnum;
            }
        }
        return null;
    }
}