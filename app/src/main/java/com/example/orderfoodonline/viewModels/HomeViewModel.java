package com.example.orderfoodonline.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.repository.AllRepository;
import com.example.orderfoodonline.repository.CategoryRepository;
import com.example.orderfoodonline.repository.RamenRepository;

public class HomeViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private RamenRepository ramenRepository;
    private AllRepository allRepository;

    public HomeViewModel() {
        categoryRepository = new CategoryRepository();
        ramenRepository = new RamenRepository();
        allRepository = new AllRepository();
    }
    public MutableLiveData<CategoryModels> categoryModelsMutableLiveData(){
        return categoryRepository.getCategory();
    }
    public MutableLiveData<RamenModels> ramenModelsMutableLiveData(int idcate){
        return ramenRepository.getRamens(idcate);
    }
    public MutableLiveData<RamenModels> ramenallModelsMutableLiveData(int idcate){
        return allRepository.getAll(idcate);
    }
}
