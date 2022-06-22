package com.example.exambooktest.ui.practice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PracticeViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public PracticeViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }


}