/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmiranda;

import jssc.SerialPortEvent;

/**
 *
 * @author Portatil
 */
public interface ServiceEvent {

    public abstract void eventService(ServiceRegistro registro);
}
