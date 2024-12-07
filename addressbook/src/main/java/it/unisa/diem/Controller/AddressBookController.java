package it.unisa.diem.Controller;

import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Profile;
import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.Interfaces.Filter.BaseFilter;
import it.unisa.diem.Model.Interfaces.Filter.EmailFilter;
import it.unisa.diem.Model.Interfaces.Filter.Filter;
import it.unisa.diem.Model.Interfaces.Filter.NameFilter;
import it.unisa.diem.Model.Interfaces.Filter.PhoneFilter;
import it.unisa.diem.Model.Interfaces.Filter.TagFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddressBookController implements OnEditable {
    private static Filter completeFilter = null;
    private static Filter tagFilter = null;
    private TaggableList taggableList;
    private TrashCan trashCan;
    private ContactList contactList;

    public AddressBookController(Profile profile) {
        AddressBook ab = new AddressBook(profile.getAddressBookPath());
        this.taggableList = ab;
        this.trashCan = ab;
        this.contactList = ab;
    }

    @FXML
    void onAdd(ActionEvent event) {
        // Method implementation
    }

    @FXML
    protected void onDelete(ActionEvent event) {
        // Method implementation
    }

    @FXML
    void onEdit(ActionEvent event) {
        // Method implementation
    }

    @FXML
    void onSelect(ActionEvent event) {
        // Method implementation
    }

    @FXML
    void onSearch(ActionEvent event) {
        if (completeFilter == null) {
            completeFilter = new EmailFilter(new PhoneFilter(new NameFilter(new TagFilter(new BaseFilter()))));
        }
        
        // Method implementation
    }

    @FXML
    void onTagFilter(ActionEvent event) {
        if (tagFilter == null) {
            tagFilter = new TagFilter(new BaseFilter());
        }
        // Method implementation
    }

    @FXML
    void onImportBookFromFile(String path) {
        // Method implementation
    }

    @FXML
    void onExportBookToFile(String path) {
        // Method implementation
    }
}