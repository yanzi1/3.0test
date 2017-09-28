package com.me.fanyin.zbme.views.course.play.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.SystemUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.Opera;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class OperaDB {
    private HelperDB db;
    private Context mContext;
    /* 应用包名 */
    private static final String APP_PACKAGE = "com.dongao.mainclient.phone";

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private DhcpInfo dhcpInfo;
    private String userId;

    public OperaDB(Context context) {
        mContext=context;
        db = new HelperDB(context);

        wifiManager = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE));
        wifiInfo = wifiManager.getConnectionInfo();
        dhcpInfo = wifiManager.getDhcpInfo();
        userId= SharedPrefHelper.getInstance().getLoginUsername();
    }

    public long add(CourseWare cw, String errReson, String errUrl, String errTime) {
        if (find(userId,cw.getMobileVideoUrl()) || find(userId,cw.getMobileDownloadUrl())) {
            return -2;
        }
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("device", android.os.Build.MODEL);
        values.put("sysversion", android.os.Build.VERSION.RELEASE);
        values.put("appversion", SystemUtils.getVersion(mContext));
        values.put("appname", "会计云课堂");
        values.put("netype", getNetype());
        values.put("deviceDNS", errUrl);//intToIp(dhcpInfo.dns1)
        values.put("deviceIP", "");//getDeviceIP()

        if(errReson.equals("playError")){
            values.put("playurl", cw.getMobileVideoUrl());
        }else{
            values.put("playurl", cw.getMobileDownloadUrl());
        }
        values.put("subjectId", cw.getsSubjectId());
        values.put("classId", cw.getClassId());
        values.put("sectionId", cw.getSectionId());
        values.put("cwId", cw.getId());
        values.put("errtime", errTime);
        values.put("erroreson", errReson);
        values.put("errurl", "");

        long result = sql.insert("operation", null, values);
        sql.close();
        return result;
    }

    public List<Opera> findOperas(String userId) {
        List<Opera> list = new ArrayList<Opera>();
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.query("operation", null, "userId=?",
                new String[]{userId}, null, null, null);
        while (cursor.moveToNext()) {
            Opera opera = new Opera();
            opera.setDeviceType(cursor.getString(2));
            opera.setOsVersion(cursor.getString(3));
            opera.setAppVersion(cursor.getString(4));
            opera.setAppName(cursor.getString(5));
            opera.setNetType(cursor.getString(8));
            opera.setUserDns(cursor.getString(11));
            opera.setUserCdn(cursor.getString(12));
            opera.setDeviceIP(cursor.getString(11));
            opera.setPlayUrl(cursor.getString(6));
            opera.setRelevantId(cursor.getString(16));
            opera.setErrMsg(cursor.getString(10));
            opera.setErrUrl(cursor.getString(7));
            list.add(opera);
        }
        cursor.close();
        sql.close();
        return list;
    }


    private String getNetype(){
        String netype="";
        if(NetWorkUtils.getNetworkType(mContext)== ConnectivityManager.TYPE_WIFI){   //wifi
            netype="wifi";
        }else if(NetWorkUtils.getNetworkType(mContext)== ConnectivityManager.TYPE_MOBILE){ //3,4G
            netype="3G/4G";
        }
        return netype;
    }

//    private String getDeviceIP(){
//        String ip="";
//        NetWorkUtils type = new NetWorkUtils(mContext);
//        if(type.getNetType()==1){   //3g
//            ip=getIpAddress();
//        }else if(type.getNetType()==2){ //wifi
//            ip=intToIp(wifiInfo.getIpAddress());
//        }
//        return ip;
//    }

    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "."
                + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int updateState(String errurl) {

        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", "666");
        int update = sql.update("operation", values, "errurl=?", new String[]{errurl});
        sql.close();
        return update;
    }

    public int delete(String errurl) {

        SQLiteDatabase sql = db.getWritableDatabase();
        int result = sql.delete("operation", "errurl=?", new String[]{errurl});
        sql.close();
        return result;
    }

    public int deleteAll() {

        SQLiteDatabase sql = db.getWritableDatabase();
        int result = sql.delete("operation", "userId=?", new String[]{userId});
        sql.close();
        return result;
    }

    public boolean find(String userId, String playurl) {
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.query("operation", null, "userId=? and playurl=?",
                new String[]{userId,playurl}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            sql.close();
            return true;
        } else {
            cursor.close();
            sql.close();
            return false;
        }
    }

    public void delTable(String tableName){
        SQLiteDatabase sql=db.getWritableDatabase();
        sql.execSQL("drop table if exists"+tableName);
    }

}
