/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmiranda;

import java.util.Date;

/**
 *
 * @author Portatil
 */
public class ServiceRegistro {

    private Date fecha;
    private String codigo;
    private ServiceEvent registro;

    public ServiceRegistro(Date fecha, String codigo, ServiceEvent registro) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.registro = registro;
    }

    public ServiceRegistro(String codigo) {
        this.fecha = new Date();
        this.codigo = codigo;
    }

    public ServiceRegistro(String codigo, ServiceEvent registro) {
        this.codigo = codigo;
        this.registro = registro;
        this.fecha = new Date();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setRegistroEvent(ServiceEvent registro) {
        this.registro = registro;
    }

    public void registrar() {
        this.registro.eventService(this);
    }

    @Override
    public String toString() {
        return "ServiceRegistro{" + "fecha=" + fecha + ", codigo=" + codigo + '}';
    }

}
