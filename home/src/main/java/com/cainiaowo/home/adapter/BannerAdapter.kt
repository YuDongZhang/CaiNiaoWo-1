package com.cainiaowo.home.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cainiaowo.common.webview.WebViewActivity
import com.cainiaowo.home.network.BannerItem
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

class BannerAdapter(list: List<BannerItem>) : BannerImageAdapter<BannerItem>(list) {
    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerItem?,
        position: Int,
        size: Int
    ) {
        holder ?: return
        Glide.with(holder.itemView)
            .load(data?.getDetailImgUrl())
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)
        holder.imageView.setOnClickListener { view ->
            WebViewActivity.openUrl(view.context, data?.redirect_url ?: "https://m.cniao5.com/")
        }
    }
}