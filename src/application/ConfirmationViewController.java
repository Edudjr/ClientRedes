package application;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ConfirmationViewController {
	@FXML
	private Label numeroLabel;
	@FXML
	private Label nomeLabel;
	@FXML
	private Label partidoLabel;

	// Reference to the main application
	private Cliente cliente;
	private Candidato candidate;

	@FXML
	private void initialize() {
		
	}
	
	public void setNumeroLabel(String numero){
		this.numeroLabel.setText(numero);
	}
	
	public void setNomeLabel(String nome){
		this.nomeLabel.setText(nome);
	}
	
	public void setPartidoLabel(String partido){
		this.partidoLabel.setText(partido);
	}

	public void setCliente(Cliente cliente){
		this.cliente = cliente;
	}
	
	public void setCandidate(Candidato candidate){
		this.candidate = candidate;
		String nome = candidate.getNome_candidato();
		Integer numero = candidate.getCodigo_votacao();
		String partido = candidate.getPartido();
		
		this.setNomeLabel(nome);
		this.setNumeroLabel(numero.toString());
		this.setPartidoLabel(partido);
		
	}

	public void handleConfirmar(){
		cliente.confirmVote(candidate);
		cliente.showMenu();
	}
	
	public void handleCancelar(){
		cliente.showMenu();
	}
}
