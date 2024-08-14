package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.AreaItem;

import java.util.List;

public interface AreaCallback {
     void onSuccessareaResult(List<AreaItem> areaItemsItem);

     void onFailureareaResult(String errorMsg);
}
