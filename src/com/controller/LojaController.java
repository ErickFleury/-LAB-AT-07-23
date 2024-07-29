package com.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.model.Funcionario;
import com.view.App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class LojaController implements Initializable{
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;
    @FXML
    private TableColumn<Funcionario, Integer> id;
    @FXML
    private TableColumn<Funcionario, String> nome;
    @FXML
    private TableColumn<Funcionario, String> cpf;
    @FXML
    private TableColumn<Funcionario, String> telefone;
    @FXML
    private TableColumn<Funcionario, String> endereco;
    @FXML
    private TableColumn<Funcionario, Double> salario;

    @FXML
    private TextField idField;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField enderecoField;
    @FXML
    private TextField salarioField;
    @FXML
    private Label erro;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    List<Funcionario> funcionarios = new ArrayList<>();

    ObservableList<Funcionario> funcionariosObs;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
        cpf.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cpf"));
        telefone.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("telefone"));
        endereco.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("endereco"));
        salario.setCellValueFactory(new PropertyValueFactory<Funcionario, Double>("salario"));
        
        try{
            List<Funcionario> funcionarios = new ArrayList<>();
            for (Funcionario f : com.view.App.funcionarioRepository.loadAll()) {
                funcionarios.add(f);
            }
            funcionariosObs = FXCollections.observableArrayList(funcionarios);
            tabelaFuncionarios.setItems(funcionariosObs);
        } catch (Exception e) {
            System.out.println("ERRO NA TABELA: "+e);
        }
        System.out.println("TABELA ATUALIZADA");
    }
    @FXML
    private void adicionarFuncionario(ActionEvent event) {
        try {
            try{
                Funcionario f = new Funcionario(nomeField.getText(), cpfField.getText(), telefoneField.getText(), enderecoField.getText(), Double.parseDouble(salarioField.getText()), 1);
                App.funcionarioRepository.create(f);
                initialize(null, null);
                limpa();
            } catch (NumberFormatException e) {
                erro.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println("ERRO AO ADICIONAR FUNCIONARIO: " + e);
        }
    }
    @FXML
    private void loadInfo(ActionEvent event) {
        try {
                Funcionario f = App.funcionarioRepository.loadFromId(Integer.parseInt(idField.getText()));
                nomeField.setText(f.getNome());
                cpfField.setText(f.getCpf());
                telefoneField.setText(f.getTelefone());
                enderecoField.setText(f.getEndereco());
                salarioField.setText(Double.toString(f.getSalario()));
        } catch (Exception e) {
            System.out.println("ERRO AO CARREGAR INFO: " + e);
        }
    }
    @FXML
    private void updateFuncionario(ActionEvent event){
        try {
            try{
                Funcionario f = App.funcionarioRepository.loadFromId(Integer.parseInt(idField.getText()));
                f.setNome(nomeField.getText());
                f.setCpf(cpfField.getText());
                f.setTelefone(telefoneField.getText());
                f.setEndereco(enderecoField.getText());
                f.setSalario(Double.parseDouble(salarioField.getText()));
                App.funcionarioRepository.update(f);
                initialize(null, null);
                limpa();
            } catch (NumberFormatException e) {
                System.out.println("ERRO NA PROCURA POR ID: ITEM NA AREA DE ID NÃO É UM NÚMERO" + e);
            }
        } catch (Exception e) {
            System.out.println("ERRO AO ATUALIZAR FUNCIONARIO: " + e);
        }
    }
    @FXML
    private void deleteFuncionario(ActionEvent event){
        try {
            try{
                Funcionario f = App.funcionarioRepository.loadFromId(Integer.parseInt(idField.getText()));
                App.funcionarioRepository.delete(f);
                initialize(null, null);
                limpa();
            } catch (NumberFormatException e) {
                System.out.println("ERRO NA PROCURA POR ID: ITEM NA AREA DE ID NÃO É UM NÚMERO" + e);
            }
        } catch (Exception e) {
            System.out.println("ERRO AO DELETAR FUNCIONARIO: " + e);
        }
    }
    private void limpa(){
        idField.setText("");
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
        salarioField.setText("");
    }
}
