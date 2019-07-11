package com.recse4cloud.pay.ali.alipay;

public class Constants {

    /**
     * 阿里开放网关
     */
    public static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";

    /**
     * 默认签名加密类型
     */
    public static final String DEFAULT_SIGN_TYPE = "RSA2";

    /**
     * 参数格式
     */
    public static final String DEFAULT_FORMAT = "json";

    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 产品类型
     */
    public static final String DEFAULT_PRODUCT_CODE = "PRE_AUTH_ONLINE";

    /**
     * 产品类型
     */
    public static final String APP_TRADE_PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    /**
     * 信用类目
     */
    public static final String DEFAULT_CATEGORY = "{\"category\":\"RENT_OUTDOOR_EQUIPMENT\"}";

    /**
     * 测试用阿里公钥
     */
    public static final String ALI_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl81TYGUToJYNnTH8mCkszcehC9MYYoJ+Hp059qemfZdjxfLoq8ug7eGZZNJnmAb9hGe7PZvvfCKPj6uaazBIElTVFVOgTFzdlmTjZNYOYcB7v+iAFbQqv0GRZWn7Y2YlgGX97MpPUdDorD74Mm8PutAjD30A8ow3/UutuJwSCUZe+TMX45nfJI3LOqza9o+i+ZyNyyFabtS3wyylEUH2tau/KIXnbNWQoyCb2WezyFt9vqnNcBM2ynmbfMy2tXzPAb1F3VBM8yS76LjzHPzYVB6wHFyiDA3A2Gx7LipvRmjdo3Ymmpb5A10TYz8AQybkO8hkUNRhBrosTYjh16epwQIDAQAB";

    /**
     * 测试商户公钥
     */
    public static final String CUST_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsLnyX7f+8Cz23IecL+7g2+083jOCofnTkobnKh8Ctm3dx22qnuQjlw35LmL+o4Ffg1NaTgHJWMjtIUBprnwkdukugCjT/j+MFRj5DQwL96raNc46sz/xe0uqaN5+XxQlm8dQM+ytfybY+g8g7WOdiGxvW3EfhCpZcQeUF/5ktNjpChHv+MwMnObuaa9DkPLrlfJZXeS6u13AjDCYQZzes2tazO1RUjp8zTo3CXpIuKWA7PpvdOAVVF4KEcJD2hHM1su0nl46EISAUx8M9y3x/R4p0GD6BnIc1AqaPDg3SuzH8BvFfOfyF07g26zhBLWQqDG6FwXO/7+AEACIXPwRKwIDAQAB";

    /**
     * 测试用商户私钥
     */
    public static final String CUST_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCwufJft/7wLPbch5wv7uDb7TzeM4Kh+dOShucqHwK2bd3Hbaqe5COXDfkuYv6jgV+DU1pOAclYyO0hQGmufCR26S6AKNP+P4wVGPkNDAv3qto1zjqzP/F7S6po3n5fFCWbx1Az7K1/Jtj6DyDtY52IbG9bcR+EKllxB5QX/mS02OkKEe/4zAyc5u5pr0OQ8uuV8lld5Lq7XcCMMJhBnN6za1rM7VFSOnzNOjcJeki4pYDs+m904BVUXgoRwkPaEczWy7SeXjoQhIBTHwz3LfH9HinQYPoGchzUCpo8ODdK7MfwG8V85/IXTuDbrOEEtZCoMboXBc7/v4AQAIhc/BErAgMBAAECggEAWLnShNn0pZJ9kfSHps0QmXn/EDPeRwINhcgy8MPG8d4w9XoKqmExuRnr260Z2O8QC/fiI73j365o10PmixKKoCSsEu3smP5AjysNhs5gh8SAb3kNoYbd7x/q+2GRIp0T0KT9kEQoSukvEEReLLDRm3eNZORjhVBZvVSntnAjEWPq4L6FFGhhg2n5vISOp78SIQDFBHwq2F0tEMRgi2vwzlM9rlKKzG3pGgVV7FneSvhIxv3hkkNd7mGzzaFdbgVoMAaY9hB+oD9mi65Tu8JaJ8jI2RG5sWtKkEIhQb92alX/IDDjYGr1biQqs2/KtVLF4/G5BnS4+amAhMag1BwWwQKBgQDq+VDULjUG7F4THJxEfEguqmYB4viITbLR7BKhBbpmHAno5SqTSqXTStf+aAt5TDn9NhPY1IoTVhCL4EAAEEmDL0rQbeYeppK1p4K8nKR6veCarC2ah+dG2ARlGCFFRSqCdzWUheiixjoC89ZFKv7PanX07q7xRjebQA8wm1UhIQKBgQDAilIZOXEvwJT0PciJRFEHyHGRrQj5arv7yFqknWygbNY4uu+SgYqdC6qOc4ufvwc7/ABkSSQYP1WjO8cZEhBhM81Zk1brCXt53qq4VLNzI00lb/pOKImTWTSLPYat2g+JDz2Zo/ww6PN/akoJ0YB0FazLA83nUKWPKbhcpQVMywKBgQDgbP4xmmyVTz9PG715gAblY2UImkNs1GFeNQ9Z8oGlM1SXuAIhKmQV2l9QowmKci7U1UjBicbTTxmAr9AQ/31nKc1YJA9t6QUpMXQiN661eRPER31LIlDYjkwsTo7AfZ5re/aWBAw9I/2WglR+PeGgM/uz1CENW1aTFu0rLMFzYQKBgGEWnrV3gLq6mqHacb++AjoxegsiPO9AQhCBhCuT8k2m9BLhOKMgzVvJoTR50UM5WZWGyk/HjfroHO/V2dyrfjT1oSv1HxQf0PKwNFgE5gQ+Hc2t/ILi8BSsG9HGsZ4C4miTldVemnGzVe2/FByFVweyUlKA1Zg503IwcHXPBLAnAoGBAORsduZfdiSY0x+pQdpIxprLCIg7uRHkgD/jhoXAZbqExHA2fB0SBie4e0eeHkMtud32Awqql2EMLm5+dllkFzhreXTtwetmM0am0fdloAg4+14UNX+8MNQ4oEkOxb03OVVj4rbQzicDWUtn8Oisw6xxb3YUEFiu8reRgCxJAejf";

}
