package it.unisa.diem.Controller;

import javafx.event.ActionEvent;

/**
 * Interface for UI controllers that work on a list of items.
 */
public interface OnEditable {
    void onAdd(ActionEvent event);
    void onDelete(ActionEvent event);
    void onEdit(ActionEvent event);
    void onSave();
}
