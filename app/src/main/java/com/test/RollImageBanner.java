package com.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banner.BaseIndicatorBanner;
import com.squareup.picasso.Picasso;

public class RollImageBanner extends BaseIndicatorBanner<BannerItem, RollImageBanner> {

    private ColorDrawable colorDrawable;

    public RollImageBanner(Context context) {
        this(context, null, 0);
    }

    public RollImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final BannerItem item = mDatas.get(position);
    }

    @Override
    public View onCreateItemView(int position) {
        ImageView imageView = new ImageView(mContext);

        final BannerItem item = mDatas.get(position);
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth * 1.0f / 10);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
        imageView.requestLayout();
        String imgUrl = item.picUrl;

        if (!TextUtils.isEmpty(imgUrl)) {
            Picasso.with(mContext).load(imgUrl)
                    .config(Bitmap.Config.ARGB_8888)
                    .into(imageView);
        } else {
            imageView.setImageDrawable(colorDrawable);
        }

        // 处理广告的点击跳转
        this.setOnItemClickL(new OnItemClickL() {
            @Override
            public void onItemClick(int position) {

                BannerItem item = mDatas.get(position);
                String linkedUrl = item.advtLinked;
                String advSrcTitleVisibility = item.src_TitleVisibility;
                String advWebViewLoginFlag = item.webViewLoginFlag;
                Log.d("aaa", "linkedUrl: " + linkedUrl + " advSrcTitleVisibility: " + advSrcTitleVisibility);

                if (TextUtils.isEmpty(linkedUrl)) {
                    Toast.makeText(mContext, "没有配置广告url！", Toast.LENGTH_LONG).show();
                    return;
                }

                // [逻辑判定]广告轮播图默认只支持KDS_WebPageNoHead, KDS_WebPageWithHead, 故下发其他快捷菜单功能Key则为中台升级使用advtInfoV3字段;
                if (!TextUtils.isEmpty(linkedUrl)
                        && ("KDS_WebPageNoHead".equalsIgnoreCase(advSrcTitleVisibility)
                        || "KDS_WebPageWithHead".equalsIgnoreCase(advSrcTitleVisibility))) {

                    /*KDS_ActivityUtils.switchWebWindow((Activity) mContext,
                            YT_HomeAdvActivity.class,
                            advSrcTitleVisibility,
                            linkedUrl,
                            false);*/
                    Toast.makeText(mContext, "跳转", Toast.LENGTH_LONG).show();

                }
            }
        });
        return imageView;
    }
}
