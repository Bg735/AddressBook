package it.unisa.diem.Controller;

import javafx.event.ActionEvent;

/**
 * Interface for UI controllers that work on a list of items.
 */
public interface OnEditable {

    /**
     * Adds a new item to the list.
     */
    void onAdd(ActionEvent event);

    /**
     * Deletes the selected item from the list.
     */
    void onDelete(ActionEvent event);

    /**
     * Opens the edit mode for the selected item.
     */
    void onEdit(ActionEvent event);

    /**
     * Saves the changes made to the list of items onto internal file.
     */
    void onSave();
}
