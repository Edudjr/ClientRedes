package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CandidateViewController {
	
	private ArrayList<Candidato> candidateList;
	
	@FXML
	private TextField textfield;
	@FXML
	private Label erroLabel;

	// Reference to the main application
	private Cliente cliente;

	@FXML
	private void initialize() {
		candidateList = new ArrayList<Candidato>();
		erroLabel.setVisible(false);
	}

	public void setCliente(Cliente cliente){
		this.cliente = cliente;
	}
	
	public void setCandidateList(ArrayList<Candidato> list){
		this.candidateList = list;
	}

	public void handleConfirmar(){
		//Pega texto do textfield
		String text = textfield.getText();
		if(!text.isEmpty()){
			Integer num = Integer.parseInt(textfield.getText());
			
			//Percorre lista recebida de fora
			for(Candidato candidate : candidateList){
				if (num == candidate.getCodigo_votacao()){
					cliente.showConfirmation(candidateList, candidate);
					erroLabel.setVisible(false);
					return;
				}
			}
			
			//se chegou aqui é porque não encontrou
			erroLabel.setVisible(true);
		}
		
	}
	
	public void handleCorrige(){
		textfield.setText("");
	}
	
	public void handleCancelar(){
		cliente.showMenu(true);
	}
}
