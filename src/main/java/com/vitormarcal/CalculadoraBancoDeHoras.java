package com.vitormarcal;

import java.time.LocalTime;

public class CalculadoraBancoDeHoras {

	private LocalTime totalCreditos;
	private LocalTime totalDebitos;
	
	
	public void incluirCredito(int hora, int minutos){
		if(this.totalCreditos == null){
			this.totalCreditos = LocalTime.of(hora, minutos);
			return;
		}
		 this.totalCreditos = somaTime(this.totalCreditos, hora, minutos);
	}
	
	public void incluirDebito(int hora, int minutos){
		if(this.totalDebitos == null){
			this.totalDebitos = LocalTime.of(hora, minutos);
			return;
		}
		 this.totalDebitos = somaTime(this.totalDebitos, hora, minutos);
	}
	
	private LocalTime somaTime(LocalTime localTime, int hora, int minutos){
		 localTime = localTime.plusMinutes(minutos);
		return localTime.plusHours(hora);
	}
	
	
	

}
