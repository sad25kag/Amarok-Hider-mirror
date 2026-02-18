package deltazero.amarok.ui;

import android.content.Context;

import androidx.annotation.NonNull;

import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import deltazero.amarok.R;

public class ColorPickerDialog {

    private final Context context;
    private final String title;
    private final int currentColor;
    private final OnColorSelectedListener listener;

    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }

    private ColorPickerDialog(Builder builder) {
        this.context = builder.context;
        this.title = builder.title;
        this.currentColor = builder.currentColor;
        this.listener = builder.listener;
    }

    public void show() {
        new com.skydoves.colorpickerview.ColorPickerDialog.Builder(context)
                .setTitle(title)
                .setPreferenceName("PanicButtonColorPicker")
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
                .setBottomSpace(12)
                .show();
    }

    public static class Builder {
        private final Context context;
        private String title = "";
        private int currentColor = 0xFFD1D1D1;
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
