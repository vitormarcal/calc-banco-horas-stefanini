package com.vitormarcal;

import java.time.LocalTime;

public class CalculadoraBancoDeHoras {

	private static final int MENOR = -1;
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
		
		if(comparacao == MAIOR){
			return this.totalCreditos.minusSeconds(this.totalDebitos.toSecondOfDay());
		} else if(comparacao == MENOR){
			return this.totalDebitos.minusSeconds(this.totalCreditos.toSecondOfDay());
		} else {
			return LocalTime.of(0, 0);
		}
	}
	
	public String mostraSituacaoDoBancoHoras(){
		LocalTime localTime = calculaDiferenca();

		StringBuilder considerandos = new StringBuilder();
		considerandos.append(String.format("Considerando um total de %s horas de creditos e\n", this.totalCreditos));
		considerandos.append(String.format("considerando um total de %s horas de débitos, \n", this.totalDebitos));

		int comparacao = this.totalCreditos.compareTo(this.totalDebitos);
		if (comparacao == MAIOR) {
			considerandos.append(String.format("você ainda tem créditos em banco de %s horas", localTime));
		} else if (comparacao == MENOR) {
			considerandos.append(String.format("você está devendo %s horas em banco", localTime));
		} else {
			considerandos.append("você não tem débitos e créditos em banco");
		}
		
		return considerandos.toString();
	}
	
	public LocalTime getTotalCreditos() {
		return totalCreditos;
	}
	
	public LocalTime getTotalDebitos() {
		return totalDebitos;
	}

}
