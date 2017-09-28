package com.me.fanyin.zbme.views.main.activity;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.me.core.app.AppManager;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.PermissionsActivity;
import com.me.zxinglib.DAViewFinderView;
import com.me.zxinglib.IViewFinder;
import com.me.zxinglib.ZXingScannerView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.orhanobut.logger.Logger;

import java.util.Hashtable;

import butterknife.BindView;

/**
 * Created by jjr on 2017/3/20.
 */

public class CaptureActivity extends BaseMvpActivity<CaptureView, CapturePresenter> implements ZXingScannerView.ResultHandler, CaptureView {

    @BindView(R.id.capture_fl)
    FrameLayout capture_fl;

    private ZXingScannerView mScannerView;
    private boolean isScannerViewInit = false;
    private static final int REQUEST_PERMISSIONS_CODE = 1;

    @Override
    protected int getLayoutRes() {
        return R.layout.main_capture_activity;
    }

    @Override
    protected int getContentResId() {
        return R.layout.main_capture_content;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("扫一扫");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(this, message);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        showDialogLoading();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isScannerViewInit) {
                PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSIONS_CODE, new String[]{Manifest.permission.CAMERA, Manifest.permission.VIBRATE});
            } else {
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
                postHideLoading();
            }
        } else {
            if (!isScannerViewInit) {
                initScannerView();
            }
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
            postHideLoading();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) mScannerView.stopCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSIONS_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            if (!isScannerViewInit) {
                initScannerView();
            }
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
            postHideLoading();
        } else if (requestCode == REQUEST_PERMISSIONS_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            AppManager.getAppManager().finishActivity(this);
        }
    }

    private void initScannerView() {
        if (mScannerView == null) {
            isScannerViewInit = true;
            mScannerView = new ZXingScannerView(this) {
                @Override
                protected IViewFinder createViewFinderView(Context context) {
                    return new CustomDAViewFinderView(context);
                }
            };
            capture_fl.addView(mScannerView);
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        String qrUrl = rawResult.getText();
        Logger.e(qrUrl);
        if (!TextUtils.isEmpty(qrUrl)) {
            vibrator();//使手机振动
            // TODO: 2017/7/12
            if (qrUrl.contains("m.dongao.com")) {
                resumeCameraPreview();
                return;
            }
            if (!qrUrl.contains("dongao.com")) {
                showError("该二维码非东奥官方二维码");
                resumeCameraPreview();
                return;
            }
            if(qrUrl.contains("http://member.dongao.com/qr/book")){
                mPresenter.getData(qrUrl);
            }else if(qrUrl.contains("qrcode.api.dongao.com")){
                qrUrl = qrUrl.replace("&","@");
                mPresenter.getData(qrUrl);
            }
        }
    }

    private Handler mHandler = new Handler();

    public void postHideLoading() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideDialogLoading();
            }
        }, 500);
    }

    @Override
    public void resumeCameraPreview() {
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(CaptureActivity.this);
            }
        }, 1000);
    }

//    /**
//     * 设置startActivityForResult的结果,返回后通过Intent内部的Bundle的"result"键取出
//     *
//     * @param result 字符串类型的参数,内部设置为Bundle的"result"键的值
//     */
//    private void setResult(String result) {
//        Log.e("CaptureActivity", "setResult:" + result);
//        Intent resultIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("result", result);
//        resultIntent.putExtras(bundle);
//        this.setResult(0, resultIntent);
//        CaptureActivity.this.finish();
//    }

    private static class CustomDAViewFinderView extends DAViewFinderView {

        public static final String TRADE_MARK_TEXT = "将东奥的二维码放入框内，即可自动扫描";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 16;
        public final Paint PAINT = new Paint();

        public CustomDAViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomDAViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            setTopSpacing(DensityUtils.dip2px(getContext(), 100));
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            PAINT.setTextAlign(Paint.Align.CENTER);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft = framingRect.left + framingRect.width() / 2;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + DensityUtils.dip2px(getContext(), 40);
            } else {
                tradeMarkTop = DensityUtils.dip2px(getContext(), 40);
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }


    /******************************************  打开相册  ****************************************/

    /**
     * 打开本地图片
     */
    private void openLocalImage() {
        // 打开手机中的相册
        Intent innerIntent = new Intent(Intent.ACTION_PICK); //在4.4之前  "android.intent.action.GET_CONTENT"
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
        this.startActivityForResult(wrapperIntent, 0x01);
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    private Bitmap scanBitmap;

    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        int width = scanBitmap.getWidth();
        int height = scanBitmap.getHeight();
        int[] pixels = new int[width * height];
        scanBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        //第三个参数是图片的像素
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private String photo_path;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case 0x01:
//                    // 获取选中图片的路径
//                    Uri uri = data.getData();
//                    Cursor cursor = null;
//                    if (uri.getScheme().equals("content")) {//判断uri地址是以什么开头的
//                        cursor = getContentResolver().query(uri, null, null, null, null);
//                    } else {
//                        cursor = getContentResolver().query(getFileUri(uri), null, null, null, null);//红色字体判断地址如果以file开头
//                    }
//                    if (cursor != null && cursor.moveToFirst()) {
//                        photo_path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                    }
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Result result = scanningImage(photo_path);
//                            if (result != null) {
//                                setResult(result.getText());
//                            }
//                        }
//                    }).start();
//
//                    break;
//            }
//        }
//    }

    /**
     * 一个android文件的Uri地址一般如下：content://media/external/images/media/62026
     * 可是也有以“file”开头的文件系统路径，出现错误的原因就在这，上面的方法只能够解析出以content开头的图片路径，
     * 却不能查找到fie开头的图片，所以解决办法就是在得到图片路径之前加上判断，判断得到的uri是以什么开头的，
     * 判断的方法是:uri.getScheme().equals("file");通过这个方法来判断得到的uri是以什么开头的，再进行路径的拼接和查找
     *
     * @param uri
     * @return
     */
    public Uri getFileUri(Uri uri) {
        if (uri.getScheme().equals("file")) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                StringBuffer buff = new StringBuffer();
                buff.append("(")
                        .append(MediaStore.Images.ImageColumns.DATA)
                        .append("=")
                        .append("'" + path + "'")
                        .append(")");
                Cursor cur = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    //do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /*********************************************  打开闪关灯  **********************************************/

    private Camera camera;
    private boolean isOpen;

    private void turnOn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                camera = mScannerView.getCameraWrapper().mCamera;
                if (camera != null) {
                    Camera.Parameters mParameters = camera.getParameters();
                    mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//打开Camera.Parameters.FLASH_MODE_TORCH则为打开
                    camera.setParameters(mParameters);
                }
            }
        }).start();
    }

    private void turnOff() {
        camera = mScannerView.getCameraWrapper().mCamera;
        if (camera != null) {
            Camera.Parameters mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//打开Camera.Parameters.FLASH_MODE_OFF则为关闭
            camera.setParameters(mParameters);
        }
    }

    /*********************************************  振动  **********************************************/

    public void vibrator() {
        Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{0, 50, 50, 100, 50}, -1);
    }
}
