package com.vitormarcal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Main {

	private final static String DEBITO = "DébitoBancoHoras660";
	private final static String CREDITO = "CréditoBancoHoras650";
	
	public static void main(String[] args) {
		try {
			File arquivo = new File("./src/main/java/com/vitormarcal/banco_horas.xls");
			
			List<LocalTime> listaHoraiosDebito = new ArrayList<LocalTime>();
			List<LocalTime> listaHoraiosCredito = new ArrayList<LocalTime>();
			
			if(arquivo.exists()){
				
				FileInputStream fis = new FileInputStream(arquivo);
				HSSFWorkbook workbook= new HSSFWorkbook(fis);
				HSSFSheet sheetHoras = workbook.getSheetAt(0);
				
				Iterator<Row> rowIterator = sheetHoras.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
					Iterator<Cell> cellIterator = row.cellIterator();
					
					while (cellIterator.hasNext()){
						Cell cell = cellIterator.next();
						if(cell.getColumnIndex() == 0){
							String conteudo  = cell.getStringCellValue().replace("-", "");
							
							conteudo = conteudo.replace(" ", "");
							if(conteudo.contains(DEBITO)){
								conteudo = conteudo.replace(DEBITO, "");
								int hora = Integer.parseInt(conteudo.substring(0, 2));
								int minutos = Integer.parseInt(conteudo.substring(3, 5));
								listaHoraiosDebito.add(LocalTime.of(hora, minutos));
				
							} else if(conteudo.contains(CREDITO)){
								conteudo = conteudo.replace(CREDITO, "");
								int hora = Integer.parseInt(conteudo.substring(0, 2));
								int minutos = Integer.parseInt(conteudo.substring(3, 5));
								listaHoraiosCredito.add(LocalTime.of(hora, minutos));
							}
							
						}
					}
				}
				workbook.close();
				fis.close();

				LocalTime creditosSomados = LocalTime.of(0, 0);
				if(listaHoraiosCredito != null){
					for (LocalTime localTime : listaHoraiosCredito) {
						creditosSomados = creditosSomados.plusMinutes(localTime.getMinute());
						creditosSomados = creditosSomados.plusHours(localTime.getHour());
						
					}
				}
				LocalTime debitosTotais = LocalTime.of(0, 0);
				if(listaHoraiosDebito != null){
					for (LocalTime localTime : listaHoraiosDebito) {
						debitosTotais = debitosTotais.plusMinutes(localTime.getMinute());
						debitosTotais = debitosTotais.plusHours(localTime.getHour());
					}
				}
				
				LocalTime resultado = null;
				
				if(debitosTotais.compareTo(creditosSomados) == -1){
					
					resultado = LocalTime.of(creditosSomados.getHour(), creditosSomados.getMinute());
					resultado = resultado.minusMinutes(debitosTotais.getMinute());
					resultado = resultado.minusHours(debitosTotais.getHour());
					
				} else {
					
					resultado = LocalTime.of(debitosTotais.getHour(), debitosTotais.getMinute());
					resultado = resultado.minusMinutes(creditosSomados.getMinute());
					resultado = resultado.minusHours(creditosSomados.getHour());
				}
				
				System.out.println("Creditos somados " + creditosSomados);
				System.out.println("Debitos totais " + debitosTotais);
				System.out.println("Creditos somados - Debitos totais " + resultado);
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
