package com.minerva.common.imagebrowse;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ImageUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;

import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ImageBrowseViewModel extends BaseViewModel {
    public ObservableField<String> imgUrl = new ObservableField<>("http://loftcn.com/wp-content/uploads/2018/06/z1-4.jpg");
    public ObservableBoolean clickable = new ObservableBoolean(true);
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    public ImageBrowseViewModel(Context context) {
        super(context);
        Intent intent = ((BaseActivity) context).getIntent();
        if (intent != null) {
            imgUrl.set(intent.getStringExtra(Constants.KeyExtra.IMAGE_BROWSE_URL));
        }
        //设置状态栏颜色
        Window window = ((BaseActivity) context).getWindow();
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourceUtil.getColor(R.color.color_000000));
        }
    }

    public void download() {
        RetrofitHelper.getInstance("", null)
                .getServer()
                .downloadPicFromNet(imgUrl.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();
                        return BitmapFactory.decodeStream(inputStream);
                    }
                }).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                if (null != bitmap) {
                    ImageUtil.saveImageToGallery(context, bitmap);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showMsg("图片保存失败,请稍后再试..." + e.getMessage());
                clickable.set(false);
            }

            @Override
            public void onComplete() {
                ToastUtil.showMsg("图片保存成功,请到相册查找");
                clickable.set(true);
            }
        });
    }
}
