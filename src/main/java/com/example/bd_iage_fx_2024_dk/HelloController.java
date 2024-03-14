package com.example.bd_iage_fx_2024_dk;

import com.example.bd_iage_fx_2024_dk.model.Personne;
import com.example.bd_iage_fx_2024_dk.repository.PersonneRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField cid;
    @FXML
    private TextField cage;

    @FXML
    private TextField cnom;

    @FXML
    private TextField cprenom;

    @FXML
    private TableColumn<Personne, Integer> tAge;

    @FXML
    private TableColumn<Personne, Integer> tId;

    @FXML
    private TableColumn<Personne, String> tNom;

    @FXML
    private TableColumn<Personne, String> tPrenom;

    @FXML
    private TableView<Personne> tableau;

    private PersonneRepository personneRepository;
    @FXML
    void add(ActionEvent event) {
       Personne personne = new Personne(cnom.getText(),cprenom.getText(),Integer.parseInt( cage.getText()));
       // PersonneRepository personneRepository = new PersonneRepository();
        personneRepository.add(personne);
        printAll();
        this.clear(event);


    }

    @FXML
    void clear(ActionEvent event) {
        cnom.setText("");
        cage.setText("");
        cprenom.setText("");
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int id = tableau.getSelectionModel().getSelectedItem().getId();
            personneRepository.delete(id);
            printAll();
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }




    @FXML
    void update(ActionEvent event) {

        Personne personne = new Personne(
                cnom.getText(),
                cprenom.getText(),
               Integer.parseInt(cage.getText())
        );
        personne.setId(Integer.parseInt(cid.getText()));
        personneRepository.update(personne);
        printAll();

    }

    void printAll(){

        try {

            ObservableList<Personne> list = personneRepository.findAll();
            tId.setCellValueFactory(new PropertyValueFactory<Personne, Integer>("id"));
            tNom.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
            tPrenom.setCellValueFactory(new PropertyValueFactory<Personne, String>("prenom"));
            tAge.setCellValueFactory(new PropertyValueFactory<Personne, Integer>("age"));
            this.tableau.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.personneRepository = new PersonneRepository();
        this.printAll();
    }

    public void charge(javafx.scene.input.MouseEvent mouseEvent) {

        if(mouseEvent.getClickCount() ==2){
           Personne p =  tableau.getSelectionModel().getSelectedItem();
           cnom.setText(p.getNom());
           cprenom.setText(p.getPrenom());
           cage.setText(p.getAge()+"");
           cid.setText(p.getId()+"");


        }

    }
}