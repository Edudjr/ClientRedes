package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CandidateViewController {
	
	private ArrayList<Candidato> candidateList;
	
	@FXML
	private TextField textfield;

	// Reference to the main application
	private Cliente cliente;

	@FXML
	private void initialize() {
		candidateList = new ArrayList<Candidato>();
	}

	public void setCliente(Cliente cliente){
		this.cliente = cliente;
	}
	
	public void setCandidateList(ArrayList<Candidato> list){
		this.candidateList = list;
	}

	public void handleConfirmar(){
		//Pega texto do textfield
		Integer num = Integer.parseInt(textfield.getText());
		
		//Percorre lista recebida de fora
		for(Candidato candidate : candidateList){
			if (num == candidate.getCodigo_votacao()){
				cliente.showConfirmation(candidate);
				return;
			}
		}
		
		//se chegou aqui é porque não encontrou
		//Emitir mensagem de erro
	}
	
	public void handleCorrige(){
		//cancelar
	}
	
	public void handleCancelar(){
		cliente.showMenu();
	}
}
