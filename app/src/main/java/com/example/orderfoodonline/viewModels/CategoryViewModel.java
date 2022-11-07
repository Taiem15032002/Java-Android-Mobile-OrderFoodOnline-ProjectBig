package com.example.orderfoodonline.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.repository.MoreFoodRepository;
import com.example.orderfoodonline.repository.RamenRepository;

public class CategoryViewModel extends ViewModel {
    private RamenRepository ramenRepository;
    private MoreFoodRepository moreFoodRepository;

    public CategoryViewModel(){
        moreFoodRepository = new MoreFoodRepository();
        ramenRepository = new RamenRepository();
    }

    public MutableLiveData<RamenModels> ramenModelsMutableLiveData(int idcate){
        return ramenRepository.getRamens(idcate);
    }
    public MutableLiveData<RamenModels> moreModelsMutableLiveData(int idcate){
        return moreFoodRepository.getMores(idcate);
    }
}
