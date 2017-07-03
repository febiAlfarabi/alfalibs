package com.alfarabi.alfalibs.adapters.interfaze;

/**
 * Created by Alfarabi on 6/15/17.
 */

//O = Is anything class model that would be state as object
//FP = FP mean FIND_PARAMETER override it on child, end custom your code to find the field which containing the value of param
public interface SimpleHolderCallback<O, FP> {

    public void showData(O object);
    public void find(FP findParam);



}

