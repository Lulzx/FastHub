package com.fastaccess.provider.timeline.handler.drawable;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fastaccess.R;
import com.fastaccess.helper.PrefGetter;

import java.lang.ref.WeakReference;

class GlideDrawableTarget extends SimpleTarget<GlideDrawable> {
    private final UrlDrawable urlDrawable;
    private final WeakReference<TextView> container;

    GlideDrawableTarget(UrlDrawable urlDrawable, WeakReference<TextView> container) {
        this.urlDrawable = urlDrawable;
        this.container = container;
    }

    @Override public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
        if (container != null && container.get() != null) {
            TextView textView = container.get();
            float width = (float) (resource.getIntrinsicWidth() / 1.3);
            float height = (float) (resource.getIntrinsicHeight() / 1.3);
            Rect rect = new Rect(0, 0, Math.round(width), Math.round(height));
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            if (resource.isAnimated() && !PrefGetter.isGistDisabled()) {
                urlDrawable.setCallback((Drawable.Callback) textView.getTag(R.id.drawable_callback));
                resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                resource.start();
            }
            textView.setText(textView.getText());
            textView.invalidate();
        }
    }

}