package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.ListsDetailsBy;

import java.util.List;

public interface CategoryDetailsCallback {
    public void onSuccessResult(List<ListsDetailsBy> categoryDetailsList);
    public void onFailureResult(String errorMsg);

}
