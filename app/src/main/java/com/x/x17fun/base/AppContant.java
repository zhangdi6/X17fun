package com.x.x17fun.base;

public class AppContant {


    //线上线下开关
    //0 线下  1 线上
    public static int TYPE = 1;
    public static final String DEBUG= "http://192.168.31.191:8080";
    public static final String RELEASE= "http://182.92.150.200:8080";

    public static final String WebUrl= "http://h5.edencitybrand.com/";
    String Ak = "7PkVS7cqVoIyfSHoD7XxhUVMRbw2Ft7F";

    public static  String BASE_URL = TYPE== 0 ? DEBUG:RELEASE;

    public static final String WXAPPID = "wx1ab47956105c5c00";
    public static final String APPSERCRET = "cb00302c85356fef23be0a87b4b5d8bd";

    public static final String YOUMENG_APPKEY = "5e7ac7e10cafb2cb76000027";
    public static final String YOUMENG_MSG_SECRET = "b36a4a43cf4e6a1988d37278ea249031";
    public static final String YOUMENG_MASTER_SECRET = "spligorlrx44ebjuoapd2pkbpuvljagy";

    public static final String APPID = "edencity_1";
    public static final String APPKEY = "edencity_2";

    public static final String XIAOMI_ID = "2882303761518357554";
    public static final String XIAOMI_KEY = "5621835730554";

    /*@DefaultDomain
    public static final String BASE_RELEASE= "http://121.42.184.173:8090/";*/
    //腾讯bugly
    public static final String BUGLY_APPID = "108b8c33f9";
    public static final String BUGLY_APPKEY = "50321ddb-81f7-4cab-a95f-ba617b9e95df";

    //人脸识别
    public static String apiKey = "pl6OwByA6KgW7Z5zIMFSlGWd";
    public static String secretKey = "dGmwAL9iV3dyBboB0M3zkO0MROPnI0M0";
    public static String licenseID = "1ab47956105c5c00-face-android";
    public static String licenseFileName = "idl-license.face-android";
}




