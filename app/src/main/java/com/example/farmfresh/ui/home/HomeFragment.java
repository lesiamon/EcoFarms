package com.example.farmfresh.ui.home;

import  android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.farmfresh.R;
import com.example.farmfresh.model.data.enums.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeFragment extends Fragment {
    private View root;
    private ArrayList<HashMap<String, Object>> list ;
    private int[] mImgIds=new int[] { R.drawable.fruit, R.drawable.vegetable, R.drawable.protein,
            R.drawable.dairy, R.drawable.sweets, R.drawable.grains, R.drawable.plant,
            R.drawable.jewelry, R.drawable.clothing };
    String[] cateKeys = Category.names();

    private final int DATA_SIZE = 9;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        GridLayout gridLayout = root.findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(2); // Set the number of columns to 2 for a vertical layout
        getData();
        ListAdapter adapter = new SimpleAdapter(
                root.getContext(),
                list,
                R.layout.grid_item,
                new String[]{"itemImage","itemName"},
                new int[]{R.id.itemImage,R.id.itemName});

        for (int i = 0; i < DATA_SIZE; i++) {
            View itemView = adapter.getView(i, null, gridLayout);
            GridLayout.Spec rowSpec = GridLayout.spec(i / 2, 1); // Calculate the row
            GridLayout.Spec colSpec = GridLayout.spec(i % 2, 1); // Calculate the column
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
            itemView.setLayoutParams(params);
            gridLayout.addView(itemView);

            final int position = i;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category category = Category.parseString(cateKeys[position]);
                    Intent intent = new Intent(root.getContext(), CategoryView.class);
                    intent.putExtra("category", category.toString());
                    root.getContext().startActivity(intent);
                }
            });
        }
        return root;
    }


    private void getData() {
        list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < DATA_SIZE; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", mImgIds[i]);
            map.put("itemName", cateKeys[i]);
            list.add(map);
        }
    }
}
