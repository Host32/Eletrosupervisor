package eletrosupervisor.websocket;

import eletrosupervisor.serial.PortaListener;

public class PortaSimulator implements Runnable{
	private PortaListener listener;
	public PortaSimulator(PortaListener listener){
		this.listener = listener;
	}
	
	private long contador = 0;
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		while(true){
			contador++;
			if(contador > 1000000){
				contador = 0;
			}
			
			int faixaMax = contador % 500 < 100 ? 100 : 10;
			int faixaMin = contador % 500 < 100 ? 100 : 0;
			
			Double rand = (Math.random() * faixaMax) + faixaMin;
			
			StringBuilder sb = new StringBuilder();
			sb.append(rand.longValue());
			String random = sb.toString();
			this.listener.fire(random);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}
}
