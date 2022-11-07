package com.example.orderfoodonline.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderfoodonline.models.FoodDetail;
import com.example.orderfoodonline.models.FoodDetailModels;
import com.example.orderfoodonline.repository.DetailFoodRepository;

public class FoodDetailViewModel extends ViewModel {
    private DetailFoodRepository detailFoodRepository;

    public FoodDetailViewModel() {
        detailFoodRepository = new DetailFoodRepository();
    }
    public MutableLiveData<FoodDetailModels> foodDetailModelsMutableLiveData(int id){
        return detailFoodRepository.getFooddetail(id);
    }
}
