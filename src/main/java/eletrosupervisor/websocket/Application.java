package eletrosupervisor.websocket;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        JLabel texto = new JLabel("O supervisor foi iniciado! Feche essa janela para interrompe-lo.");
        
        JPanel panel = new JPanel();
        panel.add(texto);
        
        JFrame janela = new JFrame("Eletro Supervisor");
        janela.add(panel);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.pack();
        janela.setVisible(true);
        
        try{
        	startNavegador();
        }catch(Exception e){	
        }
        
        SpringApplication.run(Application.class, args);
    }
    
    public static void startNavegador() throws IOException, URISyntaxException{
    	Desktop desktop = null;  
    	//Primeiro verificamos se é possível a integração com o desktop  
    	if (!Desktop.isDesktopSupported())   
    	    throw new IllegalStateException("Desktop resources not supported!");  
    	  
    	desktop = Desktop.getDesktop();  
    	//Agora vemos se é possível disparar o browser default.  
    	if (!desktop.isSupported(Desktop.Action.BROWSE))  
    	    throw new IllegalStateException("No default browser set!");  
    	  
    	//Pega a URI de um componente de texto.  
    	URI uri = new URI("http://localhost:8080");   
    	  
    	//Dispara o browser default, que pode ser o Explorer, Firefox ou outro.  
    	desktop.browse(uri); 
    }
}
