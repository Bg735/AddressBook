package it.unisa.diem.Controller;

import javafx.event.ActionEvent;


public interface OnEditable {
    void onAdd(ActionEvent event);
    void onDelete(ActionEvent event);
    void onEdit(ActionEvent event);
    void onSave();
}
