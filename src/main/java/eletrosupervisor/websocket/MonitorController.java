package eletrosupervisor.websocket;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import eletrosupervisor.serial.Porta;
import eletrosupervisor.serial.PortaException;
import eletrosupervisor.serial.PortaListener;

@Controller
public class MonitorController implements PortaListener{
	public Porta porta;
	
	public MonitorController(){
		try{
			this.porta = new Porta(this);
		}catch(PortaException e){
			e.getOriginalException().printStackTrace();
			PortaSimulator s = new PortaSimulator(this);
			Thread t = new Thread(s);
			t.start();

			JOptionPane.showMessageDialog(null,
				    "O Arduino não foi encontrado, conecte-o e reinicie o supervisor.",
				    "Arduino não encontrado",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
    @Autowired
    private SimpMessagingTemplate template;
    
    public void fire(String value){
    	this.template.convertAndSend("/topic/monitor", new Data(value));
    }

}
