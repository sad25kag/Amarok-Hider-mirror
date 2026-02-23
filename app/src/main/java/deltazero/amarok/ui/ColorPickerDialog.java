package deltazero.amarok.ui;

import android.content.Context;

import androidx.annotation.NonNull;

import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class ColorPickerDialog {

    private final Context context;
    private final String title;
    private final int currentColor;
    private final String preferenceName;
    private final OnColorSelectedListener listener;

    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }

    private ColorPickerDialog(Builder builder) {
        this.context = builder.context;
        this.title = builder.title;
        this.currentColor = builder.currentColor;
        this.preferenceName = builder.preferenceName;
        this.listener = builder.listener;
    }

    public void show() {
        var builder = new com.skydoves.colorpickerview.ColorPickerDialog.Builder(context)
                .setTitle(title)
                .setPreferenceName(preferenceName)
                .setPositiveButton(context.getString(android.R.string.ok),
                        (ColorEnvelopeListener) (envelope, fromUser) -> {
                            if (listener != null) {
                                listener.onColorSelected(envelope.getColor());
                            }
                        })
                .setNegativeButton(context.getString(android.R.string.cancel),
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(true)
                .attachBrightnessSlideBar(true)
                .setBottomSpace(12);
        builder.getColorPickerView().setInitialColor(currentColor);
        builder.show();
    }

    public static class Builder {
        private final Context context;
        private String title = "";
        private int currentColor = 0xFFD1D1D1;
        private String preferenceName = null;
        private OnColorSelectedListener listener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int titleResId) {
            this.title = context.getString(titleResId);
            return this;
        }

        public Builder setCurrentColor(int color) {
            this.currentColor = color;
            return this;
        }

        public Builder setPreferenceName(String preferenceName) {
            this.preferenceName = preferenceName;
            return this;
        }

        public Builder setOnColorSelectedListener(OnColorSelectedListener listener) {
            this.listener = listener;
            return this;
        }

        public ColorPickerDialog build() {
            return new ColorPickerDialog(this);
        }

        public void show() {
            build().show();
        }
    }
}
