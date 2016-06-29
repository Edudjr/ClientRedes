package application;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class MenuViewController {
	@FXML
	private TableView<Candidato> tableView;
	@FXML
	private TableColumn<Candidato, Integer> codigoColumn;
	@FXML
	private TableColumn<Candidato, String> nameColumn;
	@FXML
	private TableColumn<Candidato, String> partidoColumn;
	@FXML
	private TableColumn<Candidato, Integer> votosColumn;

	private ObservableList<Candidato> dataList = FXCollections.observableArrayList();

	// Reference to the main application
	private Cliente cliente;

	@FXML
	private void initialize() {
		// Initialize the person table
		codigoColumn.setCellValueFactory
		(new PropertyValueFactory<Candidato, Integer>("codigo_votacao"));
		nameColumn.setCellValueFactory
		(new PropertyValueFactory<Candidato, String>("nome_candidato"));
		partidoColumn.setCellValueFactory
		(new PropertyValueFactory<Candidato, String>("partido"));
		votosColumn.setCellValueFactory
		(new PropertyValueFactory<Candidato, Integer>("num_votos"));


		// Auto resize columns
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
	}

	public void setCliente(Cliente cliente){
		this.cliente = cliente;
	}

	public void loadDataList(){
		try {

			ArrayList<Candidato> array = new ArrayList<Candidato>();
			array = cliente.carregaCandidatos("999");

			if(array != null){
				for (Candidato item : array){
					dataList.add(item);
				}
				tableView.setItems(dataList);
			}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleCarregar(){
		loadDataList();
	}
	
	@FXML
	public void handleVotar(){
		ArrayList<Candidato> list = new ArrayList<Candidato>();
		list.addAll(dataList);
		cliente.showCandidate(list);
	}
	
	@FXML
	public void handleVotarNulo(){
		//
	}
	
	@FXML
	public void handleVotarBranco(){
		//
	}
	
	@FXML
	public void handleFinalizar(){
		//
	}
}
