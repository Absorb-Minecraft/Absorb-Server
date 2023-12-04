package org.absorb.android.ui.consolelog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsoleLogModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConsoleLogModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the console log");
    }

    public LiveData<String> getText() {
        return mText;
    }
}