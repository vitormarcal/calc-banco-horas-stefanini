package com.vitormarcal;

import java.time.LocalTime;
import java.time.temporal.TemporalAmount;

public class CalculadoraBancoDeHoras {

	private static final int MENOR = -1;
	private static final int IGUAL = 0;
	private static final int MAIOR = 1;
	
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
	
	public LocalTime calculaDiferenca(){
		if(this.totalCreditos == null){
			incluirCredito(0, 0);
		}
		if(this.totalDebitos == null){
			incluirDebito(0, 0);
		}
		
		int comparacao = this.totalCreditos.compareTo(this.totalDebitos); 
		
		if(comparacao == IGUAL){
			return LocalTime.of(0, 0);
		} else if(comparacao == MENOR){
			return this.totalDebitos.minusSeconds(this.totalCreditos.toSecondOfDay());
		} else {
			return this.totalCreditos.minusSeconds(this.totalDebitos.toSecondOfDay());
		}
	}
	
	public String mostraSituacaoDoBancoHoras(){
		return null;
	}
	
	public LocalTime getTotalCreditos() {
		return totalCreditos;
	}
	
	public LocalTime getTotalDebitos() {
		return totalDebitos;
	}

}
