package com.radleymarx.imagegallerydemo.transition;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.radleymarx.imagegallerydemo.IntentUtil;
import com.radleymarx.imagegallerydemo.databinding.DetailImageBinding;
import com.radleymarx.imagegallerydemo.databinding.GalleryImageBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailSharedElementEnterCallback extends SharedElementCallback {

    private final Intent intent;
    private float targetTextSize;
    private ColorStateList targetTextColors;
    private DetailImageBinding currentDetailBinding;
    private GalleryImageBinding currentPhotoBinding;
    private Rect targetPadding;
    
    private final List<View> mSharedElementViews;

    public DetailSharedElementEnterCallback(Intent intent) {
        this.intent = intent;
        mSharedElementViews = new ArrayList<>();
    }
    
    @Override
    public void onSharedElementStart(List<String> sharedElementNames,
                                     List<View> sharedElements,
                                     List<View> sharedElementSnapshots) {
    }
    
    @Override
    public void onSharedElementEnd(List<String> sharedElementNames,
                                   List<View> sharedElements,
                                   List<View> sharedElementSnapshots) {
    
        for (View sharedElementView : mSharedElementViews) {
        
            // no effect on transition hack I think it lays out out the checkbox
            forceSharedElementLayout(sharedElementView);
        }
    }
    
    @Override
    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        removeObsoleteElements(names, sharedElements, mapObsoleteElements(names));
        mapSharedElement(names, sharedElements, getPhoto());
    }
    
    public void setBinding(@NonNull DetailImageBinding binding) {
        currentDetailBinding = binding;
        currentPhotoBinding = null;
    }
    
    public void setBinding(@NonNull GalleryImageBinding binding) {
        currentPhotoBinding = binding;
        currentDetailBinding = null;
    }
    
    private ImageView getPhoto() {
        if (currentPhotoBinding != null) {
            return currentPhotoBinding.photo;
        } else if (currentDetailBinding != null) {
            return currentDetailBinding.photo;
        } else {
            throw new NullPointerException("Must set a binding before transitioning.");
        }
    }
    
    /**
     * Maps all views that don't start with "android" namespace.
     *
     * @param names All shared element names.
     * @return The obsolete shared element names.
     */
    @NonNull
    private List<String> mapObsoleteElements(List<String> names) {
        List<String> elementsToRemove = new ArrayList<>(names.size());
        for (String name : names) {
            if (name.startsWith("android")) continue;
            elementsToRemove.add(name);
        }
        return elementsToRemove;
    }

    /**
     * Removes obsolete elements from names and shared elements.
     *
     * @param names Shared element names.
     * @param sharedElements Shared elements.
     * @param elementsToRemove The elements that should be removed.
     */
    private void removeObsoleteElements(List<String> names,
                                        Map<String, View> sharedElements,
                                        List<String> elementsToRemove) {
        if (elementsToRemove.size() > 0) {
            names.removeAll(elementsToRemove);
            for (String elementToRemove : elementsToRemove) {
                sharedElements.remove(elementToRemove);
            }
        }
    }

    /**
     * Puts a shared element to transitions and names.
     *
     * @param names The names for this transition.
     * @param sharedElements The elements for this transition.
     * @param view The view to add.
     */
    private void mapSharedElement(List<String> names, Map<String, View> sharedElements, View view) {
        String transitionName = view.getTransitionName();
        names.add(transitionName);
        sharedElements.put(transitionName, view);
    }
    
    private void forceSharedElementLayout(View view) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(),
            View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(view.getHeight(),
            View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }


}