/*
 * Copyright (C) 2018 Radley Marx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.radley.transitionsdemo.ui.gallery;

import android.support.v7.widget.RecyclerView;

import dev.radley.transitionsdemo.databinding.GalleryImageBinding;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private final GalleryImageBinding mBinding;

    PhotoViewHolder(GalleryImageBinding itemBinding) {
        super(itemBinding.getRoot());
        mBinding = itemBinding;
    }

    public GalleryImageBinding getBinding() {
        return mBinding;
    }
}
