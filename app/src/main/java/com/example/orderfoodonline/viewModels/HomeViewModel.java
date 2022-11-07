package com.example.orderfoodonline.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.repository.CategoryRepository;
import com.example.orderfoodonline.repository.RamenRepository;

public class HomeViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private RamenRepository ramenRepository;

    public HomeViewModel() {
        categoryRepository = new CategoryRepository();
        ramenRepository = new RamenRepository();
    }
    public MutableLiveData<CategoryModels> categoryModelsMutableLiveData(){
        return categoryRepository.getCategory();
    }
    public MutableLiveData<RamenModels> ramenModelsMutableLiveData(int idcate){
        return ramenRepository.getRamens(idcate);
    }
}
