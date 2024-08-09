package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealRepositoryView {
    public void CategoryNetworkCall(CategoryCallback categoryCallBack);
    Single<ListsDetailsbyResponse> CategoryDetailsNetworkCall(String category);
}
