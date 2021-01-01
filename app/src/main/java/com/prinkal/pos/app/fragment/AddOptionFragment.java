package com.prinkal.pos.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.activity.OptionsActivity;
import com.prinkal.pos.app.adapter.OptionValuesAdapter;
import com.prinkal.pos.app.databinding.FragmentAddOptionBinding;
import com.prinkal.pos.app.db.entity.OptionValues;
import com.prinkal.pos.app.db.entity.Options;
import com.prinkal.pos.app.handlers.OptionHandler;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class AddOptionFragment extends Fragment {
    private static final String ARG_PARAM1 = "options";
    private static final String ARG_PARAM2 = "edit";
    public List<OptionValues> optionValues;
    public FragmentAddOptionBinding binding;
    public OptionValuesAdapter optionValuesAdapter;
    public Options options;
    private boolean isEdit;

    public AddOptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            options = (Options) getArguments().getSerializable(ARG_PARAM1);
            isEdit = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_option, container, false);
        optionValues = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isEdit) {
            ((OptionsActivity) getContext())
                    .setTitle(getContext().getString(R.string.edit_option));
            optionValues.addAll(options.getOptionValues());
            ((OptionsActivity) getActivity()).binding.deleteOption.setVisibility(View.VISIBLE);
        } else {
            ((OptionsActivity) getContext())
                    .setTitle(getContext().getString(R.string.add_option));
            options.setOptionValues(optionValues);
        }
        binding.setData(options);
        binding.setHandler(new OptionHandler(getActivity()));
        ((OptionsActivity) getActivity()).binding.setEdit(isEdit);
        ((OptionsActivity) getActivity()).binding.setData(options);
        ((OptionsActivity) getActivity()).binding.addOption.setVisibility(View.GONE);
        ((OptionsActivity) getActivity()).binding.saveOption.setVisibility(View.VISIBLE);
        setOptionValueAdapter();
    }

    public void setOptionValueAdapter() {
        optionValuesAdapter = new OptionValuesAdapter(getActivity(), optionValues);
        binding.optionValuesRv.setAdapter(optionValuesAdapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((OptionsActivity) getActivity()).binding.addOption.setVisibility(View.VISIBLE);
        ((OptionsActivity) getActivity()).binding.saveOption.setVisibility(View.GONE);
        ((OptionsActivity) getActivity()).binding.deleteOption.setVisibility(View.GONE);
        getActivity().recreate();
        ((OptionsActivity) getContext())
                .setTitle(getContext().getString(R.string.title_activity_options));
    }
}
