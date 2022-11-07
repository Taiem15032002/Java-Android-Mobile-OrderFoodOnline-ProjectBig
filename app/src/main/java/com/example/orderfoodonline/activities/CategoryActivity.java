package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.adapters.CategoryAdapter;
import com.example.orderfoodonline.adapters.FoodAdapter;
import com.example.orderfoodonline.adapters.RamenAdapter;
import com.example.orderfoodonline.databinding.ActivityCategoryBinding;
import com.example.orderfoodonline.listener.CategoryListener;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Ramen;
import com.example.orderfoodonline.viewModels.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity implements DetailListener {
    ActivityCategoryBinding binding;
    CategoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        initView();
        initData();
    }

    private void initView() {
        binding.rvCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvCategory.setLayoutManager(layoutManager);
    }

    private void initData() {
        int idcate = getIntent().getIntExtra("idcate",1);
        String namecate = getIntent().getStringExtra("namecate");
            viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
            viewModel.ramenModelsMutableLiveData(idcate).observe(this, ramenModels -> {
                if (ramenModels.isSuccess()){
//                    FoodAdapter adapter = new FoodAdapter(ramenModels.getResult());
//                    binding.rvCategory.setAdapter(adapter);
                    binding.tvName.setText(namecate);
                }
            });
            viewModel.moreModelsMutableLiveData(idcate).observe(this, ramenModels -> {
                    FoodAdapter adapter = new FoodAdapter(ramenModels.getResult(), this);
                    binding.rvCategory.setAdapter(adapter);
            });
    }

    @Override
    public void onMenuClick(Ramen ramen) {
        Intent intent = new Intent(getApplicationContext(), DealtailFoodActivity.class);
        //Truyen id
        intent.putExtra("id",ramen.getId());
        startActivity(intent);
    }
}