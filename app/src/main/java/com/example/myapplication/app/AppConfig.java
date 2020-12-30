package com.example.myapplication.app;

public class AppConfig {
  public static String  urlpart ="http://172.20.10.6" ;
  // Server user login url
  public static   String URL_LOGIN = urlpart+"/android_login_api/login.php";

  // Server user register url
  public static String URL_REGISTER = urlpart+"/android_login_api/register.php";
  public static  String URL_GetItems = urlpart+ "/android_login_api/getItems.php";
  public static  String URL_GetDetailItems = urlpart+"/android_login_api/get_Details.php";
  public static String URL_GetFavs = urlpart+"/android_login_api/get_favs.php";
  public static String URL_PostFavs = urlpart+"/android_login_api/save_favs.php";
  public static String URL_DeleteFavs = urlpart+"/android_login_api/delete_favs.php";
}
