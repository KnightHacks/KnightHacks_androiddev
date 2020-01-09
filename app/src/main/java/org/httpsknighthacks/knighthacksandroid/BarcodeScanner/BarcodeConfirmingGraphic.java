package org.httpsknighthacks.knighthacksandroid.BarcodeScanner;

/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Canvas;
import android.graphics.Path;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import org.httpsknighthacks.knighthacksandroid.Resources.PreferenceUtils;

/** Guides user to move camera closer to confirm the detected barcode. */
class BarcodeConfirmingGraphic extends BarcodeGraphicBase {

    private final FirebaseVisionBarcode barcode;

    BarcodeConfirmingGraphic(GraphicOverlay overlay, FirebaseVisionBarcode barcode) {
        super(overlay);
        this.barcode = barcode;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draws a highlighted path to indicate the current progress to meet size requirement.
        float sizeProgress = PreferenceUtils.getProgressToMeetBarcodeSizeRequirement(overlay, barcode);
        Path path = new Path();
        if (sizeProgress > 0.95f) {
            // To have a completed path with all corners rounded.
            path.moveTo(boxRect.left, boxRect.top);
            path.lineTo(boxRect.right, boxRect.top);
            path.lineTo(boxRect.right, boxRect.bottom);
            path.lineTo(boxRect.left, boxRect.bottom);
            path.close();

        } else {
            path.moveTo(boxRect.left, boxRect.top + boxRect.height() * sizeProgress);
            path.lineTo(boxRect.left, boxRect.top);
            path.lineTo(boxRect.left + boxRect.width() * sizeProgress, boxRect.top);

            path.moveTo(boxRect.right, boxRect.bottom - boxRect.height() * sizeProgress);
            path.lineTo(boxRect.right, boxRect.bottom);
            path.lineTo(boxRect.right - boxRect.width() * sizeProgress, boxRect.bottom);
        }
        canvas.drawPath(path, pathPaint);
    }
}