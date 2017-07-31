/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmiranda;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author Portatil
 */
public class UtilDispositivos implements SerialPortEventListener, ServiceEvent {

    private static boolean desarrollo = false;

    private static String[] dispNames;
    private static UtilDispositivos instance = null;

    static Hashtable<String, SerialPort> serialPorts = new Hashtable<String, SerialPort>();

    protected UtilDispositivos() {
    }

    public static UtilDispositivos getInstance() {
        if (instance == null) {
            instance = new UtilDispositivos();
            instance.loadDispNames();
        }
        return instance;

    }

    public String[] getDispNames() {
        return this.dispNames;
    }

    public void setDispNames(String[] dispNames) {
        this.dispNames = dispNames;
    }

    public Hashtable<String, SerialPort> getSerialPorts() {
        return serialPorts;
    }

    /**
     *Metodo que llama a {@link UtilDispositivos#getDispDisponibles()} y los carga n la bariable de dispositivos disponibles
     */
    public void loadDispNames() {
        System.out.println("Cargando dispositivos");
        instance.setDispNames(instance.getDispDisponibles());
    }

    /**
     * Metodo obtiene los dispositivos disponibles en puertos COM
     *
     * @return
     */
    private String[] getDispDisponibles() {
        if (desarrollo) {
            
            String[] dispConected = new String[2];
            dispConected[0] = "COM4";
            dispConected[1] = "COM2";
            return dispConected;
        }
        return SerialPortList.getPortNames(); //Obtiene los nombres de los puertos seriales
    }

    /**
     * Metodo que conecta un puerto
     *
     * @param nPort String con el nombre del puerto a conecar
     * @throws SerialPortException Excepcion lanzada al no encontrar el puerto para conectar
     */
    public void conectarPuerto(String nPort) throws SerialPortException {
        SerialPort serialPort;
        if (serialPorts.get(nPort) == null) {//Comprobamos si el puerto se encuentra en la lista
            serialPort = new SerialPort(nPort);  // Crear el objeto 
            serialPort.openPort();//Abre el puerto serial
            serialPort.setParams(SerialPort.BAUDRATE_9600, //Configuración del puerto serial
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);            
            serialPort.addEventListener(UtilDispositivos.this, SerialPort.MASK_RXCHAR);
            serialPorts.put(nPort, serialPort);
        } else {
            serialPort = serialPorts.get(nPort);
            if (!serialPort.isOpened()) {//Comprobamos que el puero no este ya abierto
                serialPort.openPort();//Abre el puerto serial
                serialPort.removeEventListener();
                serialPort.addEventListener(UtilDispositivos.this, SerialPort.MASK_RXCHAR);
            }
        }
    }

    /**
     * * Método para detectar cuando llego un dato al puerto serial **
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && (event.getEventValue() > 0)) {
            try {
                String code = (serialPorts.get(event.getPortName()).readString(16));
//                String code = (serialPorts.get(event.getPortName()).readHexString(16)); // lee el dato del puerto serial y lo va agregando al jTextArea1      
                new ServiceRegistro(code, this).registrar();
                Main.getIntance().log(event.getPortName() + ":  " + code + "\n");
            } catch (SerialPortException ex) {
                Logger.getLogger(UtilDispositivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo que sobre escribe el metodo para registrar en servicios, este se
     * utiliza para enviarlo a la clase ServiceRegistroe inbocarlo.
     *
     * @param registro Objeto del tipo registro el cual se utilizara para registrar el paso de un tarjeta
     */
    @Override
    public void eventService(ServiceRegistro registro) {

//        Main.getIntance().log("Falta implementar envio al ws  :::: " + registro.toString() + "\n");
    }
}
