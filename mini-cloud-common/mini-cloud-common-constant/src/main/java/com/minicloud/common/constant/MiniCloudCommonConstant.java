package com.minicloud.common.constant;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.common.data.constant
 * @Project：mini-cloud
 * @name：MiniCloudCommonConstant
 * @Filename：MiniCloudCommonConstant
 */
public final class MiniCloudCommonConstant {


    public static final class UserConstant {

        public static final String DETAILS_USER = "user_info";

        public static final String CLIENT_CREDENTIALS  ="client_credentials";

        public static final String ACTIVE = "active";
    }

    public static final class LicenceConstant {

        public static final String AUTHOR = "author";

        public static final String ALAN_WANG = "alan.wang";

        public static final String CONTACT = "contact";


        public static final String DOMAIN = "minicloud.blog.csdn.net";

        public static final String CONTACT_WECHAT = "unix-blacker";

        public static final String LICENSE = "license";


    }

    public static final class ServerConstant {

        public static final String MINI_CLOUD_SERVER_AUTH = "mini-cloud-authentication-center";

        public static final String MINI_CLOUD_SERVER_UPMS = "mini-cloud-upms-center-biz";

        public static final String MINI_CLOUD_SERVER_GOODS = "mini-cloud-simulate-goods-biz";

        public static final String MINI_CLOUD_SERVER_ORDER = "mini-cloud-simulate-order-biz";

    }

    public static final class CommonConstant {

        public static final String TENANT_ID = "tenantId";

        public static final String FEIGN = "feign";

    }

    public static final class RequestHeaderConstant {

        public static final String FEIGN_REQUEST = "feign-request";

        public static final String VERSION = "version";

        public static final String AUTHORIZATION = "Authorization";

        public static final String CONTENT_TYPE  = "content-type";


    }

    public static final class OptionConstant {

        /**
         * 成功标记
         */
        public static final Integer SUCCESS = 0;

        /**
         * 失败标记
         */
        public static final  Integer FAIL = 1;
    }

    public static final class RocketMQConstant {

        public static final String IO_lOG = "iolog";


    }
}
