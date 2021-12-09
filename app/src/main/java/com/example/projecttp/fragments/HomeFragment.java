package com.example.projecttp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projecttp.R;
import com.example.projecttp.adapters.CategoryAdapter;
import com.example.projecttp.adapters.NewProductsAdapter;
import com.example.projecttp.models.CategoryModel;
import com.example.projecttp.models.NewProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView catRecycleView,newProductRecycleView;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList ;


    private SliderLayout sliderLayout;


    //new productsrecycleview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //fireStore
    FirebaseFirestore db ;


    public HomeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        catRecycleView =(RecyclerView) view.findViewById(R.id.rec_category);
        newProductRecycleView = view.findViewById(R.id.new_product_rec);

        db = FirebaseFirestore.getInstance();

        //auto_Slider
        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1);
        setSliderViews();


        //category
        categoryModelList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catRecycleView.setLayoutManager(linearLayoutManager);
        //catRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        catRecycleView.setAdapter(categoryAdapter);




        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               CategoryModel categoryModel = document.toObject(CategoryModel.class);
                               categoryModelList.add(categoryModel);
                               categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "erreur1"+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });






        //New Products
        newProductRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsModelList);
        newProductRecycleView.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "erreur2"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }



    public void setSliderViews() {
        for (int i=0;i<5;i++){
            DefaultSliderView sliderView= new DefaultSliderView(getContext());
            switch (i){
                case 0:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/projecttp-9bbfd.appspot.com/o/img1.jpg?alt=media&token=17ed9aa0-5588-4d18-a1db-82b061c15c46");
                    break;
                case 1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/projecttp-9bbfd.appspot.com/o/img2.jpg?alt=media&token=31c420a9-ce03-4e5b-89d7-0481edb8275f");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/projecttp-9bbfd.appspot.com/o/img3.jpg?alt=media&token=d09cafc1-73ad-4055-853b-38929c44d2f7");
                    break;
                case 3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/projecttp-9bbfd.appspot.com/o/img4.png?alt=media&token=2cba5dfa-78ef-49b2-a6de-1405ca52852e");
                    break;
                case 4:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/projecttp-9bbfd.appspot.com/o/img5.png?alt=media&token=9c54bf04-5292-4acf-a785-0eb8d3204f7c");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }
    }






}