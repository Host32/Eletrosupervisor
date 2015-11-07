package eletrosupervisor.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class Porta implements SerialPortEventListener {
	private OutputStream serialOut;
	private InputStream serialIn;
	private BufferedReader serialReader;
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1",
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	private static final int TAXA = 9600;
	private PortaListener listener;
	
	/**
	 * 
	 * @param portaCOM
	 *            - Porta COM que será utilizada para enviar os dados para o
	 *            arduino
	 * @param taxa
	 *            - Taxa de transferência da porta serial geralmente é 9600
	 */
	public Porta(PortaListener listener) {
		this.listener = listener;
		this.initialize();
	}
	
	public void fire(String msg){
		this.listener.fire(msg);
	}

	public void initialize() {
		try {
			// Define uma variável portId do tipo CommPortIdentifier para
			// realizar a comunicação serial
			CommPortIdentifier portId = null;

			// Tenta verificar se a porta COM informada existe
			@SuppressWarnings("rawtypes")
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

			// First, Find an instance of serial port as set in PORT_NAMES.
			while (portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
						.nextElement();
				for (String portName : PORT_NAMES) {
					if (currPortId.getName().equals(portName)) {
						portId = currPortId;
						break;
					}
				}
			}
			
			if(portId == null){
				throw new Exception("Porta não encontrada");
			}

			// Abre a porta COM
			SerialPort port = (SerialPort) portId.open(this.getClass().getName(),
					TAXA);

			this.serialOut = port.getOutputStream();
			this.serialIn = port.getInputStream();

			this.serialReader = new BufferedReader(new InputStreamReader(
					this.serialIn));

			port.setSerialPortParams(TAXA, SerialPort.DATABITS_8, // taxa
																		// de 10
																		// bits
																		// 8
																		// (envio)
					SerialPort.STOPBITS_1, // taxa de 10 bits 1 (recebimento)
					SerialPort.PARITY_NONE); // receber e enviar dados

			// add event listeners
			port.addEventListener(this);
			port.notifyOnDataAvailable(true);
		} catch (Exception e) {
			throw new PortaException(e);
		}
	}

	public String read() throws IOException {
		return this.serialReader.readLine();
	}

	public void write(int opcao) throws IOException {
		serialOut.write(opcao);
	}

	public synchronized void close() {
		try {
			serialOut.close();
		} catch (IOException e) {
			throw new PortaException(e);
		}
	}

	@Override
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = read();
				fire(inputLine);
			} catch (Exception e) {
			}
		}
	}
}