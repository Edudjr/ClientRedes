package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Redes");

		try{
			FXMLLoader loader = new FXMLLoader
					(Main.class.getResource("ClientView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			Scene scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.show();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception{
		
		System.out.println("Iniciando Cliente.....");
		System.out.println("Iniciando conexão com o servidor....");

		Client cliente = new Client("cosmos.lasdpc.icmc.usp.br", Integer.parseInt(args[0]));

		System.out.println("Conexão estabelecida com sucesso....");

		cliente.start(); 

		Scanner scanner = new Scanner(System.in);

		while(true){
			System.out.println("Digite uma mensagem: ");
			String mensagem = scanner.nextLine();

			if(!cliente.isExecutando()){
				break;
			}

			cliente.send(mensagem);

			if("FIM".equals(mensagem)){
				break;
			}
		}
		System.out.println("Encerrando cliente...");

		cliente.stop();
		
		launch(args);
	}

	
}


class Client implements Runnable {

	private Socket socket;
	private BufferedReader in;
	private PrintStream out;	
	private boolean inicializado;
	private boolean executando;	
	private Thread thread;

	public Client(String endereco, int porta) throws Exception {

		inicializado = false;
		executando = false;
		open(endereco, porta);
	}


	private void open(String endereco,int porta) throws Exception{		
		try{
			socket = new Socket(endereco, porta);

			in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
			inicializado = true;
		}
		catch(Exception e){
			System.out.println(e);
			close();
			throw e;
		}
	}

	private void close(){

		if(in != null){
			try {
				in.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		if(out  != null){
			try {
				out.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		if(socket != null){
			try {
				socket.close();
			} 
			catch (Exception e) {
				System.out.println(e);
			}
		}

		in  = null;
		out = null;
		socket = null;

		inicializado = false;
		executando = false;

		thread = null;
	}

	public void start(){
		if(!inicializado || executando){
			return;
		}

		executando = true;
		thread =  new Thread(this);
		thread.start();
	}

	public void stop() throws Exception{
		executando = false;

		if (thread != null){
			thread.join();
		}
	}

	public boolean  isExecutando(){
		return executando;
	}

	public void send(String mensagem){
		out.println(mensagem); 
	}

	@Override
	public void run() {
		while (executando){
			try { 
				socket.setSoTimeout(2500);

				String mensagem  = in.readLine();

				if(mensagem == null){
					break;
				}

				System.out.println( 
						"Mensagem enviada pelo servidor: " +
								mensagem );
			}
			catch(SocketTimeoutException e){
				//ignorar
			}
			catch(Exception e){
				System.out.println(e);
				break;
			}
		}
		close(); 
	}


}
