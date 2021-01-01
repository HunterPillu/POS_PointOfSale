package com.prinkal.pos.app.db.entity;

import com.prinkal.pos.app.BR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Options extends BaseObservable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "option_id")
    private int optionId;

    @ColumnInfo(name = "option_name")
    private String optionName;

    @ColumnInfo(name = "option_type")
    private String type;

    @ColumnInfo(name = "option_values")
    private List<OptionValues> optionValues;

    @ColumnInfo(name = "sort_order")
    private int sortOrder;
    @Ignore
    private boolean selected;
    @Ignore
    private boolean displayError;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    @Bindable
    public String getOptionName() {
        if (optionName == null)
            return "";
        return optionName;
    }

    @Bindable({"displayError", "optionName"})
    public String getOptionNameError() {
        if (!isDisplayError()) {
            return "";
        }
        if (getOptionName().isEmpty()) {
            return "OPTION NAME IS EMPTY!";
        }
        return "";
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
        notifyPropertyChanged(BR.optionName);
    }

    @Bindable
    public String getType() {
        if (type == null)
            return type;
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public List<OptionValues> getOptionValues() {
        if (optionValues == null) {
            optionValues = new ArrayList<>();
            return optionValues;
        }
        return optionValues;
    }

    public void setOptionValues(List<OptionValues> optionValues) {
        this.optionValues = optionValues;
        notifyPropertyChanged(BR.optionValues);
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Bindable
    public boolean isDisplayError() {
        return displayError;
    }

    public void setDisplayError(boolean displayError) {
        this.displayError = displayError;
        notifyPropertyChanged(BR.displayError);
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }
}
