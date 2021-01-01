package com.prinkal.pos.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.CategoryActivity;
import com.prinkal.pos.app.databinding.FragmentAddCategoryBinding;
import com.prinkal.pos.app.db.entity.Category;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class AddCategoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "category";
    private static final String ARG_PARAM2 = "edit";

    private Category category;
    private boolean isEdit;
    public FragmentAddCategoryBinding binding;

    public AddCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable(ARG_PARAM1);
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_category, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setData(category);
        if (isEdit) {
            ((CategoryActivity) getContext())
                    .setTitle(getContext().getString(R.string.edit_category_title));
            ((CategoryActivity) getActivity()).binding.delete.setVisibility(View.VISIBLE);
        } else {
            ((CategoryActivity) getContext())
                    .setTitle(getContext().getString(R.string.add_category_title));
        }
        ((CategoryActivity) getActivity()).binding.setData(category);
        ((CategoryActivity) getActivity()).binding.setIsEdit(isEdit);
        ((CategoryActivity) getActivity()).binding.addCategory.setVisibility(View.GONE);
        ((CategoryActivity) getActivity()).binding.save.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((CategoryActivity) getActivity()).binding.addCategory.setVisibility(View.VISIBLE);
        ((CategoryActivity) getActivity()).binding.save.setVisibility(View.GONE);
        ((CategoryActivity) getActivity()).binding.delete.setVisibility(View.GONE);
        getActivity().recreate();
        ((CategoryActivity) getContext())
                .setTitle(getContext().getString(R.string.title_activity_category));
    }
}
