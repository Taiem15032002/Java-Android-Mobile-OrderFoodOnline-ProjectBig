package com.example.orderfoodonline.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.orderfoodonline.models.UserModels;
import com.example.orderfoodonline.repository.RegisterRepository;

public class RegisterViewModel extends ViewModel {
    private RegisterRepository registerRepository;
    public RegisterViewModel(){

        registerRepository = new RegisterRepository();
    }
    public MutableLiveData<UserModels> userModelsMutableLiveData(String email, String pass, String username){
        return registerRepository.getUsers(email, pass, username);
    }
}
