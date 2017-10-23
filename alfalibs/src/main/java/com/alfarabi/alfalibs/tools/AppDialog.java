package com.alfarabi.alfalibs.tools;

import android.content.Context;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alfarabi.alfalibs.R;

/**
 * Created by User on 05/07/2017.
 */

public class AppDialog {

    private Context context ;

    public AppDialog(Context context) {
        this.context = context;
    }
    public static AppDialog with(Context context){
        AppDialog appDialog = new AppDialog(context);
        return appDialog ;
    }

    public void dialog(String title, String content, MaterialDialog.SingleButtonCallback okButtonCallback, MaterialDialog.SingleButtonCallback cancelButtonCallback){
        new MaterialDialog.Builder(context).title(title).content(content).positiveText(R.string.ok)
                .onPositive(okButtonCallback).negativeText(R.string.cancel).onNegative(cancelButtonCallback).show();
    }

    public void dialog(int title, int content, MaterialDialog.SingleButtonCallback okButtonCallback, MaterialDialog.SingleButtonCallback cancelButtonCallback){
        new MaterialDialog.Builder(context).title(title).content(content).positiveText(R.string.ok)
                .onPositive(okButtonCallback).negativeText(R.string.cancel).onNegative(cancelButtonCallback).show();
    }

    public void dialog(String title, String content, MaterialDialog.SingleButtonCallback okButtonCallback){
        new MaterialDialog.Builder(context).title(title).content(content).positiveText(R.string.ok)
                .onPositive(okButtonCallback).negativeText(R.string.cancel).show();
    }

    public void dialog(int title, int content, MaterialDialog.SingleButtonCallback okButtonCallback){
        new MaterialDialog.Builder(context).title(title).content(content).positiveText(R.string.ok)
                .onPositive(okButtonCallback).negativeText(R.string.cancel).show();
    }

    public void dialog(int title, int content){
        new MaterialDialog.Builder(context).title(title).content(content).positiveText(R.string.ok).show();
    }
    public void dialog(String title, String content){
        new MaterialDialog.Builder(context).title(title).content(content).positiveText(R.string.ok).show();
    }

    public void dialogPassword(int title, int content, MaterialDialog.SingleButtonCallback okButtonCallback){
        new MaterialDialog.Builder(context).title(title).content(content)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(context.getString(R.string.password), "", (dialog, input) -> {})
                .positiveText(R.string.ok)
                .onPositive(okButtonCallback).negativeText(R.string.cancel).show();
    }

    public void dialogPassword(String title, String content, MaterialDialog.SingleButtonCallback okButtonCallback){
        new MaterialDialog.Builder(context).title(title).content(content)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(context.getString(R.string.password), "", (dialog, input) -> {})
                .positiveText(R.string.ok)
                .onPositive(okButtonCallback).negativeText(R.string.cancel).show();
    }

}
